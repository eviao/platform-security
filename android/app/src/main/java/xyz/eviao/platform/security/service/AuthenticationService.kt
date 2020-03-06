package xyz.eviao.platform.security.service

import io.reactivex.Observable
import retrofit2.http.*

interface AuthenticationService {

    @FormUrlEncoded
    @POST("/auth/login")
    fun login(
        @Header("Authorization") credential: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<Map<String, Any>>
}