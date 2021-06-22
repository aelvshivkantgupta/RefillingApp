package `in`.dmart.enterprise.refilling.ui.viewmodel.dashboard

import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.ui.lib.Application.Companion.context
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor():ViewModel(){

    val dashboardRow: MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    init {
        dashboardRow.postValue(context?.resources?.getStringArray(R.array.dashboard)?.toList())
    }

    fun getRowAt(index:Int):String = dashboardRow.value?.get(index).toString()



}