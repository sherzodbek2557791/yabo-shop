package uz.kvikk.yabo

import com.warrenstrange.googleauth.ICredentialRepository

class ICredentialRepositoryImpl: ICredentialRepository {
    var userName: String? = null
    var secretKey: String? = null
    override fun saveUserCredentials(userName: String?, secretKey: String?, validationCode: Int, scratchCodes: MutableList<Int>?) {
        this.secretKey = secretKey
        this.userName = userName
        println("userName: $userName")
        println("secretKey: $secretKey")
        println("validationCode: $validationCode")
        println("scratchCodes: $scratchCodes")
    }

    override fun getSecretKey(userName: String?): String {
        return this.secretKey!!
    }
}