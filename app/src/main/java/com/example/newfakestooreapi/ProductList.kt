package com.example.newfakestooreapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductList : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val selectCategory = intent.getStringExtra("CategoryName") ?: return

        val cateRvView = findViewById<RecyclerView>(R.id.categoriesView)
        cateRvView.layoutManager =LinearLayoutManager(this)

        productsAdapter= ProductsAdapter(listOf()){
            val obj1 = MainActivity()
            obj1.openDetailsScreen()
        }
        cateRvView.adapter = productsAdapter


        val repository = ProductRepository(RetrofitInstance.api)
        viewModel = ProductViewModel(repository)

        viewModel.productLiveData.observe(this){products ->
            productsAdapter.updateProduct(products)
        }

        viewModel.getProductByCategory(selectCategory)


        val ascBtn = findViewById<Button>(R.id.btnAsc)
        val descBtn = findViewById<Button>(R.id.btnDesc)

        ascBtn.setOnClickListener {

            viewModel.getProductByCategoryAndSort(selectCategory, "asc")
        }

        descBtn.setOnClickListener {

            viewModel.getProductByCategoryAndSort(selectCategory, "desc")
        }

        viewModel.getProductByCategoryAndSort(selectCategory, "desc")

    }
}