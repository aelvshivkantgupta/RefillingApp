package `in`.dmart.enterprise.refilling.ui.view.activity.dashboard

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.ActivityDashboardBinding
import `in`.dmart.enterprise.refilling.databinding.RowDashboardBinding
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.view.activity.task.create.dashboard.CreateTaskDashboardActivity
import `in`.dmart.enterprise.refilling.ui.view.activity.task.review.ReviewTaskRowActivity
import `in`.dmart.enterprise.refilling.ui.lib.adapter.AdapterListener
import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import `in`.dmart.enterprise.refilling.ui.viewmodel.dashboard.DashboardViewModel
import `in`.dmart.enterprise.refilling.util.setAdapterToView
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity<ActivityDashboardBinding>(),AdapterListener<RowDashboardBinding,String> {

    val dashboardViewModel : DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showActionBar(true)
        dataBinding = putContentView(R.layout.activity_dashboard)
        showLogo(View.VISIBLE)
        showBackButton(false)
        setTitle("AAA0-Kanjur FC")
        setupObserver()
    }

    fun setAdapter(dashboardRow:List<String>){
        var mAdapter = CustomAdapter<RowDashboardBinding,String>(this, R.layout.row_dashboard,dashboardRow,this)
        dataBinding.recyclerView.setAdapterToView(mAdapter,this)
    }


    private fun setupObserver() {
        dashboardViewModel.dashboardRow.observe(this, Observer {
            setAdapter(it)
        })
    }

    override fun onBindViewHolder(holder: CustomAdapter<RowDashboardBinding, String>.ViewHolder, position: Int) {

        holder.itemBinding.root.setOnClickListener(View.OnClickListener {
            var intent:Intent?= null
            when(it.tag){
                this.dashboardViewModel.getRowAt(0) -> intent = Intent(this,
                    CreateTaskDashboardActivity::class.java)
                this.dashboardViewModel.getRowAt(1) -> intent = Intent(this, ReviewTaskRowActivity::class.java)
            }
            intent?.let {
                startActivity(intent)
            }
        })

    }
}