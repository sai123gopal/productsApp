package com.saigopal.swipetest.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @SerializedName("message") var message        : String?         = null,
    @SerializedName("product_details") var productDetails : ProductDetails? = ProductDetails(),
    @SerializedName("product_id") var productId      : Int?            = null,
    @SerializedName("success") var success        : Boolean?        = null
)
