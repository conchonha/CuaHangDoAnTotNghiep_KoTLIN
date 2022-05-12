package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Nhanxetcuaban {
    @SerializedName("Id_DanGgia")
    @Expose
    var idDanGgia: Int? = null

    @SerializedName("namsao")
    @Expose
    var namsao: Int? = null

    @SerializedName("bonsao")
    @Expose
    var bonsao: Int? = null

    @SerializedName("basao")
    @Expose
    var basao: Int? = null

    @SerializedName("haisao")
    @Expose
    var haisao: Int? = null

    @SerializedName("motsao")
    @Expose
    var motsao: Int? = null

    @SerializedName("ComMent")
    @Expose
    var comMent: String? = null

    @SerializedName("Id_SanPham")
    @Expose
    var idSanPham: Int? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("NgayDanhGia")
    @Expose
    var ngayDanhGia: String? = null

    @SerializedName("Id")
    @Expose
    var id: Int? = null

    @SerializedName("Dia_Chi")
    @Expose
    var diaChi: String? = null

    @SerializedName("Email")
    @Expose
    var email: String? = null

    @SerializedName("SoDienThoai")
    @Expose
    var soDienThoai: String? = null

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
}