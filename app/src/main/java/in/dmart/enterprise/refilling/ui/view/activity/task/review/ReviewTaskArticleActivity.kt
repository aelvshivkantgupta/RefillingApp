package `in`.dmart.enterprise.refilling.ui.view.activity.task.review

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.databinding.ActivityReviewTaskArticleBinding
import `in`.dmart.enterprise.refilling.databinding.ReviewTaskArticleRowBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.Row
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.review.ReviewTaskArticleViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import android.os.Bundle
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
        val row = intent.getParcelableExtra<Row>(Constant.OBJ)
        setTitle("Row "+row.rowName)
        reviewTaskArticleViewModel.sendArticleRequest(row.rowId!!)

    }
    fun setObserver(){
        reviewTaskArticleViewModel.articleList.observe(this, Observer {
            var drawable = if(reviewTaskArticleViewModel.hasDataInAscendingOrder) resources.getDrawable(R.drawable.ic_down) else resources.getDrawable(R.drawable.ic_up)
            dataBinding.upDown.setImageDrawable(drawable)
            setAdapter(it)
        } )

    }

    fun setAdapter(articleList: List<ReviewTaskArticle>?){
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

    override fun onBindViewHolder(
        holder: CustomAdapter<ReviewTaskArticleRowBinding, ReviewTaskArticle>.ViewHolder,
        position: Int,
    ) {

   }


}