package com.example.swipe.model.screen1

import com.example.swipe.Room.ProductEntity

data class Product(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        image = this.image,
        price = this.price,
        product_name = this.product_name,
        product_type = this.product_type,
        tax = this.tax
    )
}

