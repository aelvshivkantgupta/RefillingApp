package `in`.dmart.enterprise.refilling.ui.viewmodel.task.create

import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticleData
import `in`.dmart.enterprise.refilling.model.apimodel.article.request.CreateTaskArticleReq
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
        var createTaskArticleReq = CreateTaskArticleReq(rowId)
        val createTaskArticleData = webServices.getDataFromFile("task_create_article_list",
            CreateTaskArticleData::class.java)
        _totalArticles.postValue(createTaskArticleData.totalArticles)
        var sortedList = sortArticleList(createTaskArticleData.articleList)
        createTaskArticleData.articleList?.let {
            for (item in it){
                if (item.rowId==rowId){
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
            val apiResponse = object : ApiResponse<CreateTaskArticleData,Throwable?> {
                override fun onSuccess(response: CreateTaskArticleData) {
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