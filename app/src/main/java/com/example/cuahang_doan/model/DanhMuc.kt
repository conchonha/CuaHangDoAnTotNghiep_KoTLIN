package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class DanhMuc {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("ten")
    @Expose
    var ten: String? = null

    @SerializedName("hinhicon")
    @Expose
    var hinhicon: String? = null
}