package com.example.newfakestooreapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Add_Products : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_products)


        val repository = ProductRepository(RetrofitInstance.api)
        viewModel = ProductViewModel(repository)



        //Get reference
        val etTitle = findViewById<EditText>(R.id.et_title)
        val etBrand = findViewById<EditText>(R.id.et_brand)
        val etModel = findViewById<EditText>(R.id.et_model)
        val etColor = findViewById<EditText>(R.id.et_color)
        val etCategory = findViewById<EditText>(R.id.et_category)
        val etDiscount = findViewById<EditText>(R.id.et_discount)
        val etPrice = findViewById<EditText>(R.id.et_price)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)


        val discountString = etDiscount.text.toString()
        val priceString = etPrice.text.toString()

        btnSubmit.setOnClickListener {
            val title = etTitle.text.toString()
            val brand = etBrand.text.toString()
            val model = etModel.text.toString()
            val color = etColor.text.toString()
            val category = etCategory.text.toString()

            val discount = discountString.toDoubleOrNull() ?: 0.0
            val price = priceString.toDoubleOrNull() ?: 0.0

            if (title.isNotEmpty()&&
                brand.isNotEmpty()&&
                model.isNotEmpty()&&
                color.isNotEmpty()&&
                category.isNotEmpty()){


                val product = Product(
                    title = title,
                    brand = brand,
                    model = model,
                    color =  color,
                    category = category,
                    discount = discount,
                    price = price
                )
                viewModel.addProducts(product)
                Toast.makeText(this,"Add : ${product.title}",Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(this,"Please fill all fields",Toast.LENGTH_LONG).show()
            }

        }
//        viewModel.productLiveData.observe(this) { product ->
//            Toast.makeText(this,"Successfully Add Product ${product}",Toast.LENGTH_SHORT).show()
//
//        }


    }

}