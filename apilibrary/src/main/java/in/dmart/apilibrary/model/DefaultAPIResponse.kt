package `in`.dmart.apilibrary.model

import com.google.gson.annotations.Expose

class DefaultAPIResponse {
    @Expose
    var success: Boolean? = null

    @Expose
    var returnMessage: String? = null

}