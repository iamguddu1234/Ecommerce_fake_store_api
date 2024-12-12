package com.example.newfakestooreapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var categoryAdapter: CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.rv);
        recyclerView.layoutManager = LinearLayoutManager(this)
        openDetailsScreen()
        recyclerView.adapter = productsAdapter


        val repository = ProductRepository(RetrofitInstance.api)
        viewModel = ProductViewModel(repository)


        viewModel.productLiveData.observe(this) { products ->
            productsAdapter.updateProduct(products)

        }

        //ITS VERY IMPORTANT And Its Working
//        //Limited Product api call from here
//        viewModel.getLimitedProduct(2)


        viewModel.getAllProducts()


        val cateRV = findViewById<RecyclerView>(R.id.crv)
        cateRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(listOf()) { category ->
            val intent = Intent(this, ProductList::class.java)
            intent.putExtra("CategoryName", category)
            startActivity(intent)

        }
        cateRV.adapter = categoryAdapter

        viewModel.categoriesLiveData.observe(this) { category ->
            categoryAdapter.updateCategories(category)
        }
        viewModel.getCategories()






        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == productsAdapter.itemCount - 1) {
                    viewModel.loadNextPage()
                }

            }
        })

//        viewModel.loadNextPage()


        val add = findViewById<Button>(R.id.addP);
        add.setOnClickListener {
            val intent = Intent(this, Add_Products::class.java)
            startActivity(intent)
        }

        val update = findViewById<Button>(R.id.updateP)
        update.setOnClickListener {
            val intent = Intent(this, Update_Products::class.java)
            startActivity(intent)
        }

        val delete = findViewById<Button>(R.id.deleteP)
        delete.setOnClickListener {
            val intent = Intent(this, Delete_product::class.java)
            startActivity(intent)
        }

    }

    fun openDetailsScreen() {
        productsAdapter = ProductsAdapter(listOf()) { product ->


//            openDetailsScreen()

            val intent = Intent(this, DetailScreen::class.java).apply {

                putExtra("image", product.image)

                putExtra("title", product.title)
                putExtra("des", product.description)
                putExtra("price", product.price)
                putExtra("brand", product.brand)
                putExtra("model", product.model)
                putExtra("color", product.color)
                putExtra("cate", product.category)
                putExtra("dis", product.discount)
            }
            startActivity(intent)

        }
    }


}