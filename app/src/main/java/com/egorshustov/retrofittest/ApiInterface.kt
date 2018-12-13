package com.egorshustov.retrofittest

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @GET("api_test_retrofit/tasks")
    fun apiGetRequest(): Call<List<Item>>

    @GET("api_test_retrofit/tasks/{id}")
    fun apiGetRequestWithAlias(@Path("id") id: String): Call<Item>

    @POST("api_test_retrofit/tasks")
    fun apiPostRequest(@Body item: Item): Call<Item>

    @PUT("api_test_retrofit/tasks/{id}")
    fun apiPutRequestWithAlias(@Path("id") id: String, @Body item: Item): Call<String>

    @DELETE("api_test_retrofit/tasks/{id}")
    fun apiDeleteRequestWithAlias(@Path("id") id: String): Call<Item>

    companion object Factory {
        private const val BASE_URL = "https://trubin23.ru/"
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}