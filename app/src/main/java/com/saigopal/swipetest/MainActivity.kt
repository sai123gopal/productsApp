package com.saigopal.swipetest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saigopal.swipetest.adapters.ProductsRecyclerAdapter
import com.saigopal.swipetest.databinding.ActivityMainBinding
import com.saigopal.swipetest.models.ProductsModel
import com.saigopal.swipetest.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding :ActivityMainBinding
    private var productsList = mutableListOf<ProductsModel>()
    lateinit var adapter: ProductsRecyclerAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.click = Click()
        binding.swipeRefreshLayout.isRefreshing = true

        adapter = ProductsRecyclerAdapter(this,productsList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        binding.swipeRefreshLayout.setOnRefreshListener {
                viewModel.getProducts()
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val topRowVerticalPosition =
                    if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                binding.swipeRefreshLayout.isEnabled = topRowVerticalPosition >= 0
            }
        })

        viewModel.getProducts()


        observeLiveData()

    }

    private fun observeLiveData() {
       viewModel.productsList.observe(this) {
           if (it.isNotEmpty()) {
               binding.swipeRefreshLayout.isRefreshing = false
               productsList.clear()
                productsList.addAll(it)
               adapter.notifyDataSetChanged()
           }
       }

        viewModel.errorString.observe(this){
            if(it.isNotEmpty()){
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        }

        viewModel.searchString.observe(this){
            if(it.isNotEmpty()){
                viewModel.searchProduct(it)
            }else{
                viewModel.getProducts()
            }
        }


    }


    inner class Click{
        fun openNewProduct(view: View){
            startActivity(Intent(view.context,UploadProductActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}