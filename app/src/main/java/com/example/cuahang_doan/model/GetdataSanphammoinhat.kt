package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class GetdataSanphammoinhat(
    @field:Expose @field:SerializedName("Id") var id: Int,
    @field:Expose @field:SerializedName(
        "TenSanPham"
    ) var tenSanPham: String,
    @field:Expose @field:SerializedName("HinhAnhSanPham") var hinhAnhSanPham: String,
    @field:Expose @field:SerializedName(
        "ThongSoKyThuat"
    ) var thongSoKyThuat: String,
    @field:Expose @field:SerializedName("Gia") var gia: Int,
    @field:Expose @field:SerializedName(
        "NgayKhuyenMai"
    ) var ngayKhuyenMai: String,
    @field:Expose @field:SerializedName("GiamGia") var giamGia: Int,
    @field:Expose @field:SerializedName(
        "DanhGiaSao"
    ) var danhGiaSao: String,
    @field:Expose @field:SerializedName("Loai") var loai: String
) {

    @SerializedName("id_danhmuc")
    @Expose
    var idDanhmuc: Int? = null

}