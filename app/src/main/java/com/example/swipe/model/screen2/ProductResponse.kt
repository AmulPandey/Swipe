package com.example.swipe.model.screen2

import com.example.swipe.model.screen1.Product

data class ProductResponse(
    val message: String,
    val product_id: Int,
    val success: Boolean,
    val product_details: Product
)

