package com.example.swipe.Repository

import android.content.Context
import android.net.Uri
import com.example.swipe.Networking.ProductApiService
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
    suspend fun fetchProducts() = apiService.getProducts()

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