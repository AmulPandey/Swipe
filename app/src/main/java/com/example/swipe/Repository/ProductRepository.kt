package com.example.swipe.Repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.swipe.NetworkUtils
import com.example.swipe.Networking.ProductApiService
import com.example.swipe.Room.ProductDatabase
import com.example.swipe.Room.ProductEntity
import com.example.swipe.Room.toProduct
import com.example.swipe.model.screen1.Product
import com.example.swipe.model.screen1.toEntity
import com.example.swipe.model.screen2.ProductResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import com.example.swipe.model.screen2.Result
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProductRepository(
    private val apiService: ProductApiService,
    private val context: Context
) {
    private val productDao = ProductDatabase.getDatabase(context).productDao()


    suspend fun fetchProducts(): List<Product> {
        return if (NetworkUtils.isOnline(context)) {
            try {
                val response = apiService.getProducts()

                val productEntities = response.map { it.toEntity() }

                productDao.deleteAllProducts()
                productDao.insertProducts(productEntities)

                return productEntities.map { it.toProduct() }
            } catch (e: Exception) {
                Log.e("ProductRepository", "Error fetching products: ${e.message}")
                return productDao.getAllProducts().map { it.toProduct() }
            }
        } else {
            return productDao.getAllProducts().map { it.toProduct() }
        }
    }



    suspend fun syncLocalDataToServer() {
        if (!NetworkUtils.isOnline(context)) return

        val localProducts = productDao.getAllProducts()
        for (product in localProducts) {
            addProduct(
                product.product_name,
                product.product_type,
                product.price.toString(),
                product.tax.toString(),
                null
            )
        }
    }

    suspend fun addProduct(
        name: String,
        type: String,
        price: String,
        tax: String,
        imageUri: Uri?
    ): Result<ProductResponse> {
        return try {

            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val typeBody = type.toRequestBody("text/plain".toMediaTypeOrNull())
            val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
            val taxBody = tax.toRequestBody("text/plain".toMediaTypeOrNull())


            val imagePart = imageUri?.let { uri ->
                val file = getFileFromUri(context, uri)
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("files[]", file.name, requestFile)
            }

            val productEntity = ProductEntity(
                image = "android.resource://com.example.swipe/res/drawable/def_img.png",
                price = price.toDouble(),
                product_name = name,
                product_type = type,
                tax = tax.toDouble()
            )
            productDao.insertProduct(productEntity)


            val response = apiService.addProduct(
                productName = nameBody,
                productType = typeBody,
                price = priceBody,
                tax = taxBody,
                files = imagePart
            )

            if (response.success) {
                Result.Success(response)
            } else {
                Result.Error(response.message)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error occurred")
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { output -> inputStream.copyTo(output) }
        return file
    }
}