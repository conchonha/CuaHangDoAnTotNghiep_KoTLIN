package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class QuangCao {
    @SerializedName("HinhAnh")
    @Expose
    var hinhAnh: String? = null

    @SerializedName("NoiDung")
    @Expose
    var noiDung: String? = null

    @SerializedName("Id_Sanpham")
    @Expose
    var idSanpham: Int? = null

    @SerializedName("TenSanPham")
    @Expose
    var tenSanPham: String? = null

    @SerializedName("HinhAnhSanPham")
    @Expose
    var hinhAnhSanPham: String? = null

    @SerializedName("Id")
    @Expose
    var id: Int? = null
}