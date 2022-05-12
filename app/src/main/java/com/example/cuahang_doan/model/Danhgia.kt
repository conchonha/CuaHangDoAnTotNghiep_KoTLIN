package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Danhgia {
    @SerializedName("Id_DanGgia")
    @Expose
    var idDanGgia: Int? = null

    @SerializedName("namsao")
    @Expose
    private var _5sao: Int? = null

    @SerializedName("bonsao")
    @Expose
    private var _4sao: Int? = null

    @SerializedName("basao")
    @Expose
    private var _3sao: Int? = null

    @SerializedName("haisao")
    @Expose
    private var _2sao: Int? = null

    @SerializedName("motsao")
    @Expose
    private var _1sao: Int? = null

    @SerializedName("ComMent")
    @Expose
    var comMent: String? = null

    @SerializedName("Id_SanPham")
    @Expose
    var idSanPham: Int? = null

    @SerializedName("username")
    @Expose
    var idUser: String? = null

    @SerializedName("NgayDanhGia")
    @Expose
    var ngayDanhGia: String? = null
    fun get5sao(): Int? {
        return _5sao
    }

    fun set5sao(_5sao: Int?) {
        this._5sao = _5sao
    }

    fun get4sao(): Int? {
        return _4sao
    }

    fun set4sao(_4sao: Int?) {
        this._4sao = _4sao
    }

    fun get3sao(): Int? {
        return _3sao
    }

    fun set3sao(_3sao: Int?) {
        this._3sao = _3sao
    }

    fun get2sao(): Int? {
        return _2sao
    }

    fun set2sao(_2sao: Int?) {
        this._2sao = _2sao
    }

    fun get1sao(): Int? {
        return _1sao
    }

    fun set1sao(_1sao: Int?) {
        this._1sao = _1sao
    }
}