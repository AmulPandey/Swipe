package com.example.swipe.Networking

import com.example.swipe.model.screen1.Product
import com.example.swipe.model.screen2.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductApiService {
    @GET("https://app.getswipe.in/api/public/get")
    suspend fun getProducts(): List<Product>

    @Multipart
    @POST("https://app.getswipe.in/api/public/add")
    suspend fun addProduct(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody,
        @Part files: MultipartBody.Part?
    ): ProductResponse
}
