package com.saigopal.swipetest

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.saigopal.swipetest.databinding.ActivityUploadProductBinding
import com.saigopal.swipetest.databinding.ProductTypeSelectBottomSheetBinding
import com.saigopal.swipetest.viewModels.UploadProductViewModel

class UploadProductActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUploadProductBinding
    lateinit var uploadProductViewModel:UploadProductViewModel
    lateinit var progressDialog:ProgressDialog
    private val dataList = listOf(
        "Fashion",
        "Appliances",
        "Mobiles",
        "Home",
        "Sports",
        "Toys",
        "Kitchen"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_upload_product)
        binding.lifecycleOwner = this

        uploadProductViewModel = ViewModelProvider(this)[UploadProductViewModel::class.java]
        binding.viewModel = uploadProductViewModel
        binding.click = Click()

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading...")


        observeLiveData()

    }

    private fun observeLiveData() {
       uploadProductViewModel.statusMsg.observe(this){
           if(it.isNotEmpty()){
               progressDialog.dismiss()
               Toast.makeText(this,it,Toast.LENGTH_LONG).show()
               if(it.equals("success")){
                   startActivity(Intent(this,MainActivity::class.java))
               }
           }
       }


    }

    inner class Click{

        fun post() {
            progressDialog.show()
            uploadProductViewModel.uploadProduct()
        }
        fun pickImage(){
            imagePicker()
        }
        fun back(){
            this@UploadProductActivity.onBackPressed();
            finish()
        }
        fun selectType(){
            openSelectProductType()
        }
    }

    private fun openSelectProductType() {
        val bottomSheetBinding:ProductTypeSelectBottomSheetBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),R.layout.product_type_select_bottom_sheet,null,false);

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetBinding.root)
        bottomSheetBinding.click = Click()
        bottomSheetBinding.viewModel = uploadProductViewModel

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)

        bottomSheetBinding.listProductType.adapter = adapter

        bottomSheetBinding.listProductType.setOnItemClickListener{ _, _, position, _ ->
            run {
                val selectedItem = dataList[position]
                uploadProductViewModel.type.value = selectedItem
                dialog.dismiss()
            }
        }

        dialog.show()

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.getOnBackPressedDispatcher()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0){
            if(resultCode== RESULT_OK){
                val uri = data!!.data
                binding.postImg.setImageURI(uri)
                uploadProductViewModel.imageUri.postValue(uri)
            }
        }

    }


    fun imagePicker(){
        ImagePicker.with(this@UploadProductActivity)
            .galleryOnly()
            .cropSquare()
            .galleryMimeTypes(
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpeg"
                )
            )
            .compress(120)
            .start(0)
    }

}