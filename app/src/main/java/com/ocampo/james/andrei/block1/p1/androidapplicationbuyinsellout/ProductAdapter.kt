
package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout.R

class ProductAdapter(
    private var productList: List<Product>,
    private val clickListener: OnProductClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun submitList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        itemView: View,
        private val clickListener: OnProductClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
        private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)

        fun bind(product: Product) {
            productNameTextView.text = product.name
            productPriceTextView.text = product.price
            Glide.with(itemView.context)
                .load(product.image)
                .into(productImageView)

            itemView.setOnClickListener {
                clickListener.onProductClick(product)
            }
        }
    }
}
