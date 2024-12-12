package com.example.newfakestooreapi

import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class ProductRepository(private val api:ProductApi) {

    fun getAllProducts(): Single<List<Product>> {
        return api.getAllProducts()
            .map { it.products }

    }

    fun getCategories():Single<CategoryResponse>{
        return api.getCategories()
    }

    fun getProductByCategory(category: String):Single<ProductResponse>{
        return api.getProductByCategory(category)
    }


    fun getLimitedProduct(limit: Int):Single<ProductResponse>{
        return api.getLimitedProduct(limit)
    }

    fun getProductByCategoryAndSort(category: String, sort: String):Single<ProductResponse>{
        return api.getProductByCategoryAndSort(category, sort)
    }

    fun getProductByPage(page: Int):Single<ProductResponse>{
        return api.getProductByPage(page)
    }


//    fun addProducts(product: Product):Single<ProductResponse>{
//        return api.addProducts(product)
//    }

    fun addProducts(product: Product):Single<ProductResponse>{

        val product = mapOf(
            "title" to product.title,
            "brand" to product.brand,
            "model" to product.model,
            "color" to product.color,
            "category" to product.category,
            "discount" to product.discount.toString(),
            "price" to product.price.toString()
        )

        return api.addProducts(product)
    }


    fun updateProduct(id: Int, product: Product):Single<ProductResponse>{

        val product = mapOf(
            "title" to product.title,
            "brand" to product.brand,
            "model" to product.model,
            "color" to product.color,
            "category" to product.category,
            "discount" to product.discount.toString(),
            "price" to product.price.toString()
        )


        return api.updateProduct(id, product)
    }


   fun deleteProduct(id: Int):Single<Response<Void>>{
       return api.deleteProduct(id)
   }
}