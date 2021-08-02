package `in`.dmart.enterprise.refilling.ui.viewmodel.task.create

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticleData
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.CreateTaskArticleReq
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskArticleViewModel  @Inject constructor(val webServices: WebServiceClass): ViewModel(){
    private var _articleList = MutableLiveData<List<CreateTaskArticle>>()
    val createTaskArticleList: LiveData<List<CreateTaskArticle>>
    get()= _articleList

    private var _listHasAscendingData = false
    val hasDataInAscendingOrder:Boolean
        get() = _listHasAscendingData

    private var _totalArticles = MutableLiveData<String>()
    val totalArticles: LiveData<String>
        get() = _totalArticles


    fun sendArticleRequest(rowId:String? = ""){
        viewModelScope.launch {
            val apiResponse = object : ApiResponse<CreateTaskArticleData,Throwable> {
                override fun onSuccess(response: CreateTaskArticleData) {
                    //write your business logic
                    _articleList.postValue(response.articleList)
                }

                override fun onFailure(error: Throwable?) {
                    //handle error logic
                }

            }
            var createTaskArticles = CreateTaskArticleReq(rowId);
            webServices.getData(ApiUrls.API_GET_ARTICLES_BY_ROW,createTaskArticles,CreateTaskArticleData::class.java,apiResponse)
        }

    }


    fun onSort(view: View){
        _articleList.value = sortArticleList(createTaskArticleList.value)
    }

    fun sortArticleList(list:List<CreateTaskArticle>?):List<CreateTaskArticle>? {
        _listHasAscendingData= !_listHasAscendingData!!
        return if (_listHasAscendingData) list?.sortedBy {
            it.fixBin
        } else list?.sortedBy {
            it.fixBin
        }?.reversed()
    }

}