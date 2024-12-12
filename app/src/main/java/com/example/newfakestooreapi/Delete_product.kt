package com.example.newfakestooreapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.NumberFormatException

class Delete_product : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_product)

        val repository = ProductRepository(RetrofitInstance.api)
        viewModel = ProductViewModel(repository)

        val edID = findViewById<EditText>(R.id.et_Id)


        val btnDelete = findViewById<Button>(R.id.btn_delete)
        btnDelete.setOnClickListener {
            val id = edID.text.toString()

            if (id.isNotEmpty()) {
                try {
                    val ID = id.toInt()
                    viewModel.deleteProduct(ID)
                } catch (e: NumberFormatException) {

                    Toast.makeText(this, "Enter a Valid Id", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter the Product Id", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.deleteProductLiveData.observe(this) { isDelete ->
            if (isDelete) {
                Toast.makeText(this, "Product is Successfully Deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed To delete product", Toast.LENGTH_SHORT).show()
            }
        }


    }
}