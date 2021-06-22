package `in`.dmart.apilibrary.content

interface ApiResponse<T:Any,E:Throwable?> {
    fun onSuccess(response: T)
    fun onFailure(error: E?)
}