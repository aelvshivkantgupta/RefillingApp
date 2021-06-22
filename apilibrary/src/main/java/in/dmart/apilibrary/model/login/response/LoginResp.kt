package `in`.dmart.apilibrary.model.login.response

import java.io.Serializable

class LoginResp : Serializable {
    var additionalAttributes: List<Any>? = null
    var additionalProperties: AdditionalProperties? = null
    var ca_token: String? = null
    var city: String? = null
    var createdAt: String? = null
    var domain: String? = null
    var fcCodes: List<String>? = null
    var fullName: String? = null
    var message: String? = null
    var userId: String? = null
    private val user_id: String? = null
    var userRoles: List<UserRole>? = null
    var validity: String? = null


}