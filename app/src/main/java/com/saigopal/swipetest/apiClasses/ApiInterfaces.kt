package com.saigopal.swipetest.apiClasses

import com.saigopal.swipetest.models.ApiResponse
import com.saigopal.swipetest.models.ProductsModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterfaces {
    @GET("get")
    fun getProducts(): Call<List<ProductsModel>>

    @Multipart()
    @POST("add")
    fun addProduct(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody,
        @Part image: MultipartBody.Part
    ):Call<ApiResponse>
}