package `in`.dmart.enterprise.refilling.ui.viewmodel.task

import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.TaskRow
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.Row
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.TaskType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskRowViewModel  @Inject constructor(val webServices: WebServiceClass): ViewModel(){
    private var _rowList = MutableLiveData<List<Row>>()
    val rowList: LiveData<List<Row>>
        get()= _rowList

    fun sendRowRequest(taskType: TaskType){
        viewModelScope.launch {
            val apiResponse = object : ApiResponse<TaskRow, Throwable?> {
                override fun onSuccess(response: TaskRow) {
                    //write your business logic

                    _rowList.postValue(response.rowList)
                }

                override fun onFailure(error: Throwable?) {
                    //handle error logic
                }

            }
            var list = webServices.getDataFromFile("task_row_list",TaskRow::class.java).rowList
            _rowList.postValue(list)
            //webServices.login()
        }

    }

}