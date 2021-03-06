package club.bweakfast.foodora.auth

import club.bweakfast.foodora.StorageService
import club.bweakfast.foodora.util.mapResponse
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by silve on 3/4/2018.
 */

open class AuthenticationService @Inject constructor(private val storageService: StorageService) {
    var token: String?
        get() = storageService.token
        set(value) {
            storageService.token = value
        }

    val isLoggedIn
        get() = storageService.isLoggedIn

    fun login(email: String, password: String): Single<TokenResponse> {
        return if (email == "banana@apple.ca" && password == "banana") {
            storageService.token = "keyboardcat"
            Single
                .just(Response.success(TokenResponse(token!!)))
                .delay(2, TimeUnit.SECONDS)

        } else {
            Single.just(
                Response.error(
                    401,
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{ \"error\": false, \"data\": \"User and password has incorrect combination\" }"
                    )
                )
            )
        }
            .mapResponse()
    }

    fun register(name: String, email: String, password: String): Single<TokenResponse> {
        return Single.just(Response.success(TokenResponse(token!!))).mapResponse()
    }
}