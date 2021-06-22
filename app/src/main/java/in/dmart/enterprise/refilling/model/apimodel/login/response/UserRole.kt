package `in`.dmart.enterprise.refilling.model.apimodel.login.response

import java.io.Serializable

class UserRole : Serializable {
    var resource: String? = null
    var userRoles: List<String>? = null

}