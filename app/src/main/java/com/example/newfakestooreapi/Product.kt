package com.example.newfakestooreapi



data class ProductResponse(
    val status: String,
    val message: String,
    val products: List<Product>
)

//data class Product(
//    val id : Int,
//    val title: String,
//    val image : String,
//    val price: Double,
//    val description : String,
//    val brand: String,
//    val model: String,
//    val color: String,
//    val category: String,
//    val discount: Double
//)


////i am use this for add products
data class Product(
    val id: Int = 0, // Default value or can be optional
    val title: String,
    val image: String? = "", // Make it nullable if not always provided
    val price: Double, // Default value for price
    val description: String? = "", // Nullable
    val brand: String,
    val model: String,
    val color: String,
    val category: String,
    val discount: Double// Default value for discount
)

