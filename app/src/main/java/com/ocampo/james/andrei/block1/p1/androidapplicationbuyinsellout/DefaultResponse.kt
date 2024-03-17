package com.ocampo.james.andrei.block1.p1.androidapplicationbuyinsellout

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String
)
