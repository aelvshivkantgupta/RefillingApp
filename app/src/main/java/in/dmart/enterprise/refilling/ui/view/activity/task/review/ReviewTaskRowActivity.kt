package `in`.dmart.enterprise.refilling.ui.view.activity.task.review

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.databinding.ActivityReviewTaskBinding
import `in`.dmart.enterprise.refilling.databinding.ReviewTaskRowBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.Row
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.TaskRowViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.TaskType
import `in`.dmart.enterprise.refilling.util.searchByRowName
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewTaskRowActivity : BaseActivity<ActivityReviewTaskBinding>(),AdapterListener<ReviewTaskRowBinding,Row>{
    private var mAdapter:CustomAdapter<ReviewTaskRowBinding,Row>?=null
    private val taskReviewRowViewModel : TaskRowViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_review_task)
        setTitle(resources.getString(R.string.fc_row))
        dataBinding.lifecycleOwner = this
        setObserver()
        taskReviewRowViewModel.sendRowRequest(TaskType.CREATE)

    }

    private fun setObserver(){
        taskReviewRowViewModel.rowList.observe(this, Observer {
            setAdapter(it)
        } )
    }

    private fun setAdapter(rowList: List<Row>){
        mAdapter = CustomAdapter<ReviewTaskRowBinding, Row>(this,R.layout.review_task_row,rowList,this)
        dataBinding.recyclerView.setAdapterToView(mAdapter!!,this,0)

        mAdapter?.let { dataBinding.etSearch.searchByRowName(dataBinding.search, it) }
    }



    override fun onBindViewHolder(
        holder: CustomAdapter<ReviewTaskRowBinding, Row>.ViewHolder,
        position: Int,
    ) {
        holder.itemBinding.root.setOnClickListener(View.OnClickListener {
            val model = it.getTag() as? Row
            val  intent = Intent(this,ReviewTaskArticleActivity::class.java)
            intent.putExtra(Constant.OBJ,model)
            startActivity(intent)
        })

    }
}