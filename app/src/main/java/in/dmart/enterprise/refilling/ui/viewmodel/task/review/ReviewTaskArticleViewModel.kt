package `in`.dmart.enterprise.refilling.ui.viewmodel.task.review

import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.model.apimodel.login.response.LoginResp
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticleData
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



    fun sendArticleRequest(){
        val loginResp = LoginResp()
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

}