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

    const val API_GET_ROW_LIST="/api/refilling/v1/articles"
    const val API_GET_ARTICLES_BY_KEY = "/api/refilling/v1/articles/bykey"
    const val API_GET_ARTICLES_BY_ROW="/api/refilling/v1/articles/byrows"
    const val API_POST_CREATE_OR_UPDATE_TASK="/api/refilling/v1/articles/tasks"
    const val API_GET_TASK_DETAILS="/api/refilling/v1/articles/tasks/bykey"
    const val API_POST_CLOSE_TASK = "/api/refilling/v1/articles/tasks/close"

}