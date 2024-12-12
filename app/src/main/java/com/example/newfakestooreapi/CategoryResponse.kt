package com.example.newfakestooreapi

data class CategoryResponse(
    val status: String,
    val message: String,
    val categories: List<String>
)
