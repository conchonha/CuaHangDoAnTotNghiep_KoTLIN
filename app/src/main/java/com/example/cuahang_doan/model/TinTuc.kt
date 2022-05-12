package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class TinTuc(
    @field:Expose @field:SerializedName("id") var id: Int,
    @field:Expose @field:SerializedName(
        "hinhbaiviet"
    ) var hinhbaiviet: String,
    @field:Expose @field:SerializedName("tenbaiviet") var tenbaiviet: String,
    @field:Expose @field:SerializedName(
        "noidung"
    ) var noidung: String,
    @field:Expose @field:SerializedName("thoigian") var thoigian: String
) : Serializable