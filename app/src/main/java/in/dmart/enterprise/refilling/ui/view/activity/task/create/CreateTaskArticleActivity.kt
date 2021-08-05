package `in`.dmart.enterprise.refilling.ui.view.activity.task.create

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.apiutil.Status
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.databinding.ActivityCreateTaskArticleBinding
import `in`.dmart.enterprise.refilling.databinding.ArticleInfoBinding
import `in`.dmart.enterprise.refilling.databinding.CreateTaskArticleRowBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.LastRefillingDetail
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.Row
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.lib.dialogs.createtask.CreateTaskDialog
import `in`.dmart.enterprise.refilling.ui.lib.popup.PopupView
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.create.CreateTaskArticleViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskArticleActivity : BaseActivity<ActivityCreateTaskArticleBinding>(),AdapterListener<CreateTaskArticleRowBinding, CreateTaskArticle> {
    private var mAdapter: CustomAdapter<CreateTaskArticleRowBinding, CreateTaskArticle>?=null
    private val createTaskArticleViewModel : CreateTaskArticleViewModel by viewModels()
    private var row:Row? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_create_task_article)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = createTaskArticleViewModel
        setObserver()
        setCreateTaskObserver()
        row = intent.getParcelableExtra<Row>(Constant.OBJ)
        setTitle("Row "+row?.rowName)
        createTaskArticleViewModel.sendArticleRequest(ApiUrls.API_GET_ARTICLES_BY_ROW,row?.rowId!!)

        dataBinding.sinnerSortBy.adapter = ArrayAdapter.createFromResource(this,
            R.array.sortBy,
            R.layout.spinner_item);
    }

    private fun setObserver() {
        createTaskArticleViewModel.createTaskArticleList.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        hideProgressDialog()
                        var drawable = if(createTaskArticleViewModel.hasDataInAscendingOrder) resources.getDrawable(R.drawable.ic_down) else resources.getDrawable(R.drawable.ic_up)
                        dataBinding.upDown.setImageDrawable(drawable)
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


    private fun setAdapter(createTaskArticleList: List<CreateTaskArticle>?){
        if (createTaskArticleList == null || createTaskArticleList.isEmpty()){
            finish()
        }else {
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
    }
    private fun setCreateTaskObserver() {
        createTaskArticleViewModel.createTask.observe(this, {
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

    fun onCreateTask(view: View){
        var createTaskArticle = view.tag as? CreateTaskArticle
        CreateTaskDialog(this,createTaskArticle!!,object :CreateTaskDialog.PopupActionListener{
            override fun onCreateClick(
                createTaskArticle: CreateTaskArticle,
                totalCaseLotQty: String,
                priority: Boolean,
            ) {
                createTaskArticleViewModel.createOrUpdateTask(priority.toString(),"",totalCaseLotQty,createTaskArticle)
            }

            override fun onCancelClick() {
            }

        }).show()

    }


    fun onInfo(view: View){
        var createTaskArticle = view.tag as? CreateTaskArticle
        createTaskArticle?.lastRefillingDetails?.let {
            PopupView<ArticleInfoBinding, LastRefillingDetail>(R.layout.article_info,
                createTaskArticle?.lastRefillingDetails?.get(0)!!).showPopupWindow(view.parent as ViewGroup)
        }

    }


    override fun onBindViewHolder(
        holder: CustomAdapter<CreateTaskArticleRowBinding, CreateTaskArticle>.ViewHolder,
        position: Int,
    ) {

    }
}