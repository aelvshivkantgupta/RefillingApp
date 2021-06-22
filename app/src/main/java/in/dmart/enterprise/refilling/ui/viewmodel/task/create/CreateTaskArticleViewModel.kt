package `in`.dmart.enterprise.refilling.ui.viewmodel.task.create

import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.Article
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.ArticleData
import `in`.dmart.apilibrary.model.login.response.LoginResp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskArticleViewModel  @Inject constructor(val webServices: WebServiceClass): ViewModel(){
    private var _articleList = MutableLiveData<List<Article>>()
    val articleList: LiveData<List<Article>>
    get()= _articleList



    fun sendArticleRequest(){
        val loginResp = LoginResp()
        //apiResponse.onSuccess(null)
        viewModelScope.launch {
            val apiResponse = object : ApiResponse<ArticleData,Throwable?> {
                override fun onSuccess(response: ArticleData) {
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