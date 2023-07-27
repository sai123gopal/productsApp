package com.saigopal.swipetest.viewModels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.saigopal.swipetest.apiClasses.ApiInterfaces
import com.saigopal.swipetest.apiClasses.RetrofitClient
import com.saigopal.swipetest.models.ApiResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadProductViewModel(application: Application) : AndroidViewModel(application) {

    var imageUri : MutableLiveData<Uri> = MutableLiveData()
    private var retrofit = RetrofitClient.getInstance()
    private var apiInterface: ApiInterfaces = retrofit.create(ApiInterfaces::class.java)
    var statusMsg = MutableLiveData("")
    var name = MutableLiveData("")
    var type = MutableLiveData("")
    var price = MutableLiveData("")
    var tax = MutableLiveData("")
    private lateinit var filePart : MultipartBody.Part

    fun uploadProduct() {
       if(name.value!!.isEmpty()){
           statusMsg.value = "Please enter product name"
           return
       }
        if(price.value!!.isEmpty()){
            statusMsg.value = "Please enter product price"
            return
        }
        if(tax.value!!.isEmpty()){
            statusMsg.value = "Please enter product tax"
            return
        }
        if(type.value!!.isEmpty()){
            statusMsg.value = "Please select product type"
            return
        }

        filePart = if(imageUri.value != null) {
            val file =  File(imageUri.value!!.path!!)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files[]", file.name, requestFile)
        }else{
            MultipartBody.Part.createFormData("files[]", null.toString())
        }

        sendPost()
    }

    private fun sendPost() {
        val call = apiInterface.addProduct(
            name.value!!.toRequestBody("text/plain".toMediaTypeOrNull()),
            type.value!!.toRequestBody("text/plain".toMediaTypeOrNull()),
            price.value!!.toRequestBody("text/plain".toMediaTypeOrNull()),
            tax.value!!.toRequestBody("text/plain".toMediaTypeOrNull()),
            filePart
        )

        call.enqueue(object :Callback<ApiResponse>{
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if(response.isSuccessful){
                    statusMsg.value = "success"
                }else{
                    statusMsg.value = "Error"
                    Log.d("retrofit error", response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                statusMsg.value = t.localizedMessage
                Log.d("retrofit error", "onFailure: $t")
            }

        })
    }

}