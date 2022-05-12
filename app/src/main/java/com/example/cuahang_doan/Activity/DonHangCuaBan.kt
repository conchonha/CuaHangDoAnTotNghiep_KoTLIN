package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.Activity.admin.Admin
import com.example.cuahang_doan.Adapter.MainAdapter
import com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang.Fragment_Choxetduyet
import com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang.Fragment_Dangvanchuyen
import com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang.Fragment_Dagiaohang
import com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang.Fragment_DaHuy

class DonHangCuaBan : AppCompatActivity() {
    private val toolbardonhangcuaban: Toolbar? = null
    private var viewpagerdonhangcuaban: ViewPager? = null
    private var Donhangcuabantablayout: TabLayout? = null
    private var Toobardonhangcuaban: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_don_hang_cua_ban)
        anhxa()
        init()
    }

    private fun anhxa() {
        Toobardonhangcuaban = findViewById(R.id.Toobardonhangcuaban)
        setSupportActionBar(Toobardonhangcuaban)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Toobardonhangcuaban?.setNavigationIcon(R.drawable.back)
        Toobardonhangcuaban?.setNavigationOnClickListener(View.OnClickListener {
            if (MainActivity.sharedPreferences!!.getString("admin", "") != "") {
                startActivity(Intent(applicationContext, Admin::class.java))
                finish()
            } else {
                finish()
            }
        })
        viewpagerdonhangcuaban = findViewById(R.id.viewpagerdonhangcuaban)
        Donhangcuabantablayout = findViewById(R.id.Donhangcuabantablayout)
    }

    private fun init() {
        val mainAdapter = MainAdapter(supportFragmentManager)
        mainAdapter.addFragment(Fragment_Choxetduyet(), "Chờ Duyệt")
        mainAdapter.addFragment(Fragment_Dangvanchuyen(), "Đang Giao")
        mainAdapter.addFragment(Fragment_Dagiaohang(), "Hoàn Thành")
        mainAdapter.addFragment(Fragment_DaHuy(), "Đã Hủy")
        viewpagerdonhangcuaban!!.adapter = mainAdapter
        Donhangcuabantablayout!!.setupWithViewPager(viewpagerdonhangcuaban)
        Donhangcuabantablayout!!.getTabAt(0)!!.setIcon(R.drawable.choduyet)
        Donhangcuabantablayout!!.getTabAt(1)!!.setIcon(R.drawable.vanchuyen)
        Donhangcuabantablayout!!.getTabAt(2)!!.setIcon(R.drawable.hoanthanh)
        Donhangcuabantablayout!!.getTabAt(3)!!.setIcon(R.drawable.delete)
    }
}