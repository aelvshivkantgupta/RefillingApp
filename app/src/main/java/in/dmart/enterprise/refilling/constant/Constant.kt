package `in`.dmart.enterprise.refilling.constant

import `in`.dmart.enterprise.refilling.model.apimodel.login.response.UserRole

object Constant {
   const val TAG_API_FAILURE: String="API failure error"
    public var userId:String? =""
    public var USER_NAME:String? =""
    const val SCAN_QR = "By_QR"
    const val SCAN_BAR = "By_Barcode"
    var userRole: UserRole? = null
}