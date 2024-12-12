package com.example.newfakestooreapi

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("api/products")
    fun getAllProducts(): Single<ProductResponse>


    @GET("api/products/category")
    fun getCategories(): Single<CategoryResponse>


    @GET("api/products/category")
    fun getProductByCategory(@Query("type") category: String): Single<ProductResponse>


    @GET("api/products")
    fun getLimitedProduct(@Query("limit") limit: Int): Single<ProductResponse>

    @GET("api/products/category")
    fun getProductByCategoryAndSort(
        @Query("type") category: String,
        @Query("sort") sort: String
    ): Single<ProductResponse>

    @GET("api/products")
    fun getProductByPage(@Query("page") page: Int): Single<ProductResponse>


//    //Add products
//    @POST("api/products")
//    @Headers("Content-Type: application/json")
//    fun addProducts(@Body product: Product):Single<ProductResponse>


    //Add products
    @POST("api/products")
    @Headers("Content-Type: application/json")
    fun addProducts(@Body product: Map<String, String>): Single<ProductResponse>


    //Update product
    @PUT("api/products/{id}")
    fun updateProduct(
        @Path("id") id: Int,
        @Body product: Map<String, String>
    ): Single<ProductResponse>

    @DELETE("api/products/{id}")
    fun deleteProduct(
        @Path("id") id: Int
    ): Single<Response<Void>>


}