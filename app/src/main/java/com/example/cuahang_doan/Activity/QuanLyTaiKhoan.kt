package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import com.makeramen.roundedimageview.RoundedImageView
import android.widget.TextView
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Fragment.Tai_Khoan.TaiKhoan.DialogFragment_chinhsuathongtintaikhoan
import com.example.cuahang_doan.Fragment.Tai_Khoan.TaiKhoan.DialogFragment_Chinhsuadiachi
import com.example.cuahang_doan.Activity.MainActivity
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.model.HoaDon
import com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang.Fragment_SanPhamHoaDon
import android.content.Intent
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.Activity.Login
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuanLyTaiKhoan : AppCompatActivity() {
    private var imgavartaruser: RoundedImageView? = null
    private var txtusername: TextView? = null
    private var txtemail: TextView? = null
    private var txtsdt: TextView? = null
    private var txtusername1: TextView? = null
    private var txtdiachinhanhang: TextView? = null
    private var txtchinhsuathongtin: TextView? = null
    private var txtchinhsuadiachi: TextView? = null
    private var toolbarquanlytaikhoan: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quan_ly_tai_khoan)
        anhxa()
        actionbar()
        init()
        getdatasanphamganday()
        sukienchinhsuathongtin()
    }

    private fun sukienchinhsuathongtin() {
        txtchinhsuathongtin!!.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val dialog = DialogFragment_chinhsuathongtintaikhoan()
            dialog.show(fragmentManager, "chinhsuathongtintaikhoan")
        }
        txtchinhsuadiachi!!.setOnClickListener {
            val fragmentManager1 = supportFragmentManager
            val fragment_chinhsuadiachi = DialogFragment_Chinhsuadiachi()
            fragment_chinhsuadiachi.show(fragmentManager1, "chinhsuadiachi")
        }
    }

    private fun getdatasanphamganday() {
        if (MainActivity.sharedPreferences!!.getInt("iduser", 0) != 0) {
            val dataService = APIServices.getService()
            val callback = dataService.getdatahoadon(
                MainActivity?.sharedPreferences!!
                    .getInt("iduser", 0).toString() + ""
            )
            callback.enqueue(object : Callback<List<HoaDon?>?> {
                override fun onResponse(
                    call: Call<List<HoaDon?>?>,
                    response: Response<List<HoaDon?>?>
                ) {
                    Log.d("AAA", "don hang gan day: $response")
                    val arrayList = response.body()
                    val fragment_sanPhamHoaDon = Fragment_SanPhamHoaDon(arrayList as List<HoaDon>?)
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.add(
                        R.id.linnerlaoutdonhangganday,
                        fragment_sanPhamHoaDon,
                        "donhangganday"
                    )
                    fragmentTransaction.commit()
                }

                override fun onFailure(call: Call<List<HoaDon?>?>, t: Throwable) {}
            })
        }
    }

    private fun actionbar() {
        setSupportActionBar(toolbarquanlytaikhoan)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbarquanlytaikhoan!!.setNavigationIcon(R.drawable.back)
        toolbarquanlytaikhoan!!.setNavigationOnClickListener { finish() }
    }

    private fun init() {
        if (MainActivity.sharedPreferences!!.getInt("iduser", 0) == 0) {
            startActivity(Intent(applicationContext, Login::class.java))
        } else {
            Picasso.with(applicationContext)
                .load(MainActivity.sharedPreferences!!.getString("hinh", ""))
                .into(imgavartaruser)
            txtusername!!.text = MainActivity.sharedPreferences!!.getString("username", "")
            txtemail!!.text = MainActivity.sharedPreferences!!.getString("email", "")
            txtusername1!!.text = MainActivity.sharedPreferences!!.getString("username", "")
            txtdiachinhanhang!!.text = MainActivity.sharedPreferences!!.getString("diachi", "")
            txtsdt!!.text = MainActivity.sharedPreferences!!.getString("sodienthoai", "")
        }
    }

    private fun anhxa() {
        txtsdt = findViewById(R.id.txtsdt)
        toolbarquanlytaikhoan = findViewById(R.id.toolbarquanlytaikhoan)
        imgavartaruser = findViewById(R.id.imgavartaruser)
        txtusername = findViewById(R.id.txtusername)
        txtemail = findViewById(R.id.txtemail)
        txtusername1 = findViewById(R.id.txtusername1)
        txtdiachinhanhang = findViewById(R.id.txtdiachinhanhang)
        txtchinhsuathongtin = findViewById(R.id.txtchinhsuathongtin)
        txtchinhsuadiachi = findViewById(R.id.txtchinhsuadiachi)
    }
}