package `in`.dmart.enterprise.refilling.di

import `in`.dmart.apilibrary.lib.GSONUtil
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref  @Inject constructor(@ApplicationContext private val context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("Refilling", Context.MODE_PRIVATE)

    @SuppressLint("ApplySharedPref")
    fun putString(key: String?, data: String) {
        preferences.edit().putString(key, data.trim { it <= ' ' }).commit()
    }

    @SuppressLint("ApplySharedPref")
    fun putBoolean(key: String?, data: Boolean) {
        preferences.edit().putBoolean(key, data).commit()
    }

    fun getString(key: String?): String? {
        return preferences.getString(key, "")
    }

    fun getBoolean(key: String?): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun saveObject(key: String?, `object`: Any?) {
        putString(key, GSONUtil.objectToJsonString(`object`))
    }

    fun <T:Any> getObject(key: String?, classOfT: Class<T>?): T? {
        return try {
            val reader = getString(key)
            GSONUtil.fromJson(reader, classOfT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        const val ADV_ID = "google_adv_id"
        public const val USERNAME = "UserName"
        public const val USERID = "UserId"
        public const val USERPWD = "UserPwd"
        public const val USERPIN = "UserPIN"
        public const val USERACCESS = "UserAccess"
        const val REMOTE_CONFIG = "remoteConfig"
    }

}