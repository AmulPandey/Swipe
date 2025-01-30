package com.example.swipe.ViewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe.Repository.ProductRepository
import com.example.swipe.model.screen1.Product
import com.example.swipe.model.screen2.ProductResponse
import com.example.swipe.model.screen2.Result
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _addProductResult = MutableLiveData<Result<ProductResponse>>()
    val addProductResult: LiveData<Result<ProductResponse>> = _addProductResult

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                // Fetch from local Room database first
                _products.value = repository.fetchProducts()
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching products: ${e.message}")
            }
            _loading.value = false
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
            _addProductResult.value = Result.Loading
            val response = repository.addProduct(name, type, price, tax, imageUri)
            _addProductResult.value = response
            fetchProducts()
            response
        } catch (e: Exception) {
            val error = Result.Error("Error adding product: ${e.message}")
            _addProductResult.value = error
            error
        }
    }
}
