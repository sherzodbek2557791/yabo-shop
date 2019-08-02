package uz.kvikk.yabo

import com.warrenstrange.googleauth.GoogleAuthenticator


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val username = "leonbek"
//        userName: leonbek
//        secretKey: D2ZDHEVGYXFFBQQA
//        validationCode: 541674
//        scratchCodes: [80941957, 61894258, 92711077, 94372877, 86434354]
//        D2ZDHEVGYXFFBQQA
//        val secretKey = generateKey(username)
//        println(secretKey)
        val secretKey = validity("D2ZDHEVGYXFFBQQA", 311128)
        println(secretKey)
    }

    @JvmStatic
    fun generateKey(username: String): String{
        val gAuth = GoogleAuthenticator()
        gAuth.credentialRepository = ICredentialRepositoryImpl()
        val key = gAuth.createCredentials(username)
        return key.key
    }

    @JvmStatic
    fun validity(secret: String, code: Int): Boolean{
        val gAuth = GoogleAuthenticator()
        gAuth.credentialRepository = ICredentialRepositoryImpl()
//        val code = gAuth.getTotpPassword(secret)
//        println(code)
        return gAuth.authorize(secret, code, System.currentTimeMillis())
    }

}
