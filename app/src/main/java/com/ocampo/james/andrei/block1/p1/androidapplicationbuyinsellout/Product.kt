package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val created_at: String,
    val updated_at: String,
    val sizes: List<String>,
    val relatedProductIds: List<String>
)
