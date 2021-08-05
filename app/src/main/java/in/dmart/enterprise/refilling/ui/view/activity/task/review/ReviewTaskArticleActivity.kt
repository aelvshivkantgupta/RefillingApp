package `in`.dmart.enterprise.refilling.ui.view.activity.task.review

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.apiutil.Status
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.databinding.ActivityReviewTaskArticleBinding
import `in`.dmart.enterprise.refilling.databinding.ReviewArticleInfoBinding
import `in`.dmart.enterprise.refilling.databinding.ReviewTaskArticleRowBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.LastRefillingDetail
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.Row
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.lib.dialogs.reviewtask.CloseTaskDialog
import `in`.dmart.enterprise.refilling.ui.lib.dialogs.reviewtask.EditTaskDialog
import `in`.dmart.enterprise.refilling.ui.lib.popup.PopupView
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.review.ReviewTaskArticleViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewTaskArticleActivity : BaseActivity<ActivityReviewTaskArticleBinding>() ,
    AdapterListener<ReviewTaskArticleRowBinding, ReviewTaskArticle> {
    private var mAdapter:CustomAdapter<ReviewTaskArticleRowBinding,ReviewTaskArticle>?=null
    private val reviewTaskArticleViewModel : ReviewTaskArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_review_task_article)
        showActionBar(true)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = reviewTaskArticleViewModel
        setObserver()
        setEditTaskObserver()
        setCloseTaskObserver()
        val row = intent.getParcelableExtra<Row>(Constant.OBJ)
        setTitle("Row "+row.rowName)
        reviewTaskArticleViewModel.sendArticleRequest(row.rowId!!)

    }

    private fun setObserver() {
        reviewTaskArticleViewModel.articleList.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        hideProgressDialog()
                        var drawable = if(reviewTaskArticleViewModel.hasDataInAscendingOrder) resources.getDrawable(R.drawable.ic_down) else resources.getDrawable(R.drawable.ic_up)
                        dataBinding.upDown.setImageDrawable(drawable)
                        setAdapter(it)                 }
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
    private fun setEditTaskObserver() {
        reviewTaskArticleViewModel.editTask.observe(this, {
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
    private fun setCloseTaskObserver() {
        reviewTaskArticleViewModel.closeTask.observe(this, {
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
    private fun setAdapter(articleList: List<ReviewTaskArticle>?){
        if (articleList == null || articleList.isEmpty()){
            finish()
        }else {
            if(mAdapter == null) {
                mAdapter = CustomAdapter<ReviewTaskArticleRowBinding, ReviewTaskArticle>(this,
                    R.layout.review_task_article_row,
                    articleList,
                    this)
                dataBinding.recyclerView.setAdapterToView(mAdapter!!, this, 0)
            }else{
                mAdapter?.setDataList(articleList)
                mAdapter?.notifyDataSetChanged()
            }
        }
    }

    fun onInfo(view: View){
        var reviewTaskArticle = view.tag as? ReviewTaskArticle
        reviewTaskArticle?.lastRefillingDetails?.let {
            PopupView<ReviewArticleInfoBinding, LastRefillingDetail>(R.layout.review_article_info,
                reviewTaskArticle?.lastRefillingDetails?.get(0)!!).showPopupWindow(view.parent as ViewGroup)
        }

    }

    fun onEditTask(view: View){
        var reviewTaskArticle = view.tag as? ReviewTaskArticle
        EditTaskDialog(this,reviewTaskArticle!!,object : EditTaskDialog.PopupActionListener{
            override fun onCreateClick(
                reviewTaskArticle: ReviewTaskArticle,
                totalCaseLotQty: String,
                reason: String,
            ) {

                reviewTaskArticleViewModel.createOrUpdateTask(reason,totalCaseLotQty,reviewTaskArticle)

            }

            override fun onCancelClick() {

            }


        }).show()
    }

    fun onCloseTask(view: View){
        var reviewTaskArticle = view.tag as? ReviewTaskArticle
        CloseTaskDialog(this,reviewTaskArticle!!,object : CloseTaskDialog.PopupActionListener{
            override fun onYesClick(model: ReviewTaskArticle) {
                reviewTaskArticleViewModel.closeTask(model)
            }

            override fun onNoClick() {
            }


        }).show()

    }

    override fun onBindViewHolder(
        holder: CustomAdapter<ReviewTaskArticleRowBinding, ReviewTaskArticle>.ViewHolder,
        position: Int,
    ) {

   }


}