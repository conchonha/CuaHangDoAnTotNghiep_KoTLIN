package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class DonDatHang {
    @SerializedName("Id_DonHang")
    @Expose
    var idDonHang: Int? = null

    @SerializedName("NgayDat")
    @Expose
    var ngayDat: String? = null

    @SerializedName("TrinhTrang")
    @Expose
    var trinhTrang: String? = null

    @SerializedName("Id_TaiKhoan")
    @Expose
    var idTaiKhoan: Int? = null
}