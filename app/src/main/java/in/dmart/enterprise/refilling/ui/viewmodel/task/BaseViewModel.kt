package `in`.dmart.enterprise.refilling.ui.viewmodel.task

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.apiutil.Resource
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.ArticleGetRequest
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.ArticlePostReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.CreateUpdateTaskPostReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.RowPostReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticleData
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateUpdateTaskPostResp
import `in`.dmart.enterprise.refilling.util.DateUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel(open val webServices: WebServiceClass): ViewModel() {
    protected var _articleList = MutableLiveData<Resource<List<CreateTaskArticle>>>()
    val createTaskArticleList: LiveData<Resource<List<CreateTaskArticle>>>
        get()= _articleList

    protected var _createTask = MutableLiveData<Resource<CreateUpdateTaskPostResp>>()
    val createTask: LiveData<Resource<CreateUpdateTaskPostResp>>
        get()= _createTask


    fun sendArticleRequest(url:String,rowId:String? = "",ean:String?=""){
        _articleList.postValue(Resource.loading(null))
        viewModelScope.launch {
            val apiResponse = object : ApiResponse<CreateTaskArticleData, Throwable> {
                override fun onSuccess(response: CreateTaskArticleData) {
                    //write your business logic
                    _articleList.postValue(Resource.success(response.articleList))
                }

                override fun onFailure(error: Throwable?) {
                    _articleList.postValue(Resource.error(error?.message.toString(),null))
                }

            }
            var createTaskArticles = ArticleGetRequest(rowId,ean);
            webServices.getData(url,createTaskArticles,
                CreateTaskArticleData::class.java,apiResponse)
        }

    }


    fun createOrUpdateTask(priority:String, reasonForChange:String?, caseLotQty:String, createTaskArticle: CreateTaskArticle){

        _createTask.value = Resource.loading(null)
        var article = ArticlePostReq(createTaskArticle.ean,createTaskArticle.fixBin,priority,"false",
            createTaskArticle.itemId,createTaskArticle.mrp,reasonForChange,createTaskArticle.taskId,(createTaskArticle.caseLotQty?.toInt()?:0 * caseLotQty.toInt()).toString(),caseLotQty)
        var row = RowPostReq(arrayListOf(article),createTaskArticle.rowId)
        var createTaskReq = CreateUpdateTaskPostReq(DateUtil.getCurrentDate(DateUtil.DDMMMYYYY), arrayListOf(row))

        viewModelScope.launch {
            val apiResponse = object : ApiResponse<CreateUpdateTaskPostResp,Throwable> {
                override fun onSuccess(response: CreateUpdateTaskPostResp) {
                    //write your business logic
                    _createTask.postValue(Resource.success(response))
                }

                override fun onFailure(error: Throwable?) {
                    _createTask.postValue(Resource.error(error?.message.toString(),null))

                }

            }
            webServices.postData(ApiUrls.API_POST_CREATE_OR_UPDATE_TASK,createTaskReq,CreateUpdateTaskPostResp::class.java,apiResponse)
        }

    }
}