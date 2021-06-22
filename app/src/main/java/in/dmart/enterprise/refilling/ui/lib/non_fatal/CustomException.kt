package `in`.dmart.enterprise.refilling.ui.lib.non_fatal

import `in`.dmart.apilibrary.constant.ConstantParameters

class CustomException(tag: String, code: String) : Exception(tag) {
    private fun getClassName(tag: String, code: String?): String {
        return if (code != null && !code.isEmpty()) {
            if (code.equals(ConstantParameters.Retrofit_Failure, ignoreCase = true)) {
                code
            } else {
                "Error Code : $code"
            }
        } else {
            tag
        }
    }

    private fun getCode(code: String): Int {
        return try {
            code.toInt()
        } catch (e: Exception) {
            0
        }
    }

    init {
        val stackTrace = stackTrace
        val newStackTrace = arrayOfNulls<StackTraceElement>(stackTrace.size + 1)
        System.arraycopy(stackTrace, 0, newStackTrace, 1, stackTrace.size)
        val className = getClassName(tag, code)
        newStackTrace[0] = StackTraceElement(className, tag, className, getCode(code))
        setStackTrace(newStackTrace)
        println("NON-FATAL : $tag")
    }
}