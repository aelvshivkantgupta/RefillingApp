package `in`.dmart.enterprise.refilling.model.apimodel.login.response

import java.io.Serializable

class AdditionalProperties : Serializable {
    var OrganizationCode: String? = null
    var UserGroupName: String? = null
    var ForceUserToChangePassword: String? = null
        get() = if (field == null) "" else field
    private var PasswordExpiresInDays: String? = null

    fun getPasswordExpiresInDays(): String {
        //return PasswordExpiresInDays==null?"-1":PasswordExpiresInDays;
        return if (PasswordExpiresInDays == null || PasswordExpiresInDays!!.isEmpty()) "-1" else PasswordExpiresInDays!!
    }

    fun setPasswordExpiresInDays(passwordExpiresInDays: String?) {
        PasswordExpiresInDays = passwordExpiresInDays
    }
}