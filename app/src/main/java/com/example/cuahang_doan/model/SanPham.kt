package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class SanPham {
    @SerializedName("Id")
    @Expose
    var id: Int? = null

    @SerializedName("TenSanPham")
    @Expose
    var tenSanPham: String? = null

    @SerializedName("HinhAnhSanPham")
    @Expose
    var hinhAnhSanPham: String? = null

    @SerializedName("Gia")
    @Expose
    var gia: Int? = null

    @SerializedName("NgayKhuyenMai")
    @Expose
    var ngayKhuyenMai: String? = null

    @SerializedName("GiamGia")
    @Expose
    var giamGia: Int? = null

    @SerializedName("DanhGiaSao")
    @Expose
    var danhGiaSao: String? = null

    @SerializedName("Loai")
    @Expose
    var loai: String? = null

    @SerializedName("HinhMoTa")
    @Expose
    var hinhMoTa: String? = null

    @SerializedName("Mota")
    @Expose
    var mota: String? = null

    @SerializedName("SoLuong")
    @Expose
    var soLuong: Int? = null

    @SerializedName("NgayDang")
    @Expose
    var ngayDang: String? = null

    @SerializedName("ThongSoKyThuat")
    @Expose
    var thongSoKyThuat: String? = null
}