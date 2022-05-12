package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Chitietdondathang {
    @SerializedName("Id")
    @Expose
    var id: Int? = null

    @SerializedName("Id_Taikhoan")
    @Expose
    var idTaikhoan: Int? = null

    @SerializedName("Username")
    @Expose
    var username: String? = null

    @SerializedName("Dia_Chi")
    @Expose
    var diaChi: String? = null

    @SerializedName("Email")
    @Expose
    var email: String? = null

    @SerializedName("SoDienThoai")
    @Expose
    var soDienThoai: String? = null

    @SerializedName("Id_SanPham")
    @Expose
    var idSanPham: Int? = null

    @SerializedName("GiaSanPham")
    @Expose
    var giaSanPham: Int? = null

    @SerializedName("HinhSanPham")
    @Expose
    var hinhSanPham: String? = null

    @SerializedName("SoLuongSanPham")
    @Expose
    var soLuongSanPham: Int? = null

    @SerializedName("TenSanPham")
    @Expose
    var tenSanPham: String? = null

    @SerializedName("Id_DonDatHang")
    @Expose
    var idDonDatHang: String? = null
}