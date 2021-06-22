package `in`.dmart.enterprise.refilling.ui.viewmodel.task.create.dashboard

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.ui.lib.Application.Companion.context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class CreateTaskDashboardViewModel @Inject constructor():ViewModel(){

    val dashboardRow: MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    init {
        dashboardRow.postValue(context?.resources?.getStringArray(R.array.create_task_dashboard)?.toList())
    }

    fun getRowAt(index:Int):String = dashboardRow.value?.get(index).toString()



}