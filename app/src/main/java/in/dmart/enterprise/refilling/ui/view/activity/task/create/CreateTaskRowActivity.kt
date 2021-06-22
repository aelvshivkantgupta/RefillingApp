package `in`.dmart.enterprise.refilling.ui.view.activity.task.create

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.ActivityCreateTaskRowBinding
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
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.TaskType
import `in`.dmart.enterprise.refilling.util.searchByRowName
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskRowActivity : BaseActivity<ActivityCreateTaskRowBinding>(),
    AdapterListener<CrateTaskRowBinding, Row> {
    var mAdapter:CustomAdapter<CrateTaskRowBinding,Row>?=null
    val createRowViewModel : TaskRowViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_create_task_row)
        dataBinding.lifecycleOwner = this

        setObserver()
        createRowViewModel.sendRowRequest(TaskType.CREATE)

    }
    private fun setObserver(){
        createRowViewModel.rowList.observe(this, Observer {
            setAdapter(it)
        } )
    }

    private fun setAdapter(rowList: List<Row>){
        mAdapter = CustomAdapter<CrateTaskRowBinding, Row>(this,R.layout.crate_task_row,rowList,this)
        dataBinding.recyclerView.setAdapterToView(mAdapter!!,this,0)

        mAdapter?.let { dataBinding.etSearch.searchByRowName(dataBinding.search, it) }

    }



    override fun onBindViewHolder(holder: CustomAdapter<CrateTaskRowBinding,Row>.ViewHolder, position: Int) {


    }
}