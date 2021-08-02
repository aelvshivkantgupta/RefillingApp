package `in`.dmart.enterprise.refilling.constant

import `in`.dmart.apilibrary.model.login.response.UserRole

object Constant {

    const val REFILLING_APP = "refillingapp"
    const val TAG_API_FAILURE: String = "API failure error"
    const val SCAN_QR = "By_QR"
    const val SCAN_BAR = "By_Barcode"
    const val OBJ = "obj"

    var userId: String? = ""
    var USER_NAME: String? = ""
    var userRole: UserRole? = null
    var userName: String = ""
}