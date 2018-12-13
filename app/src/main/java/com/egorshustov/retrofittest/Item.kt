package com.egorshustov.retrofittest

import com.google.gson.annotations.SerializedName

data class Item (
    @SerializedName("id")
    var id: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("completed")
    var completed: String
)