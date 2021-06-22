package `in`.dmart.enterprise.refilling.util

import `in`.dmart.apilibrary.lib.GSONUtil
import `in`.dmart.apilibrary.util.Logger
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.listerner.OnRemoteConfigFetched
import `in`.dmart.enterprise.refilling.model.remote_config.Properties
import `in`.dmart.enterprise.refilling.ui.view.activity.SplashScreen
import `in`.dmart.enterprise.refilling.ui.lib.Application
import `in`.dmart.enterprise.refilling.ui.lib.Application.Companion.context
import `in`.dmart.enterprise.refilling.di.SharedPref
import android.content.Context
import android.os.Bundle
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.*

object FireBaseUtil {
    //firebase event
    const val EVENT_NAME_LOGIN = "Login_Successful"
    const val EVENT_NAME_LOGOUT = "Logout"

    const val EVENT_TYPE_DISPLAY = "Display"
    const val EVENT_TYPE_CLICK = "Click"
    const val EVENT_TYPE_SCAN = "Scan"

    private val SCREEN_MAP = HashMap<String, String>()
    private const val REMOTE_CONFIG_FETCH_INTERVAL = 1800 //30 Min
    fun getScreenViewEvent(screenName: String): String {
        var event = SCREEN_MAP[screenName]
        event = if (event == null || event.isEmpty()) {
            if (screenName.contains("Screen")) screenName + "_V" else screenName + "_Screen_V"
        } else {
            event + "_Screen_V"
        }
        return event
    }

    fun getScanEvent(screenName: String, scanBy: String): String {
        var event = SCREEN_MAP[screenName]
        if (event == null || event.isEmpty()) {
            event = screenName
        }
        event = event + "_Scan_" + scanBy
        return event
    }

    @JvmOverloads
    fun sendEvent(firebaseAnalytics: FirebaseAnalytics?, eventName: String?, screenName: String?, action: String?, text: String? = "") {
        var firebaseAnalytics = firebaseAnalytics
        var eventName = eventName
        var screenName = screenName
        var action = action
        var text = text
        try {
            if (firebaseAnalytics == null) {
                firebaseAnalytics = FirebaseAnalytics.getInstance(context!!)
            }
            eventName = eventName ?: ""
            screenName = screenName ?: ""
            action = action ?: ""
            text = text ?: ""
            // String userType = Constant.userRole==null?"":Constant.userRole.getResource();
            if (Constant.userId != null && !Constant.userId!!.isEmpty()) {
                firebaseAnalytics.setUserId(Constant.userId)
            }
            firebaseAnalytics.setUserProperty("LoginId", Constant.userId)
            val bundle = Bundle()
            bundle.putString("LoginId", Constant.userId)
            // bundle.putString("UserType", userType);
            // bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, eventName);
            bundle.putString("ActivityName", screenName)
            bundle.putString("Text", text)
            bundle.putString("Action", action)
            firebaseAnalytics.logEvent(eventName, bundle)

            // TODO: Use your own attributes to track content views in your app
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    init {
        SCREEN_MAP[SplashScreen::class.java.simpleName] = "Splash"
    }
}