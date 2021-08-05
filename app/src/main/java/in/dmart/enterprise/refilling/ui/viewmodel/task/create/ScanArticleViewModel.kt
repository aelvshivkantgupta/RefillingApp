package `in`.dmart.enterprise.refilling.ui.viewmodel.task.create

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.apiutil.Resource
import `in`.dmart.enterprise.refilling.ui.lib.Application
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.BaseViewModel
import `in`.dmart.enterprise.refilling.util.AppUtil
import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanArticleViewModel  @Inject constructor(override val webServices: WebServiceClass) : BaseViewModel(webServices){

    val searchText: MutableLiveData<String> = MutableLiveData("")

    fun onSearchClick(view: View){
        if (view is Button){
            searchText?.let {
                var go = Application.context?.getString(R.string.go)
                if (view.text.toString().equals(go,true)){
                    if(it.value?.isEmpty() == true){
                        AppUtil.showToast("Please enter or scan search text")
                    }else {
                        view.text = Application.context?.getString(R.string.clear)
                        sendArticleRequest(ApiUrls.API_GET_ARTICLES_BY_KEY,"",it.value!!.trim())
                    }
                }else{
                    view.text = go
                    _articleList.value = Resource.success(ArrayList())
                }
            }
        }



    }


}