package `in`.dmart.enterprise.refilling.ui.lib

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.di.RemoteConfig
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by anamika.chavan on 20-06-2018.
 */
@HiltAndroidApp
class Application : android.app.Application() {
    @Inject
    lateinit var remoteConfig: RemoteConfig
    private var apiKey //= mInstance.getString(R.string.slackwebhookkey);
            : String? = null
    private val crashName = "Application-Android" // bot name
    private val iconUrl = "http://blog.paprix.com/wp-content/uploads/2014/10/Android-error.png" // image url
    override fun onCreate() {
        super.onCreate()
        //Fabric.with(this, new Crashlytics());
        instance = this
        ApiUrls.initialize( getString(R.string.baseurl))
        //CachingManager.saveAppContext(this);
       // slackcrashreporter()
        remoteConfig.fetchRemoteConfig(object :  `in`.dmart.enterprise.refilling.listener.OnRemoteConfigFetched {
            override fun remoteConfig() {

            }
        })
    }

    /*private fun slackcrashreporter() {
        // Slack Reporter
        apiKey = getString(R.string.slackwebhookkey)
        val reporter: IReporter = SlackReporter.create(apiKey, crashName, iconUrl)
        Bee.init(this, reporter)
        Bee.clearLog()
    }
*/
    companion object {
        var instance: Application? = null
            private set

        val context: Context?
            get() = instance
    }
}