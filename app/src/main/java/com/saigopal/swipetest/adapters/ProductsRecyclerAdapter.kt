package com.saigopal.swipetest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.saigopal.swipetest.R
import com.saigopal.swipetest.databinding.ProductsItemBinding
import com.saigopal.swipetest.models.ProductsModel
import com.saigopal.swipetest.utils.DataBindingUtils

class ProductsRecyclerAdapter(
    private var context: Context,
    private var list: MutableList<ProductsModel>
) : RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: ProductsItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var binding:ProductsItemBinding
        init {
            binding = itemView
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val productsItemBinding:ProductsItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.products_item,parent,false);
        return ViewHolder(productsItemBinding);
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = list[position]
    }
}