package `in`.dmart.enterprise.refilling.util

import `in`.dmart.apilibrary.constant.ConstantParameters
import `in`.dmart.apilibrary.lib.GSONUtil
import `in`.dmart.apilibrary.model.response_model.ErrorResponseModel
import `in`.dmart.enterprise.refilling.constant.Constant
import `in`.dmart.enterprise.refilling.ui.lib.Application.Companion.context
import `in`.dmart.enterprise.refilling.ui.lib.non_fatal.CustomException
import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.io.IOException
import java.util.*

object AppUtil {
    fun showToast( msg: String?) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun logNonFatalErrors(TAG: String?, req: Any?, url: String?, error: Any?) {
        try {
            val userId = if (Constant.userId == null) "" else Constant.userId!!
            FirebaseCrashlytics.getInstance().setCustomKey("TAG", TAG!!)
            FirebaseCrashlytics.getInstance().setCustomKey("UserID", userId)
            FirebaseCrashlytics.getInstance().setCustomKey("Req", GSONUtil.objectToJsonString(req))
            FirebaseCrashlytics.getInstance().setCustomKey("url", url!!)
            FirebaseCrashlytics.getInstance().setCustomKey("error", error?.let { getResponseError(it) }?:"")
            FirebaseCrashlytics.getInstance().recordException(CustomException(url,error?.let { getResponseCode(it) }?:""))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getResponseCode(error: Any): String {
        try {
            if (error is ErrorResponseModel) {
                return (error as ErrorResponseModel).responseCode.toString() + ""
            } else if (error is Throwable) {
                return ConstantParameters.Retrofit_Failure
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getResponseError(error: Any): String? {
        try {
            if (error is Throwable) {
                return error.message
            } else if (error is ErrorResponseModel) {
                return (error as ErrorResponseModel).errorResponse
            }
            return error.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun <T> addToBeginningOfArray(elements: Array<T>, element: T): Array<T> {
        val newArray = Arrays.copyOf(elements, elements.size + 1)
        newArray[0] = element
        System.arraycopy(elements, 0, newArray, 1, elements.size)
        return newArray
    }

    fun closeKeyboard(view: View) {
        Handler().postDelayed({
            try {
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, 1)
    }

    fun <T> getModelFromAssets(context: Context, fileName: String?, modelClass: Class<T>?): T? {
        val jsonString: String
        jsonString = try {
            val `is` = context.assets.open(fileName!!)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, charset("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return GSONUtil.fromJson(jsonString, modelClass)
    }
}