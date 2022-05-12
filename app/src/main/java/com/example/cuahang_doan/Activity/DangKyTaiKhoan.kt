package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.EditText
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Activity.MainActivity
import com.example.cuahang_doan.Activity.Login
import com.example.cuahang_doan.Activity.admin.QuanLytaikhoan
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DangKyTaiKhoan : AppCompatActivity() {
    private val relativedangkytaikhoan: RelativeLayout? = null
    private var edtusername: EditText? = null
    private var edtpassword: EditText? = null
    private var edtemaill: EditText? = null
    private var edtsodienthoaii: EditText? = null
    private var edtdiachii: EditText? = null
    private var btndangky: Button? = null
    private var toolbardangkytaikhoan: Toolbar? = null
    private var idloai = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang_ky_tai_khoan)
        anhxa()
        init()
        getintent()
        dangky()
    }

    private fun getintent() {
        val intent = intent
        if (intent != null) {
            if (intent.hasExtra("khachhang")) {
                idloai = 0
                Log.d("AAA", "idloai: $idloai")
            }
            if (intent.hasExtra("nhanvien")) {
                idloai = 1
                Log.d("AAA", "idloai: $idloai")
            }
            if (intent.hasExtra("admin")) {
                idloai = 2
                Log.d("AAA", "idloai: $idloai")
            }
        }
    }

    private fun dangky() {
        btndangky!!.setOnClickListener {
            if (edtusername!!.text.toString() == "" || edtpassword!!.text.toString() == "" || edtemaill!!.text.toString() == "" || edtsodienthoaii!!.text.toString() == "" || edtdiachii!!.text.toString() == "") {
                Toast.makeText(
                    this@DangKyTaiKhoan,
                    "Không Được Để Trống Dữ Liệu",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!edtemaill!!.text.toString().endsWith("@gmail.com")) {
                Toast.makeText(this@DangKyTaiKhoan, "Sai Email", Toast.LENGTH_SHORT).show()
            } else if (edtsodienthoaii!!.text.toString().startsWith("086") ||
                edtsodienthoaii!!.text.toString().startsWith("096") ||
                edtsodienthoaii!!.text.toString().startsWith("097") ||
                edtsodienthoaii!!.text.toString().startsWith("098") ||
                edtsodienthoaii!!.text.toString().startsWith("032") ||
                edtsodienthoaii!!.text.toString().startsWith("032") ||
                edtsodienthoaii!!.text.toString().startsWith("034") ||
                edtsodienthoaii!!.text.toString().startsWith("035") ||
                edtsodienthoaii!!.text.toString().startsWith("036") ||
                edtsodienthoaii!!.text.toString().startsWith("036") ||
                edtsodienthoaii!!.text.toString().startsWith("037") ||
                edtsodienthoaii!!.text.toString().startsWith("038") ||
                edtsodienthoaii!!.text.toString().startsWith("039") ||
                edtsodienthoaii!!.text.toString().startsWith("089") ||
                edtsodienthoaii!!.text.toString().startsWith("090") ||
                edtsodienthoaii!!.text.toString().startsWith("093") ||
                edtsodienthoaii!!.text.toString().startsWith("070") ||
                edtsodienthoaii!!.text.toString().startsWith("079") ||
                edtsodienthoaii!!.text.toString().startsWith("079") ||
                edtsodienthoaii!!.text.toString().startsWith("077") ||
                edtsodienthoaii!!.text.toString()
                    .startsWith("076") || edtsodienthoaii!!.text.toString().startsWith("078") &&
                edtsodienthoaii!!.text.toString().length == 10
            ) {
                val dataService = APIServices.getService()
                val callback = dataService.dangkytaikhoan(
                    edtusername!!.text.toString(),
                    edtpassword!!.text.toString(),
                    edtemaill!!.text.toString(),
                    edtsodienthoaii!!.text.toString(),
                    edtdiachii!!.text.toString(),
                    idloai.toString() + ""
                )
                callback.enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        Log.d("AAA", "dang ky tai khoan: $response")
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@DangKyTaiKhoan,
                                "Đang Ký Thành Công",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (MainActivity.sharedPreferences!!.getString("admin", "") == "") {
                                startActivity(Intent(applicationContext, Login::class.java))
                                finish()
                            } else {
                                startActivity(
                                    Intent(
                                        applicationContext,
                                        QuanLytaikhoan::class.java
                                    )
                                )
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {}
                })
            } else {
                Toast.makeText(this@DangKyTaiKhoan, "sai so dien thoai", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init() {
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.animalpha)
        //relativedangkytaikhoan.setAnimation(animation);
        setSupportActionBar(toolbardangkytaikhoan)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbardangkytaikhoan!!.setNavigationIcon(R.drawable.back)
        toolbardangkytaikhoan!!.setNavigationOnClickListener { finish() }
    }

    private fun anhxa() {
        toolbardangkytaikhoan = findViewById(R.id.toolbardangkytaikhoan)
        btndangky = findViewById(R.id.btndangky)
        edtdiachii = findViewById(R.id.edtdiachii)
        edtsodienthoaii = findViewById(R.id.edtsodienthoaii)
        edtemaill = findViewById(R.id.edtemaill)
        edtpassword = findViewById(R.id.edtpassword)
        edtusername = findViewById(R.id.edtusername)
        //relativedangkytaikhoan=findViewById(R.id.relativedangkytaikhoan);
    }
}