package com.example.swipe.Room


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.swipe.model.screen1.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)

fun ProductEntity.toProduct(): Product {
    return Product(
        image = this.image,
        price = this.price,
        product_name = this.product_name,
        product_type = this.product_type,
        tax = this.tax
    )
}


