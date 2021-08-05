package `in`.dmart.enterprise.refilling.ui.viewmodel.task.review

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.apiutil.Resource
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.ArticlePostReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.CreateUpdateTaskPostReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.RowPostReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateUpdateTaskPostResp
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.request.CloseTask
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.request.CloseTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.request.CloseTaskRow
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.request.ReviewTaskArticleRequest
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.CloseTaskResp
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticleData
import `in`.dmart.enterprise.refilling.util.DateUtil
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewTaskArticleViewModel  @Inject constructor(val webServices: WebServiceClass): ViewModel(){
    private var _articleList = MutableLiveData<Resource<List<ReviewTaskArticle>>>()
    val articleList: LiveData<Resource<List<ReviewTaskArticle>>>
    get()= _articleList

    private var _editTask = MutableLiveData<Resource<CreateUpdateTaskPostResp>>()
    val editTask: LiveData<Resource<CreateUpdateTaskPostResp>>
        get()= _editTask

    private var _closeTask= MutableLiveData<Resource<CloseTaskResp>>()
    val closeTask: LiveData<Resource<CloseTaskResp>>
    get()= _closeTask


    private var _listHasAscendingData = false
    val hasDataInAscendingOrder:Boolean
        get() = _listHasAscendingData

    private var _totalArticles = MutableLiveData<String>()
    val totalArticles: LiveData<String>
        get() = _totalArticles



    fun sendArticleRequest(rowId:String? = "",ean:String? = ""){
        _articleList.postValue(Resource.loading(null))
        var reviewTaskArticleReq = ReviewTaskArticleRequest(rowId,ean)
        viewModelScope.launch {
            val apiResponse = object : ApiResponse<ReviewTaskArticleData,Throwable> {
                override fun onSuccess(response: ReviewTaskArticleData) {
                    //write your business logic
                    _totalArticles.postValue(response.totalArticles)
                    var sortedList = sortArticleList(response.articleList)
                    _articleList.postValue(Resource.success(response.articleList))
                }

                override fun onFailure(error: Throwable?) {
                    _articleList.postValue(Resource.error(error?.message.toString(),null))
                }

            }

            webServices.getData(ApiUrls.API_GET_ARTICLES_BY_KEY,reviewTaskArticleReq,ReviewTaskArticleData::class.java,apiResponse)
        }

    }

    fun onSort(view: View){
        _articleList.value = Resource.success(sortArticleList(articleList.value?.data))
    }

    fun sortArticleList(list:List<ReviewTaskArticle>?):List<ReviewTaskArticle>? {
        _listHasAscendingData= !_listHasAscendingData!!
        return if (_listHasAscendingData) list?.sortedBy {
            it.fixBin
        } else list?.sortedBy {
            it.fixBin
        }?.reversed()
    }

    fun createOrUpdateTask( reasonForChange:String?, caseLotQty:String, reviewTaskArticle: ReviewTaskArticle) {

        _editTask.value = Resource.loading(null)
        var article =
            ArticlePostReq(reviewTaskArticle.ean,
                reviewTaskArticle.fixBin,
                "",
                "true",
                reviewTaskArticle.itemId,
                reviewTaskArticle.mrp,
                reasonForChange,
                reviewTaskArticle.taskId,
                (reviewTaskArticle.caseLotQty?.toInt() ?: 0 * caseLotQty.toInt()).toString(),
                caseLotQty)
        var row = RowPostReq(arrayListOf(article), reviewTaskArticle.rowId)
        var createUpdateTaskReq =
            CreateUpdateTaskPostReq(DateUtil.getCurrentDate(DateUtil.DDMMMYYYY), arrayListOf(row))

        viewModelScope.launch {
            val apiResponse = object : ApiResponse<CreateUpdateTaskPostResp, Throwable> {
                override fun onSuccess(response: CreateUpdateTaskPostResp) {
                    //write your business logic
                    _editTask.postValue(Resource.success(response))
                }

                override fun onFailure(error: Throwable?) {
                    _editTask.postValue(Resource.error(error?.message.toString(), null))

                }

            }
            webServices.postData(ApiUrls.API_POST_CREATE_OR_UPDATE_TASK, createUpdateTaskReq,
                CreateUpdateTaskPostResp::class.java, apiResponse)
        }
    }

    fun closeTask( reviewTaskArticle: ReviewTaskArticle) {

        _closeTask.value = Resource.loading(null)
        var article =
            CloseTaskArticle(reviewTaskArticle.taskId)
        var row = CloseTaskRow(arrayListOf(article),reviewTaskArticle.rowId)
        var closeTaskReq =
            CloseTask(DateUtil.getCurrentDate(DateUtil.DDMMMYYYY), arrayListOf(row))

        viewModelScope.launch {
            val apiResponse = object : ApiResponse<CloseTaskResp, Throwable> {
                override fun onSuccess(response: CloseTaskResp) {
                    //write your business logic
                    _closeTask.postValue(Resource.success(response))
                }

                override fun onFailure(error: Throwable?) {
                    _closeTask.postValue(Resource.error(error?.message.toString(), null))

                }

            }
            webServices.postData(ApiUrls.API_POST_CLOSE_TASK, closeTaskReq,
                CloseTaskResp::class.java, apiResponse)
        }
    }

}