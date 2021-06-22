package `in`.dmart.enterprise.refilling.ui.view.activity.task.review

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.ActivityReviewTaskBinding
import `in`.dmart.enterprise.refilling.databinding.ActivityReviewTaskRowBinding
import `in`.dmart.enterprise.refilling.databinding.CrateTaskRowBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.Row
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.TaskRowViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.mindorks.framework.mvvm.utils.TaskType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewTaskRowActivity : BaseActivity<ActivityReviewTaskBinding>(),AdapterListener<CrateTaskRowBinding,Row>{
    var mAdapter:CustomAdapter<CrateTaskRowBinding,Row>?=null
    val reviewRowViewModel : TaskRowViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_review_task)
        showActionBar(true)
        setObserver()
        reviewRowViewModel.sendRowRequest(TaskType.CREATE)
    }
    fun setObserver(){
        reviewRowViewModel.rowList.observe(this, Observer {
            setAdapter(it)
        } )
    }

    fun setAdapter(rowList: List<Row>){
        mAdapter = CustomAdapter<CrateTaskRowBinding, Row>(this,R.layout.crate_task_row,rowList,this)
        dataBinding.recyclerView.setAdapterToView(mAdapter!!,this,0)
    }

    override fun onBindViewHolder(
        holder: CustomAdapter<CrateTaskRowBinding, Row>.ViewHolder,
        position: Int,
    ) {
        TODO("Not yet implemented")
    }
}