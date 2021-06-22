package `in`.dmart.enterprise.refilling.ui.view.activity.task.create

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.ActivityCreateTaskArticleBinding
import `in`.dmart.enterprise.refilling.databinding.CreateTaskArticleRowBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.Article
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.create.CreateTaskArticleViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskArticleActivity : BaseActivity<ActivityCreateTaskArticleBinding>(),AdapterListener<CreateTaskArticleRowBinding, Article> {
    var mAdapter: CustomAdapter<CreateTaskArticleRowBinding, Article>?=null
    val createTaskArticleViewModel : CreateTaskArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_create_task_article)
        showActionBar(true)
        setObserver()
        createTaskArticleViewModel.sendArticleRequest()
    }

    fun setObserver(){
        createTaskArticleViewModel.articleList.observe(this, Observer {
           setAdapter(it)
        } )
    }

    fun setAdapter(articleList: List<Article>){
        mAdapter = CustomAdapter<CreateTaskArticleRowBinding,Article>(this,R.layout.create_task_article_row,articleList,this)
        dataBinding.recyclerView.setAdapterToView(mAdapter!!,this,0)
    }

    override fun onBindViewHolder(
        holder: CustomAdapter<CreateTaskArticleRowBinding, Article>.ViewHolder,
        position: Int,
    ) {
        TODO("Not yet implemented")
    }
}