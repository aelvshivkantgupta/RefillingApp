package `in`.dmart.enterprise.refilling.ui.viewmodel.task

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.ArticleGetRequest
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.ArticlePostReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.CreateTaskPostReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.RowPostReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticleData
import `in`.dmart.enterprise.refilling.util.DateUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel(open val webServices: WebServiceClass): ViewModel() {
    protected var _articleList = MutableLiveData<List<CreateTaskArticle>>()
    val createTaskArticleList: LiveData<List<CreateTaskArticle>>
        get()= _articleList


    fun sendArticleRequest(url:String,rowId:String? = "",ean:String?=""){
        viewModelScope.launch {
            val apiResponse = object : ApiResponse<CreateTaskArticleData, Throwable> {
                override fun onSuccess(response: CreateTaskArticleData) {
                    //write your business logic
                    _articleList.postValue(response.articleList)
                }

                override fun onFailure(error: Throwable?) {
                    //handle error logic
                }

            }
            var createTaskArticles = ArticleGetRequest(rowId,ean);
            webServices.getData(url,createTaskArticles,
                CreateTaskArticleData::class.java,apiResponse)
        }

    }
    fun sendScanArticleRequest(ean:String?=""){
        viewModelScope.launch {
            val apiResponse = object : ApiResponse<CreateTaskArticleData, Throwable> {
                override fun onSuccess(response: CreateTaskArticleData) {
                    //write your business logic
                    _articleList.postValue(response.articleList)
                }

                override fun onFailure(error: Throwable?) {
                    //handle error logic
                }

            }
            /*var createTaskArticles = ArticleGetRequest(rowId,ean);
            webServices.getData(ApiUrls.API_GET_ARTICLES_BY_ROW,createTaskArticles,
                CreateTaskArticleData::class.java,apiResponse)*/
        }

    }

    fun createTask(priority:String,reasonForChange:String?,caseLotQty:String,createTaskArticle: CreateTaskArticle){

        var article = ArticlePostReq(createTaskArticle.ean,createTaskArticle.fixBin,priority,"false",
            createTaskArticle.itemId,createTaskArticle.mrp,reasonForChange,createTaskArticle.taskId,(createTaskArticle.caseLotQty?.toInt()?:0 * caseLotQty.toInt()).toString(),caseLotQty)
        var row = RowPostReq(arrayListOf(article),createTaskArticle.rowId)
        var createTaskReq = CreateTaskPostReq(DateUtil.getCurrentDate(DateUtil.DDMMMYYYY), arrayListOf(row))

        /*viewModelScope.launch {
            val apiResponse = object : ApiResponse<CreateTaskPostResp,Throwable> {
                override fun onSuccess(response: CreateTaskPostResp) {
                    //write your business logic
                    _articleList.postValue(response.articleList)
                }

                override fun onFailure(error: Throwable?) {
                    //handle error logic
                }

            }
            var createTaskArticles = CreateTaskArticleReq(rowId);
            webServices.getData(ApiUrls.API_GET_ARTICLES_BY_ROW,createTaskArticles,CreateTaskArticleData::class.java,apiResponse)
        }*/

    }
}