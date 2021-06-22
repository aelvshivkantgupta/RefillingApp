package `in`.dmart.enterprise.refilling.model.remote_config

import `in`.dmart.enterprise.refilling.di.SharedPref
import android.content.Context
import javax.inject.Inject

class Properties {


    companion object {
        private var properties: Properties? = null

        fun getInstance(pref: SharedPref): Properties {
            if (properties == null) {
                properties = pref.getObject<Properties>(SharedPref.REMOTE_CONFIG, Properties::class.java)
            }
            return if (properties == null) Properties() else properties!!
        }

        fun setProperties(properties: Properties?) {
            Properties.properties = properties
        }
    }
}