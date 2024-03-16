package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL =  "https://buyin-n-sellout-dd59ae5ce084.herokuapp.com/api/"

    val apiService: ProductAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductAPI::class.java)
    }
}
