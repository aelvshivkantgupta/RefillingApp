package `in`.dmart.enterprise.refilling.ui.viewmodel.task.review

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.request.ReviewTaskArticleRequest
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticleData
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
    private var _articleList = MutableLiveData<List<ReviewTaskArticle>>()
    val articleList: LiveData<List<ReviewTaskArticle>>
    get()= _articleList

    private var _listHasAscendingData = false
    val hasDataInAscendingOrder:Boolean
        get() = _listHasAscendingData

    private var _totalArticles = MutableLiveData<String>()
    val totalArticles: LiveData<String>
        get() = _totalArticles



    fun sendArticleRequest(rowId:String? = "",ean:String? = ""){
        var reviewTaskArticleReq = ReviewTaskArticleRequest(rowId,ean)

        viewModelScope.launch {
            val apiResponse = object : ApiResponse<ReviewTaskArticleData,Throwable> {
                override fun onSuccess(response: ReviewTaskArticleData) {
                    //write your business logic
                    _totalArticles.postValue(response.totalArticles)
                    var sortedList = sortArticleList(response.articleList)
                    _articleList.postValue(sortedList)
                }

                override fun onFailure(error: Throwable?) {
                    //handle error logic
                }

            }

            webServices.getData(ApiUrls.API_GET_ARTICLES_BY_KEY,reviewTaskArticleReq,ReviewTaskArticleData::class.java,apiResponse)
        }

    }


    fun onSort(view: View){
        _articleList.value = sortArticleList(articleList.value)
    }

    fun sortArticleList(list:List<ReviewTaskArticle>?):List<ReviewTaskArticle>? {
        _listHasAscendingData= !_listHasAscendingData!!
        return if (_listHasAscendingData) list?.sortedBy {
            it.fixBin
        } else list?.sortedBy {
            it.fixBin
        }?.reversed()
    }





}