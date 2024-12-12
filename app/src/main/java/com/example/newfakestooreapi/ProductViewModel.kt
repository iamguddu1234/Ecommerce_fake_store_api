package com.example.newfakestooreapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productsLiveData = MutableLiveData<List<Product>>()
    val productLiveData: LiveData<List<Product>> get() = _productsLiveData


    private val _categoriesLiveDate = MutableLiveData<List<String>>()
    val categoriesLiveData: LiveData<List<String>> get() = _categoriesLiveDate

    private val _deleteLivedata = MutableLiveData<Boolean>()
    val deleteProductLiveData: LiveData<Boolean> = _deleteLivedata


    private val compositeDisposable = CompositeDisposable()


    //for Page load API
    private var CURRENT_PAGE = 1
    private val TOTAL_PAGE = 3
    var isLoading = false


    fun getAllProducts() {
        compositeDisposable.add(
            repository.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ products ->
                    _productsLiveData.value = products

                }, { error ->
                    error.printStackTrace()
                    Log.e("Products ViewModel", "Error Getting all Products ${error.message}")
                })
        )
    }

    fun getCategories() {
        compositeDisposable.add(
            repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categoryResponse ->
                    _categoriesLiveDate.value = categoryResponse.categories
                }, { error ->
                    // Log full error response
                    if (error is HttpException) {
                        Log.e("Products ViewModel", "HTTP Error Code: ${error.code()}")
                        Log.e(
                            "Products ViewModel",
                            "HTTP Error Message: ${error.response()?.errorBody()?.string()}"
                        )
                    } else {
                        Log.e("Products ViewModel", "Error Getting Categories ${error.message}")
                    }
                })
        )
    }

    fun getProductByCategory(category: String) {
        compositeDisposable.add(
            repository.getProductByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ productResponse ->
                    _productsLiveData.value = productResponse.products
                }, { error ->
                    Log.e("ProductViewmodel", "Error${error.message}")

                }


                )
        )

    }


    fun getLimitedProduct(limit: Int) {
        compositeDisposable.add(
            repository.getLimitedProduct(limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ product ->
                    _productsLiveData.value = product.products

                }, { error ->
                    Log.e("ProductViewModel", "Error to show Limited Product ${error.message}")

                })
        )
    }


    fun getProductByCategoryAndSort(category: String, sort: String) {
        compositeDisposable.add(
            repository.getProductByCategoryAndSort(category, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ productResponse ->
                    _productsLiveData.value = productResponse.products

                }, { error ->
                    if (error is HttpException) {
                        Log.e("ProductViewModel", "HTTP Error Code : ${error.code()}")
                        Log.e(
                            "ProductViewModel",
                            "HTTP Error Message: ${error.response()?.errorBody()?.string()}"
                        )
                    } else {
                        Log.e(
                            "ProductViewModel",
                            "Error Fetching Product by Category: ${error.message}"
                        )
                    }
                })
        )
    }


//    fun getProductByPage(page: Int) {
//        compositeDisposable.add(
//            repository.getProductByPage(page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ productResponse ->
//                    _productsLiveData.value = productResponse.products
//
//                }, { error ->
//                    Log.e(
//                        "ProductViewModel",
//                        "Error Fetching Product For page $page: ${error.message}"
//                    )
//                })
//        )
//    }


    fun loadNextPage() {
        if (CURRENT_PAGE <= TOTAL_PAGE && !isLoading) {
            isLoading = true

            compositeDisposable.add(
                repository.getProductByPage(CURRENT_PAGE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        val currentList = _productsLiveData.value ?: emptyList()

                        //Avoid Duplicating products
                        val newProducts = response.products.filter { product ->
                            !currentList.contains(product)
                        }

                        _productsLiveData.value = currentList + response.products
                        CURRENT_PAGE++
                        Log.d("Pagination", "Current Page: $CURRENT_PAGE")
                        isLoading = false
                    }, { error ->
                        error.printStackTrace()
                        isLoading = false

                    })
            )
        }
    }


    //Add Products
    fun addProducts(product: Product) {
        compositeDisposable.add(
            repository.addProducts(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ add ->
                    _productsLiveData.value = add.products
                    Log.e("Add product", "Product Added Successfully")

                }, { error ->

                    if (error is HttpException) {
                        Log.e("productViewModel", "HTTP ERROR CODE : ${error.code()}")
                        Log.e(
                            "productViewModel",
                            "HTTP ERROR MESSAGE : ${error.response()?.errorBody()?.string()}"
                        )

                    } else {
                        Log.e("productViewModel", "Error Adding Product : ${error.message}")

                    }
                })
        )
    }

    fun updateProduct(id: Int, product: Product) {
        compositeDisposable.add(
            repository.updateProduct(id, product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ updateProduct ->
                    _productsLiveData.value = updateProduct.products
                    Log.e("Update Product", "Product Update Successfully")

                }, { error ->

                    if (error is HttpException) {
                        Log.e("ProductViewModel", "HTTP ERROR CODE: ${error.code()}")
                        Log.e(
                            "ProductViewModel",
                            "HTTP ERROR MESSAGE: ${error.response()?.errorBody()?.string()}"
                        )
                    } else {
                        Log.e("ProductViewModel", "Error Updating Product : ${error.message}")
                    }

                })
        )
    }

    fun deleteProduct(id: Int) {
        compositeDisposable.add(
            repository.deleteProduct(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ delete ->
                    if (delete.isSuccessful) {
                        _deleteLivedata.value = true
                        Log.e("Product Delete", "Product Delete Successfully")

                    } else {
                        _deleteLivedata.value = false
                    }
                }, { error ->
                    Log.e("ProductViewModel", "Error Deleting Product : ${error.message}")
                    _deleteLivedata.value = false
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}