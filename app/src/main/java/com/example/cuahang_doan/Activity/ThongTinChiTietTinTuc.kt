package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.model.TinTuc
import com.squareup.picasso.Picasso

class ThongTinChiTietTinTuc : AppCompatActivity() {
    private var imghinhtintuc: ImageView? = null
    private var tentintuc: TextView? = null
    private var ngaytintuc: TextView? = null
    private var noidungtintuc: TextView? = null
    private var toolbarThongTinTinTuc: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_tin_chi_tiet_tin_tuc)
        anhxa()
        actionbar()
    }

    private fun actionbar() {
        setSupportActionBar(toolbarThongTinTinTuc)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbarThongTinTinTuc!!.setNavigationIcon(R.drawable.back)
        toolbarThongTinTinTuc!!.setNavigationOnClickListener { finish() }
    }

    private fun anhxa() {
        toolbarThongTinTinTuc = findViewById(R.id.toolbarThongTinTinTuc)
        imghinhtintuc = findViewById(R.id.imghinhtintuc)
        tentintuc = findViewById(R.id.tentintuc)
        ngaytintuc = findViewById(R.id.ngaytintuc)
        noidungtintuc = findViewById(R.id.noidungtintuc)
        val intent = intent
        if (intent != null) {
            val tinTuc = intent.getSerializableExtra("tintuc") as TinTuc
            Picasso.with(applicationContext).load(tinTuc.hinhbaiviet).into(imghinhtintuc)
            tentintuc?.setText(tinTuc.tenbaiviet)
            ngaytintuc?.setText(tinTuc.thoigian)
            noidungtintuc?.setText(tinTuc.noidung)
        }
    }
}