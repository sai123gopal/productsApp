package com.saigopal.swipetest.models

import com.google.gson.annotations.SerializedName

data class ProductsModel(
    @SerializedName("product_name")var productName:String,
    @SerializedName("price")var price:Double,
    @SerializedName("product_type")var productType:String,
    @SerializedName("tax")var tax:Double,
    @SerializedName("image")var image:String,
    )
