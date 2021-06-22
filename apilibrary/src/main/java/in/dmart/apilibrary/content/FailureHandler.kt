package `in`.dmart.apilibrary.content

interface FailureHandler {
    fun <T> onFailure(url: String, reqBody: Any?, t: T)
}