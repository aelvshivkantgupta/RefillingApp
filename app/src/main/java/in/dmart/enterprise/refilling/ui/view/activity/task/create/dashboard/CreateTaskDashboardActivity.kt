package `in`.dmart.enterprise.refilling.ui.view.activity.task.create.dashboard

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.ActivityCreateTaskDashboardBinding
import `in`.dmart.enterprise.refilling.databinding.ActivityDashboardBinding
import `in`.dmart.enterprise.refilling.databinding.RowDashboardBinding
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.view.activity.task.create.CreateTaskArticleActivity
import `in`.dmart.enterprise.refilling.ui.view.activity.task.create.CreateTaskRowActivity
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.view.activity.task.create.ScanArticleActivity
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.create.dashboard.CreateTaskDashboardViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskDashboardActivity : BaseActivity<ActivityCreateTaskDashboardBinding>(),AdapterListener<RowDashboardBinding,String> {
    private val dashboardViewModel: CreateTaskDashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showActionBar(true)
        dataBinding = putContentView(R.layout.activity_create_task_dashboard)
        setTitle(resources.getString(R.string.create_task))
        setupObserver()
    }

    private fun setAdapter(dashboardRow: List<String>) {
        var mAdapter = CustomAdapter<RowDashboardBinding, String>(this,
            R.layout.row_dashboard,
            dashboardRow,
            this)
        dataBinding.recyclerView.setAdapterToView(mAdapter, this)
    }


    private fun setupObserver() {
        dashboardViewModel.dashboardRow.observe(this, Observer {
            setAdapter(it)
        })
    }

    override fun onBindViewHolder(
        holder: CustomAdapter<RowDashboardBinding, String>.ViewHolder,
        position: Int
    ) {
        holder.itemBinding.root.setOnClickListener(View.OnClickListener {
            var intent: Intent? = null
            when (it.tag) {
                this.dashboardViewModel.getRowAt(0) -> intent =
                    Intent(this, CreateTaskRowActivity::class.java)
                this.dashboardViewModel.getRowAt(1) -> intent =
                    Intent(this, ScanArticleActivity::class.java)
            }
            intent?.let {
                startActivity(intent)
            }
        })
    }
}