package `in`.dmart.apilibrary.model

/**
 * Created by anamika.chavan on 13-02-2018.
 */
class PostResponse {
    var Status: String? = null
    var Message: String? = null

    override fun toString(): String {
        return "PostResponse{" +
                "Status='" + Status + '\'' +
                ", Message='" + Message + '\'' +
                '}'
    }
}