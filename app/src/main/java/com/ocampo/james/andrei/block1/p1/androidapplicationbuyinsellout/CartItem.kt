package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout

import java.io.Serializable


data class CartItem(
    val name: String,
    val price: Double,
    val size: String,
    val imageResource: Int,
    var isSelected: Boolean = false
) : Serializable



