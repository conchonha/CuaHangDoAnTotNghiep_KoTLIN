package com.example.cuahang_doan.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class User {
    @SerializedName("Id")
    @Expose
    var id: Int? = null

    @SerializedName("UserName")
    @Expose
    var userName: String? = null

    @SerializedName("PassWord")
    @Expose
    var passWord: String? = null

    @SerializedName("Email")
    @Expose
    var email: String? = null

    @SerializedName("PhoneNumBer")
    @Expose
    var phoneNumBer: String? = null

    @SerializedName("Adress")
    @Expose
    var adress: String? = null

    @SerializedName("Hinh")
    @Expose
    var hinh: String? = null

    @SerializedName("loai")
    @Expose
    var loai: Int? = null
}