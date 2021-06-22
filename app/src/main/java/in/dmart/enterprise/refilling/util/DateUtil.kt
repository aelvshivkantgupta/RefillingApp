package `in`.dmart.enterprise.refilling.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    var YYYYMMDD = "yyyy-MM-dd"
    var DDMMMYYYY = "dd-MMM-yyyy"
    var YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss"
    var DDMMYYYYHHMMSSAMPM = "dd-MM-yyyy  hh:mm:ss a"
    var DDMMYYYYHHMMAMPM = "dd-MM-yyyy  hh:mm a"
    var HHmmss = "HH:mm:ss"
    var hhmm_a = "hh:mm a"
    fun getDate(date: Date?, format: String?): String {
        val sdf = SimpleDateFormat(format, Locale.US)
        return sdf.format(date)
    }

    fun getDate(date: String?, passDateFormat: String?, returnFormat: String?): String {
        try {
            val sdf = SimpleDateFormat(passDateFormat, Locale.US)
            return getDate(sdf.parse(date), returnFormat)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getCurrentDate(format: String?): String {
        val sdf = SimpleDateFormat(format, Locale.US)
        return sdf.format(Date())
    }


}