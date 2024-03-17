package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductAPI {
    @GET("products/{productId}")
    fun getProduct(@Path("productId") productId: String): Call<Product>

    @GET("products")
    fun getProducts(): Call<List<Product>>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("account/process-register")
    fun createUser(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,

        ):retrofit2.Call<DefaultResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("account/login")
    fun login(

        @Field("email") email: String,
        @Field("password") password: String
    ):retrofit2.Call<DefaultResponse>


}
