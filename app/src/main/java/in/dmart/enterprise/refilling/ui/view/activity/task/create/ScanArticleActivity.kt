package `in`.dmart.enterprise.refilling.ui.view.activity.task.create

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.apiutil.Status
import `in`.dmart.enterprise.refilling.databinding.ActivityScanArticleBinding
import `in`.dmart.enterprise.refilling.databinding.ArticleInfoBinding
import `in`.dmart.enterprise.refilling.databinding.CreateTaskArticleRowBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.LastRefillingDetail
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.lib.dialogs.createtask.CreateTaskDialog
import `in`.dmart.enterprise.refilling.ui.lib.popup.PopupView
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.create.ScanArticleViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanArticleActivity : BaseActivity<ActivityScanArticleBinding>(),AdapterListener<CreateTaskArticleRowBinding, CreateTaskArticle> {
    private var mAdapter: CustomAdapter<CreateTaskArticleRowBinding, CreateTaskArticle>?=null
    private val scanArticleViewModel : ScanArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_scan_article)
        setTitle(getString(R.string.search_article))
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = scanArticleViewModel
        setObserver()
        setCreateTaskObserver()

    }
    private fun setObserver() {
        scanArticleViewModel.createTaskArticleList.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        hideProgressDialog()
                        setAdapter(it)                    }
                }
                Status.LOADING -> {
                    showProgressDialog()
                }
                Status.ERROR -> {
                    hideProgressDialog()
                    if (it.message?.isNotEmpty() == true) {
                        showToast(it.message)
                    }
                }
            }
        })
    }

    private fun setCreateTaskObserver() {
        scanArticleViewModel.createTask.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        hideProgressDialog()
                        showToast(it.message)
                        // createTaskArticleViewModel.sendArticleRequest(ApiUrls.API_GET_ARTICLES_BY_ROW,row?.rowId!!)
                    }
                }
                Status.LOADING -> {
                    showProgressDialog()
                }
                Status.ERROR -> {
                    hideProgressDialog()
                    if (it.message?.isNotEmpty() == true) {
                        showToast(it.message)
                    }
                }
            }
        })
    }


    private fun setAdapter(createTaskArticleList: List<CreateTaskArticle>){
            if (mAdapter == null) {
                mAdapter = CustomAdapter<CreateTaskArticleRowBinding, CreateTaskArticle>(this,
                    R.layout.create_task_article_row,
                    createTaskArticleList,
                    this)
                dataBinding.recyclerView.setAdapterToView(mAdapter!!, this, 0)
            } else {
                mAdapter?.setDataList(createTaskArticleList)
                mAdapter?.notifyDataSetChanged()
            }

    }

    fun onInfo(view: View){
        var createTaskArticle = view.tag as? CreateTaskArticle
        createTaskArticle?.lastRefillingDetails?.let {
            PopupView<ArticleInfoBinding,LastRefillingDetail>(R.layout.article_info,
                createTaskArticle?.lastRefillingDetails?.get(0)!!).showPopupWindow(view.parent as ViewGroup)
        }

    }


    fun onCreateTask(view: View){
        var createTaskArticle = view.tag as? CreateTaskArticle
        CreateTaskDialog(this,createTaskArticle!!,object : CreateTaskDialog.PopupActionListener{
            override fun onCreateClick(
                createTaskArticle: CreateTaskArticle,
                totalCaseLotQty: String,
                priority: Boolean,
            ) {
                scanArticleViewModel.createOrUpdateTask(priority.toString(),"",totalCaseLotQty,createTaskArticle)

            }

            override fun onCancelClick() {
            }

        }).show()

    }

    override fun onBindViewHolder(
        holder: CustomAdapter<CreateTaskArticleRowBinding, CreateTaskArticle>.ViewHolder,
        position: Int,
    ) {
    }
}