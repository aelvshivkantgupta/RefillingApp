package `in`.dmart.enterprise.refilling.ui.view.activity.login

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.content.ApiResponse
import `in`.dmart.apilibrary.content.WebServiceClass
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.ActivityBaseBinding
import `in`.dmart.enterprise.refilling.databinding.ActivityLoginBinding
import `in`.dmart.enterprise.refilling.model.apimodel.login.request.LoginRequest
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.view.activity.dashboard.DashboardActivity
import `in`.dmart.enterprise.refilling.ui.viewmodel.login.LoginViewModel
import `in`.dmart.enterprise.refilling.util.AppUtil
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.mindorks.framework.mvvm.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.Objects

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    val loginViewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_login)
        dataBinding.lifecycleOwner = this
        dataBinding.loginViewModel = loginViewModel
        showActionBar(true)
        setLoginFieldObserver()
        setLoginResponseObserver()
    }

    private fun setLoginFieldObserver() {
        loginViewModel.userData.observe(this, Observer {
            if (TextUtils.isEmpty(Objects.requireNonNull(it).userId)) {
                dataBinding.txtUserName.error = getString(R.string.err_login)
                dataBinding.txtUserName.requestFocus()
            } else if (TextUtils.isEmpty(Objects.requireNonNull(it).password)) {
                dataBinding.txtPassword.error = getString(R.string.err_pwd)
                dataBinding.txtPassword.requestFocus()
            } else {
                loginViewModel.sendLoginRequest(it)
            }
        })

    }

    private fun setLoginResponseObserver() {
        loginViewModel.loginResp.observe(this, Observer {
            val intent = Intent(this@LoginActivity,DashboardActivity::class.java)
            startActivity(intent)
        })

    }

    /*fun setObserver(){
        loginViewModel.loginResp.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list ->
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    //Handle Error
                }
            }
        } )
    }*/

}