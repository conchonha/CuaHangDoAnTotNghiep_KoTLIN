package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class GioithieuShop : Serializable {
    @SerializedName("Id")
    @Expose
    var id: Int? = null

    @SerializedName("TenCuaHang")
    @Expose
    var tenCuaHang: String? = null

    @SerializedName("TruSoChinh")
    @Expose
    var truSoChinh: String? = null

    @SerializedName("DienThoai")
    @Expose
    var dienThoai: String? = null

    @SerializedName("Email")
    @Expose
    var email: String? = null

    @SerializedName("Website")
    @Expose
    var website: String? = null

    @SerializedName("Fanpage")
    @Expose
    var fanpage: String? = null
}