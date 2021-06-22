package `in`.dmart.apilibrary.util

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

object Logger {
    fun log(Tag: String?, message: String?) {
        var tag = Tag
        var msg = message
        if (Tag == null){
            tag = ""
        }
        if (msg == null){
            msg = ""
        }
        Log.d(tag, msg)
    }

    // LOGS
    // LOGS
    val loggingInceptor: HttpLoggingInterceptor
        get() {
            val loggingInceptor = HttpLoggingInterceptor()
            loggingInceptor.setLevel(HttpLoggingInterceptor.Level.BASIC) // LOGS
            loggingInceptor.setLevel(HttpLoggingInterceptor.Level.BODY) // LOGS
            return loggingInceptor
        }
}