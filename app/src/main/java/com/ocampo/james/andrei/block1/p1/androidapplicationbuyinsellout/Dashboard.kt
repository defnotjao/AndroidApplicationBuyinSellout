package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback


class Dashboard : AppCompatActivity(), ProductAdapter.OnProductClickListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        val productRecyclerView = findViewById<RecyclerView>(R.id.productRecyclerView)
        val productRecyclerView1 = findViewById<RecyclerView>(R.id.productRecyclerView1)

        val itemList = generateDummyData()
        val adapter = CarouselAdapter(itemList)

        viewPager2.adapter = adapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * resources.displayMetrics.density).toInt()))
        viewPager2.setPageTransformer(compositePageTransformer)

        val productAPI = RetrofitHelper.getInstance().create(ProductAPI::class.java)
        GlobalScope.launch {
            val call: Call<List<Product>> = productAPI.getProducts()
            call.enqueue(object : Callback<List<Product>> {
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    if (response.isSuccessful) {
                        val products = response.body()
                        if (products != null) {
                            // Update the productAdapter with the list of products
                            productAdapter.submitList(products)
                        } else {
                            // Handle empty response
                        }
                    } else {
                        // Handle unsuccessful response
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    // Handle API call failure
                }
            })
        }

        val spanCount = 2
        val layoutManager = GridLayoutManager(this, spanCount)
        productRecyclerView.layoutManager = layoutManager
        productRecyclerView1.layoutManager = layoutManager

        productAdapter = ProductAdapter(emptyList(), this)
        productRecyclerView.adapter = productAdapter
        productRecyclerView1.adapter = productAdapter


        // Set onClickListener for the menu button to open the drawer
        val menuButton: ImageButton = findViewById(R.id.menuButton)
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }

        // Set onClickListener for the profile image in the drawer header
        val profileImageView = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.profileImageView)
        profileImageView.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        // Set onClickListener for the username in the drawer header
        val usernameTextView = navigationView.getHeaderView(0).findViewById<TextView>(R.id.usernameTextView)
        usernameTextView.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle Home click
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_orders -> {
                    // Handle Orders click
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_favorites -> {
                    // Handle Favorites click
                    val intent = Intent(this, FavoritesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
                    // Handle Settings click
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        val cartButton: ImageButton = findViewById(R.id.cartButton)
        cartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun generateDummyData(): List<Int> {
        return listOf(
            R.drawable.upangstudents,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7
        )
    }

    override fun onProductClick(product: Product) {
        // Handle product click, for example, show a product description
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("productName", product.name)
        intent.putExtra("productPrice", product.price)
        intent.putExtra("productImage", product.image)
        intent.putExtra("productDescription", product.description)
        startActivity(intent)
    }

    companion object {
        const val SEARCH_REQUEST_CODE = 123
    }
}
