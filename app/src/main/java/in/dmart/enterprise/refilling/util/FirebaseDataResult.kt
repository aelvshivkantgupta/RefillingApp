package `in`.dmart.enterprise.refilling.util

interface FirebaseDataResult<T> {
    fun onResult(isDataExist: Boolean, `object`: T)
    fun onFailed(error: Any?)
}