package `in`.dmart.apilibrary.constant

import `in`.dmart.apilibrary.util.Logger
import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ApiUrls {
    var baseUrl: String? = null
    //var headerNodeID = ""
    fun initialize(baseURL: String) {
        Logger.log("API", "URL----$baseURL")
        baseUrl = baseURL
    }

    var headerTokenn = "dsfs"

    const val API_POST_LOGIN_USERNAME = "/api/auth/v1/login"
    const val API_POST_LOGOUT = "/api/auth/v1/logout"
    const val API_GET_ROW_LIST="/api/v1/refilling/articles"
    const val API_GET_ARTICLES_BY_KEY = "/api/v1/refilling/articles/bykey"
    const val API_GET_ARTICLES_BY_ROW="/api/v1/refilling/articles/byrows"
    const val API_POST_CREATE_OR_UPDATE_TASK="/api/v1/refilling/articles/tasks"
    const val API_GET_TASK_DETAILS="/api/v1/refilling/articles/tasks/bykey"
    const val API_POST_CLOSE_TASK = "/api/v1/refilling/articles/tasks/close"

}