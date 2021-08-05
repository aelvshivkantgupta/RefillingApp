package `in`.dmart.enterprise.refilling.ui.viewmodel.login

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.apilibrary.model.login.request.LoginRequest
import `in`.dmart.apilibrary.model.login.response.LoginResp
import `in`.dmart.apilibrary.model.login.response.UserRole
import `in`.dmart.enterprise.refilling.apiutil.Resource
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.di.SharedPref
import `in`.dmart.enterprise.refilling.util.FireBaseUtil
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val webServices: WebServiceClass,
    private val sharedPref: SharedPref
) : ViewModel() {

    val userName: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    private var _userMutableLiveData = MutableLiveData<LoginRequest>()

    val userData: LiveData<LoginRequest>
        get() = _userMutableLiveData

    private var _loginResp = MutableLiveData<Resource<LoginResp>>()
    //private var _loginResp = MutableLiveData<LoginResp>()

    val loginResp: LiveData<Resource<LoginResp>>
        //val loginResp: LiveData<LoginResp>
        get() = _loginResp


    public fun onLoginClick(view: View) {
        val loginRequest = LoginRequest()
        loginRequest.userId = userName.value
        loginRequest.password = password.value
        _userMutableLiveData.value = loginRequest
    }

     /*fun sendLoginRequest(loginRequest: LoginRequest) {
         val loginResp = LoginResp()
         loginResp.userId = loginRequest.userId
         //apiResponse.onSuccess(null)
         viewModelScope.launch {
             _loginResp.postValue(Resource.success(LoginResp()))
             val apiResponse = object : ApiResponse<LoginResp, Throwable?> {
                 override fun onSuccess(response: LoginResp) {
                     //write your business logic

                     _loginResp.postValue(Resource.success(response))
                 }

                 override fun onFailure(error: Throwable?) {
                     _loginResp.postValue(Resource.error("", null))

                 }

             }
             //webServices.login()
         }

     }*/

    fun sendLoginRequest(loginRequest: LoginRequest) {
        var loginResp = LoginResp()
        loginResp.userId = loginRequest.userId
        //apiResponse.onSuccess(null)
        _loginResp.postValue(Resource.loading(null))

        viewModelScope.launch {
            val apiResponse = object : ApiResponse<LoginResp, Throwable> {
                override fun onSuccess(response: LoginResp) {
                    //write your business logic
                    checkResponse(loginRequest, response)
                }

                override fun onFailure(error: Throwable?) {
                    _loginResp.postValue(Resource.error("", LoginResp()))
                    //loginResp._loginResp.postValue(Resource.error("", null))

                }

            }
            //apiResponse.onSuccess(LoginResp())
            webServices.login(
                ApiUrls.API_POST_LOGIN_USERNAME,
                loginRequest,
                LoginResp::class.java, apiResponse
            )
        }
    }

    private fun checkResponse(loginRequest: LoginRequest, response: LoginResp) {
        if (isAuthorizedUser(response.userRoles, Constant.REFILLING_APP)) {
            Constant.userName = response.fullName ?: ""
            Constant.userId = loginRequest.userId
            sharedPref.putString(SharedPref.USERNAME, Constant.userName)
            sharedPref.putString(SharedPref.USERID, loginRequest.userId ?: "")
            sharedPref.putString(SharedPref.USERPWD, loginRequest.password ?: "")
            _loginResp.postValue(Resource.success(response))
        } else {
            val loginResp = LoginResp()
            val msg = "You are not authorized user to use this app"
            _loginResp.postValue(Resource.error(msg, loginResp))
        }
        //apiResponse.onSuccess(data);
        FireBaseUtil.sendEvent(
            null,
            FireBaseUtil.EVENT_NAME_LOGIN,
            javaClass.simpleName,
            FireBaseUtil.EVENT_TYPE_CLICK
        )
    }

    private fun isAuthorizedUser(userRoles: List<UserRole>?, resource: String): Boolean {
        for (userRole in userRoles!!) {
            if (userRole.resource.equals(resource, ignoreCase = true)) {
                Constant.userRole = userRole
                return true
            }
        }
        return true //TODO Make it FALSE before creating release.
    }
}


/*private fun <T> MutableLiveData<T>.postValue(success: T) {
    postValue(success)
}*/
