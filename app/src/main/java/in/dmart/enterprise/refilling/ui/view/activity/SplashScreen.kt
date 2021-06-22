package `in`.dmart.enterprise.refilling.ui.view.activity

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.ActivitySplashBinding
import `in`.dmart.enterprise.refilling.ui.view.activity.login.LoginActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : BaseActivity<ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = putContentView(R.layout.activity_splash)
        showActionBar(false)
       // remoteConfig.init(this)
        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this, LoginActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000)

    }

}