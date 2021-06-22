package `in`.dmart.apilibrary.model

import com.google.gson.annotations.Expose

class AuthError {
    @Expose
    var error: String? = null

    @Expose
    var message: String? = null

    @Expose
    var path: String? = null

    @Expose
    var status: Long? = null

    @Expose
    var timestamp: String? = null

}