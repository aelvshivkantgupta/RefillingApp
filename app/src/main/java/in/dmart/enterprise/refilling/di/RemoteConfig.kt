package `in`.dmart.enterprise.refilling.di

import `in`.dmart.apilibrary.lib.GSONUtil
import `in`.dmart.enterprise.refilling.BuildConfig
import `in`.dmart.enterprise.refilling.listener.OnRemoteConfigFetched
import `in`.dmart.enterprise.refilling.model.remote_config.Properties
import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfig @Inject constructor(@ApplicationContext val context:Context,val pref: SharedPref) {

     val remoteConfig:FirebaseRemoteConfig = Firebase.remoteConfig
    var fetchIntervalInSeconds:Long = 3600

    init {
        try{
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = fetchIntervalInSeconds
        }
        remoteConfig!!.setConfigSettingsAsync(configSettings)
        //remoteConfig!!.setDefaultsAsync(R.xml.remote_config_defaults)

    } catch (e: Exception) {
        e.printStackTrace()
    }
    }
    fun fetchRemoteConfig(onRemoteConfig: OnRemoteConfigFetched){
        remoteConfig.fetchAndActivate().addOnCompleteListener { task: Task<Boolean?> ->
            try {
                if (task.isSuccessful) {
                    val configParam = remoteConfig.getString(BuildConfig.BUILD_TYPE)
                    var properties: Properties? =
                        GSONUtil.fromJson(configParam, Properties::class.java)
                    if (properties == null) {
                        properties = Properties()
                    }
                    context?.let {
                        pref.saveObject(SharedPref.REMOTE_CONFIG, properties)
                    }
                    //Properties.setProperties(properties);
                    onRemoteConfig.remoteConfig()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}