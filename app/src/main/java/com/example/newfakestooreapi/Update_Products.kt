package com.example.newfakestooreapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Update_Products : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_products)

        val repository = ProductRepository(RetrofitInstance.api)
        viewModel = ProductViewModel(repository)

        val edId = findViewById<EditText>(R.id.et_Id)
        val edTitle = findViewById<EditText>(R.id.et_title)
        val edBrand = findViewById<EditText>(R.id.et_brand)
        val edModel = findViewById<EditText>(R.id.et_model)
        val edColor = findViewById<EditText>(R.id.et_color)
        val edCategory = findViewById<EditText>(R.id.et_category)
        val edDiscount = findViewById<EditText>(R.id.et_discount)
        val edPrice = findViewById<EditText>(R.id.et_price)

        val btnUpdate = findViewById<Button>(R.id.btn_update)
        btnUpdate.setOnClickListener {

            val id = edId.text.toString().toInt()
            val title = edTitle.text.toString()
            val brand = edBrand.text.toString()
            val model = edModel.text.toString()
            val color = edColor.text.toString()
            val category = edCategory.text.toString()
            val discount = edDiscount.text.toString().toDoubleOrNull() ?: 0.0
            val price = edPrice.text.toString().toDoubleOrNull() ?: 0.0


            val product = Product(
                id = id,
                title = title,
                brand = brand,
                model = model,
                color = color,
                category = category,
                discount = discount,
                price = price

            )
            viewModel.updateProduct(id, product)

        }

        viewModel.productLiveData.observe(this){updateProduct ->
            Toast.makeText(this,"Product Update Successfully",Toast.LENGTH_SHORT).show()

        }
    }
}