package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsActivity : AppCompatActivity() {
    private var selectedSizeButton: Button? = null
    private var cartitemsTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        // Get the product ID from the intent
        val productId = intent.getStringExtra("productId") ?: ""

        // Fetch the product details using the product ID
        fetchProductDetails(productId)
    }

    private fun fetchProductDetails(productId: String) {
        val productAPI = ApiClient.apiService

        val call = productAPI.getProduct(productId)
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    if (product != null) {
                        updateUI(product)
                    }
                } else {
                    Toast.makeText(this@ProductDetailsActivity, "Failed to fetch product details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(this@ProductDetailsActivity, "Failed to fetch product details", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(product: Product) {
        val productNameHeader: TextView = findViewById(R.id.productTitleTextView)
        val productNameImage: TextView = findViewById(R.id.productNameBelowImage)
        val productPriceTextView: TextView = findViewById(R.id.productPriceTextView)
        val productDescriptionTextView: TextView = findViewById(R.id.productDescriptionTextView)
        val productImageView: ImageView = findViewById(R.id.productImageView)

        productNameHeader.text = product.name
        productNameImage.text = product.name
        productPriceTextView.text = product.price
        productDescriptionTextView.text = product.description
        Glide.with(this).load(product.image).into(productImageView)

        setUpSizeButtons(product.sizes)

        val addToCartButton: Button = findViewById(R.id.addToCartButton)
        addToCartButton.setOnClickListener {
            val selectedSize = selectedSizeButton?.text.toString()
            addToCart(product.name, product.price, selectedSize)
        }

        fetchRelatedProducts(product.relatedProductIds)
    }

    private fun setUpSizeButtons(sizes: List<String>) {
        val sizesGridLayout: GridLayout = findViewById(R.id.sizesGridLayout)
        sizesGridLayout.removeAllViews()

        for (size in sizes) {
            val sizeButton = Button(this)
            sizeButton.text = size
            sizeButton.setBackgroundColor(Color.TRANSPARENT)
            sizeButton.setTextColor(Color.BLACK)
            sizeButton.textSize = 16f
            sizeButton.setBackgroundResource(R.drawable.size_button_background)
            sizeButton.setOnClickListener {
                handleSizeSelection(sizeButton)
                selectSize(sizeButton)
            }
            sizesGridLayout.addView(sizeButton)
        }
    }

    private fun fetchRelatedProducts(relatedProductIds: List<String>) {
        val productAPI = ApiClient.apiService

        val relatedProductsList = mutableListOf<Product>()
        val calls = mutableListOf<Call<Product>>()

        for (productId in relatedProductIds) {
            val call = productAPI.getProduct(productId)
            calls.add(call)
        }

        for (call in calls) {
            call.enqueue(object : Callback<Product> {
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    if (response.isSuccessful) {
                        val product = response.body()
                        if (product != null) {
                            relatedProductsList.add(product)
                            if (relatedProductsList.size == relatedProductIds.size) {
                                updateRelatedProductsUI(relatedProductsList)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    // Handle API call failure
                }
            })
        }
    }

    private fun updateRelatedProductsUI(relatedProductsList: List<Product>) {
        val relatedProductsRecyclerView: RecyclerView = findViewById(R.id.otherProductsRecyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        relatedProductsRecyclerView.layoutManager = layoutManager

        val relatedProductAdapter = RelatedProductAdapter(relatedProductsList)
        relatedProductsRecyclerView.adapter = relatedProductAdapter
    }

    private fun parsePrice(priceString: String): Double {
        val cleanPriceString = priceString.replace("PHP", "").replace(",", "").trim()
        return cleanPriceString.toDoubleOrNull() ?: 0.0
    }

    private fun addToCart(name: String, price: String, size: String) {
        // Implement adding the item to the cart
    }

    private fun handleSizeSelection(sizeButton: Button) {
        selectedSizeButton?.setBackgroundResource(R.drawable.size_button_background)
        sizeButton.setBackgroundResource(R.drawable.selected_size_button_background)
        selectedSizeButton = sizeButton
    }

    private fun selectSize(sizeButton: Button) {
        // Implement selecting the size
    }

    private fun displayCartItemsWithSizes() {
        val cartItems = getCartItems()

        cartitemsTextView?.text = ""

        for (cartItem in cartItems) {
            val itemText = "Product: ${cartItem.name}, Price: ${cartItem.price}, Size: ${cartItem.size}\n"
            cartitemsTextView?.append(itemText)
        }
    }

    private fun getCartItems(): List<CartItem> {
        return emptyList()
    }

    fun openCart(view: View) {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    fun openSearch(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}
