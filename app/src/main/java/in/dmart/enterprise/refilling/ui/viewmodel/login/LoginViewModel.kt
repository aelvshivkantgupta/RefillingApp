package `in`.dmart.enterprise.refilling.ui.viewmodel.login

import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.model.apimodel.login.request.LoginRequest
import `in`.dmart.enterprise.refilling.model.apimodel.login.response.LoginResp
import `in`.dmart.enterprise.refilling.ui.lib.Application.Companion.context
import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val webServices:WebServiceClass):ViewModel(){

    val userName: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    private var _userMutableLiveData = MutableLiveData<LoginRequest>()
    val userData: LiveData<LoginRequest>
    get()= _userMutableLiveData

    //private var _loginResp = MutableLiveData<Resource<LoginResp>>()
    private var _loginResp = MutableLiveData<LoginResp>()
    //val loginResp: LiveData<Resource<LoginResp>>
    val loginResp: LiveData<LoginResp>
    get() = _loginResp


    public fun onLoginClick(view: View) {
        val loginRequest = LoginRequest()
        loginRequest.userId = userName.value
        loginRequest.password = password.value
        _userMutableLiveData?.value = loginRequest
    }

   /* fun sendLoginRequest(loginRequest: LoginRequest){
        val loginResp = LoginResp()
        loginResp.userId =loginRequest.userId
        //apiResponse.onSuccess(null)
        viewModelScope.launch {
            _loginResp.postValue(Resource.loading(null))
            val apiResponse = object : ApiResponse<LoginResp,Throwable?> {
                override fun onSuccess(response: LoginResp) {
                    //write your business logic

                    _loginResp.postValue(Resource.success(response))
                }

                override fun onFailure(error: Throwable?) {
                    _loginResp.postValue(Resource.error("",null))

                }

            }
            //webServices.login()
        }

    }*/
   fun sendLoginRequest(loginRequest: LoginRequest){
       val loginResp = LoginResp()
       loginResp.userId =loginRequest.userId
       //apiResponse.onSuccess(null)
       viewModelScope.launch {
           val apiResponse = object : ApiResponse<LoginResp,Throwable?> {
               override fun onSuccess(response: LoginResp) {
                   //write your business logic
                   _loginResp.postValue(response)
               }

               override fun onFailure(error: Throwable?) {
                  // _loginResp.postValue(Resource.error("",null))

               }

           }
           apiResponse.onSuccess(LoginResp())
           //webServices.login()
       }

   }
}

private fun <T> MutableLiveData<T>.postValue(success: T) {
   postValue(success)
}
