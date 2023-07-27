package com.saigopal.swipetest.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.saigopal.swipetest.apiClasses.ApiInterfaces
import com.saigopal.swipetest.apiClasses.RetrofitClient
import com.saigopal.swipetest.models.ProductsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class MainViewModel(application: Application) : AndroidViewModel(application) {

    var productsList:MutableLiveData<List<ProductsModel>> = MutableLiveData()
    private var retrofit = RetrofitClient.getInstance()
    var apiInterface: ApiInterfaces = retrofit.create(ApiInterfaces::class.java)
    var errorString:MutableLiveData<String> = MutableLiveData("")
    var searchString:MutableLiveData<String> = MutableLiveData("")

    fun getProducts(){
        apiInterface.getProducts().enqueue(object : Callback<List<ProductsModel>>{
            override fun onResponse(call: Call<List<ProductsModel>>, response: Response<List<ProductsModel>>) {
                if(response.isSuccessful ){
                    productsList.value = response.body()
                }else{
                    errorString.value = response.code().toString()
                }
            }

            override fun onFailure(call: Call<List<ProductsModel>>, t: Throwable) {
                errorString.value = t.localizedMessage
                Log.d("retrofit error", "onFailure: $t")
            }

        })
    }

    fun searchProduct(searchText:String) {
        val ml: MutableList<ProductsModel> = mutableListOf()
        val oldList = productsList.value
        if (oldList != null) {
            for (obj in oldList) {
                if (obj.productName.lowercase().contains(searchText.lowercase(Locale.getDefault()))
                    || obj.productType.lowercase().contains(searchText.lowercase(Locale.getDefault()))) {
                    ml.add(obj)
                }
            }
        }
        productsList.value = ml
    }

}