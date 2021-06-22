package `in`.dmart.apilibrary.util

import okhttp3.logging.HttpLoggingInterceptor

object Logger {
    fun log(Tag: String?, message: String?) {
       /* var tag = Tag
        var msg = message
        if (Tag == null){
            tag = ""
        }
        if (msg == null){
            msg = ""
        }
        Log.d(tag, msg)*/
    }

    // loggingInceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);// LOGS
    // loggingInceptor.setLevel(HttpLoggingInterceptor.Level.BODY);// LOGS
    val loggingInceptor: HttpLoggingInterceptor
        get() =// loggingInceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);// LOGS
                // loggingInceptor.setLevel(HttpLoggingInterceptor.Level.BODY);// LOGS
            HttpLoggingInterceptor()
}