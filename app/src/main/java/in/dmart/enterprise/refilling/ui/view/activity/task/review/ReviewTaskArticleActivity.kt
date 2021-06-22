package `in`.dmart.enterprise.refilling.ui.view.activity.task.review

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.ActivityReviewTaskArticleBinding
import `in`.dmart.enterprise.refilling.databinding.ReviewTaskArticleRowBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticle
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
    var mAdapter:CustomAdapter<ReviewTaskArticleRowBinding,ReviewTaskArticle>?=null
    val reviewTaskArticleViewModel : ReviewTaskArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_review_task_article)
        showActionBar(true)
        setObserver()
        reviewTaskArticleViewModel.sendArticleRequest()
    }
    fun setObserver(){
        reviewTaskArticleViewModel.articleList.observe(this, Observer {
            setAdapter(it)
        } )
    }

    fun setAdapter(articleList: List<ReviewTaskArticle>){
        mAdapter = CustomAdapter<ReviewTaskArticleRowBinding,ReviewTaskArticle>(this,R.layout.create_task_article_row,articleList,this)
        dataBinding.recyclerView.setAdapterToView(mAdapter!!,this,0)
    }

    override fun onBindViewHolder(
        holder: CustomAdapter<ReviewTaskArticleRowBinding, ReviewTaskArticle>.ViewHolder,
        position: Int,
    ) {
        TODO("Not yet implemented")
    }


}