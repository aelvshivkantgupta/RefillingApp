package `in`.dmart.apilibrary.model.login.response

class LoginData {
    var userId: String? = null
    var userRole: String? = null
    var ca_token: String? = null
    var node: String? = null
    var fullName: String? = null

    override fun toString(): String {
        return "LoginData{" +
                "userId='" + userId + '\'' +
                ", userRole='" + userRole + '\'' +
                ", ca_token='" + ca_token + '\'' +
                ", node='" + node + '\'' +
                ", fullName='" + fullName + '\'' +
                '}'
    }
}