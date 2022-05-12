package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.cuahang_doan.model.GioithieuShop
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Activity.MainActivity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.Activity.admin.UpdateThongtin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Gioi_Thieu : AppCompatActivity() {
    private var toolbargioithieu: Toolbar? = null
    private var txttentrusogioithieu: TextView? = null
    private var txtdiachigioithieu: TextView? = null
    private var txtsodienthoaigioithieu: TextView? = null
    private var txtemailgioithieushop: TextView? = null
    private var txtwebsitegioithieushop: TextView? = null
    private var txtfanpagegioithieushop: TextView? = null
    private var btnupdategioithieu: Button? = null
    private var arrayList: ArrayList<GioithieuShop>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gioi__thieu)
        anhxa()
        getdatagioithieushop()
    }

    private fun getdatagioithieushop() {
        val dataService = APIServices.getService()
        val callback = dataService.Getdatagioithieushop()
        callback.enqueue(object : Callback<List<GioithieuShop>?> {
            override fun onResponse(
                call: Call<List<GioithieuShop>?>,
                response: Response<List<GioithieuShop>?>
            ) {
                Log.d("AAA", "Gioi thieu shop: $response")
                if (response.isSuccessful) {
                    arrayList = response.body() as ArrayList<GioithieuShop>?
                    val gioithieuShop = arrayList!![0]
                    txttentrusogioithieu!!.text = gioithieuShop.tenCuaHang
                    txtdiachigioithieu!!.text = gioithieuShop.truSoChinh
                    txtsodienthoaigioithieu!!.text = gioithieuShop.dienThoai
                    txtemailgioithieushop!!.text = gioithieuShop.email
                    txtwebsitegioithieushop!!.text = gioithieuShop.website
                    txtfanpagegioithieushop!!.text = gioithieuShop.fanpage
                }
            }

            override fun onFailure(call: Call<List<GioithieuShop>?>, t: Throwable) {}
        })
    }

    private fun anhxa() {
        btnupdategioithieu = findViewById(R.id.btnupdategioithieu)
        txttentrusogioithieu = findViewById(R.id.txttentrusogioithieu)
        txtdiachigioithieu = findViewById(R.id.txtdiachigioithieu)
        txtsodienthoaigioithieu = findViewById(R.id.txtsodienthoaigioithieu)
        txtemailgioithieushop = findViewById(R.id.txtemailgioithieushop)
        txtwebsitegioithieushop = findViewById(R.id.txtwebsitegioithieushop)
        txtfanpagegioithieushop = findViewById(R.id.txtfanpagegioithieushop)
        toolbargioithieu = findViewById(R.id.toolbargioithieu)
        setSupportActionBar(toolbargioithieu)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbargioithieu?.setNavigationIcon(R.drawable.back)
        toolbargioithieu?.setNavigationOnClickListener(View.OnClickListener { finish() })
        if (MainActivity.sharedPreferences!!.getString("admin", "") != "") {
            btnupdategioithieu?.setVisibility(View.VISIBLE)
            btnupdategioithieu?.setOnClickListener(View.OnClickListener {
                if (arrayList != null) {
                    val intent = Intent(this@Gioi_Thieu, UpdateThongtin::class.java)
                    intent.putExtra("updatethongtinshop", arrayList!![0])
                    startActivity(intent)
                }
            })
        }
    }
}