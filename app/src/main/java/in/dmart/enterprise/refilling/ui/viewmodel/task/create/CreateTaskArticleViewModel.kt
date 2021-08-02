package `in`.dmart.enterprise.refilling.ui.viewmodel.task.create

import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.ui.viewmodel.task.BaseViewModel
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTaskArticleViewModel  @Inject constructor(override val webServices: WebServiceClass) : BaseViewModel(webServices){


    private var _listHasAscendingData = false
    val hasDataInAscendingOrder:Boolean
        get() = _listHasAscendingData

    private var _totalArticles = MutableLiveData<String>()
    val totalArticles: LiveData<String>
        get() = _totalArticles

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