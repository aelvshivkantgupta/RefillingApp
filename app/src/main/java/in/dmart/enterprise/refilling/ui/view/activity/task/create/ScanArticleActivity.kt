package `in`.dmart.enterprise.refilling.ui.view.activity.task.create

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.databinding.ActivityScanArticleBinding
import `in`.dmart.enterprise.refilling.databinding.CreateTaskArticleRowBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.Row
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.lib.dialogs.createtask.CreateTaskDialog
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.create.CreateTaskArticleViewModel
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.create.ScanArticleViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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


    }
    private fun setObserver(){
        scanArticleViewModel.createTaskArticleList.observe(this, Observer {
            setAdapter(it)
        } )
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

    fun onCreateTask(view: View){
        var createTaskArticle = view.tag as? CreateTaskArticle
        CreateTaskDialog(this,createTaskArticle!!,object : CreateTaskDialog.PopupActionListener{
            override fun onCreateClick(
                createTaskArticle: CreateTaskArticle,
                totalCaseLotQty: String,
                priority: Boolean,
            ) {
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