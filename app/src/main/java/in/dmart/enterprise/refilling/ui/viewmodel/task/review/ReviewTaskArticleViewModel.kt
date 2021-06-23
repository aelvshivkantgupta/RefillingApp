package `in`.dmart.enterprise.refilling.ui.viewmodel.task.review

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
        val reviewTaskArticleData = webServices.getDataFromFile("review_article_list",ReviewTaskArticleData::class.java)
        _totalArticles.postValue(reviewTaskArticleData.totalArticles)
        var sortedList = sortArticleList(reviewTaskArticleData.articleList)
        reviewTaskArticleData.articleList?.let {
            for (item in it){
                if (item.rowId==rowId || item.ean!!.split(",").contains(reviewTaskArticleReq.ean)){
                    _articleList.postValue(sortedList)
                    break
                }else{
                    _articleList.postValue(ArrayList())
                    break
                }
            }
        }

        //apiResponse.onSuccess(null)
        viewModelScope.launch {
            val apiResponse = object : ApiResponse<ReviewTaskArticleData,Throwable?> {
                override fun onSuccess(response: ReviewTaskArticleData) {
                    //write your business logic

                    _articleList.postValue(response.articleList)
                }

                override fun onFailure(error: Throwable?) {
                    //handle error logic
                }

            }



            //webServices.login()
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