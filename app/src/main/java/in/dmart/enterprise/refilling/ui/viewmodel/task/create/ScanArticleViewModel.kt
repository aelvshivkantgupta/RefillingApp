package `in`.dmart.enterprise.refilling.ui.viewmodel.task.create

import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticleData
import `in`.dmart.enterprise.refilling.model.apimodel.article.request.CreateTaskArticleReq
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request.ScanArticleRequest
import `in`.dmart.enterprise.refilling.ui.lib.Application
import `in`.dmart.enterprise.refilling.util.AppUtil
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanArticleViewModel  @Inject constructor(val webServices: WebServiceClass): ViewModel(){

    private var _articleList = MutableLiveData<List<CreateTaskArticle>>()
    val createTaskArticleList: LiveData<List<CreateTaskArticle>>
    get()= _articleList
    val searchText: MutableLiveData<String> = MutableLiveData("")



    fun sendArticleRequest(ean:String? = "",searchKey:String?=""){
        var createTaskArticleReq = ScanArticleRequest(ean,searchKey)
        val createTaskArticleData = webServices.getDataFromFile("task_create_article_list",
            CreateTaskArticleData::class.java)
        createTaskArticleData.articleList?.let {
            for (item in it){
                if (item.ean?.contains(ean!!) == true){
                    _articleList.postValue(createTaskArticleData.articleList.subList(0,1))
                    break
                }else{
                    AppUtil.showToast("No data found")
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

    fun onSearchClick(view: View){
        if (view is Button){
            searchText?.let {
                var go = Application.context?.getString(R.string.go)
                if (view.text.toString().equals(go,true)){
                    if(it.value?.isEmpty() == true){
                        AppUtil.showToast("Please enter or scan search text")
                    }else {
                        view.text = Application.context?.getString(R.string.clear)
                        sendArticleRequest(it.value!!.trim())
                    }
                }else{
                    view.text = go
                    _articleList.value = ArrayList()
                }
            }
        }



    }


}