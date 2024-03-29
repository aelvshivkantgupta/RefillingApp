package `in`.dmart.enterprise.refilling.ui.view.activity.login

import `in`.dmart.apilibrary.dialog.CustomDialog
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.apiutil.Status
import `in`.dmart.enterprise.refilling.databinding.ActivityLoginBinding
import `in`.dmart.enterprise.refilling.ui.view.activity.BaseActivity
import `in`.dmart.enterprise.refilling.ui.view.activity.dashboard.DashboardActivity
import `in`.dmart.enterprise.refilling.ui.viewmodel.login.LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private var progressDialog: CustomDialog? = null

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_login)
        dataBinding.lifecycleOwner = this
        dataBinding.loginViewModel = loginViewModel
        showLogo(View.VISIBLE)
        setTitle(resources.getString(R.string.login))
        setLoginFieldObserver()
        setObserver()
        //setLoginResponseObserver()
        showBackButton(false)

    }

    private fun setLoginFieldObserver() {
        loginViewModel.userData.observe(this, {
            when {
                TextUtils.isEmpty(Objects.requireNonNull(it).userId) -> {
                    dataBinding.txtUserName.error = getString(R.string.err_login)
                    dataBinding.txtUserName.requestFocus()
                }
                TextUtils.isEmpty(Objects.requireNonNull(it).password) -> {
                    dataBinding.txtPassword.error = getString(R.string.err_pwd)
                    dataBinding.txtPassword.requestFocus()
                }
                else -> {
                    loginViewModel.sendLoginRequest(it)
                }
            }
        })

    }

    /*private fun setLoginResponseObserver() {
        *//*loginViewModel.loginResp.observe(this, {
            when (it.status) {
                true -> {
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(intent)
                }

                false -> {
                    showToast(it.message)
                }
            }
        })*//*
        loginViewModel.loginResp.observe(this, {
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(intent)
        })

    }*/

    private fun setObserver() {
        loginViewModel.loginResp.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        progressDialog?.finish()
                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        startActivity(intent)
                    }
                }
                Status.LOADING -> {
                    progressDialog = CustomDialog(this)
                    progressDialog?.show()
                }
                Status.ERROR -> {
                    progressDialog?.finish()
                    if (it.message?.isNotEmpty() == true) {
                        showToast(it.message)
                    }
                }
            }
        })
    }

}