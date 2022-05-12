package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class GioHang {
    @SerializedName("Id")
    @Expose
    var id: Int? = null

    @SerializedName("Id_User")
    @Expose
    var idUser: Int? = null

    @SerializedName("Id_Sanpham")
    @Expose
    var idSanpham: String? = null

    @SerializedName("Ten_Sanpham")
    @Expose
    var tenSanpham: String? = null

    @SerializedName("SoLuong")
    @Expose
    var soLuong: Int? = null

    @SerializedName("ThanhTien")
    @Expose
    var thanhTien: Int? = null

    @SerializedName("Hinh")
    @Expose
    var hinh: String? = null
}