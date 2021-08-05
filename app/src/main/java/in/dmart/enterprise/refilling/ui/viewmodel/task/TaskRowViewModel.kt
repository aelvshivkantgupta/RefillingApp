package `in`.dmart.enterprise.refilling.ui.viewmodel.task

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.apiutil.Resource
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.TaskRow
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.response.Row
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.TaskType
import `in`.dmart.enterprise.refilling.model.apimodel.task.row.request.CreateTaskRowRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TaskRowViewModel  @Inject constructor(val webServices: WebServiceClass): ViewModel(){
    private var _rowList = MutableLiveData<Resource<List<Row>>>()
    val rowList: LiveData<Resource<List<Row>>>
        get()= _rowList

    fun sendRowRequest(taskType: TaskType){
        _rowList.postValue(Resource.loading(null))
        viewModelScope.launch {
            var apiResponse = object : ApiResponse<TaskRow, Throwable> {
                override fun onSuccess(response: TaskRow) {
                    //if (response.rowList.)
                    _rowList.postValue(Resource.success(response.rowList))

                }

                override fun onFailure(error: Throwable?) {
                   _rowList.postValue(Resource.error(error?.message.toString(), null))
                }

            }
            var taskRowReq = CreateTaskRowRequest(taskType.name)
            webServices.getData(ApiUrls.API_GET_ROW_LIST,taskRowReq,TaskRow::class.java,apiResponse)
        }

    }



}