package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import android.content.Intent
import com.example.cuahang_doan.Activity.admin.Admin
import com.example.cuahang_doan.Activity.nhanvien.NhanVien
import com.example.cuahang_doan.Adapter.MainAdapter
import com.example.cuahang_doan.Fragment.TrangChu.Fragment_trangchu
import com.example.cuahang_doan.Fragment.Gio_Hang.Fragment_giohang
import com.example.cuahang_doan.Fragment.Tai_Khoan.TaiKhoan.Fragmnet_taikhoan
import com.example.cuahang_doan.Fragment.Tim_Kiem.Fragment_timkiem
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import android.content.SharedPreferences
import android.os.Handler
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        anhxa()
        if (sharedPreferences!!.getString("admin", "") != "") {
            startActivity(Intent(applicationContext, Admin::class.java))
        } else if (sharedPreferences!!.getString("nhanvien", "") != "") {
            startActivity(Intent(applicationContext, NhanVien::class.java))
        } else {
        }
        init()
    }

    private fun init() {
        val adapter = MainAdapter(supportFragmentManager)
        adapter.addFragment(Fragment_trangchu(this), "Cửa Hàng")
        adapter.addFragment(Fragment_giohang(), "Giỏ Hàng")
        adapter.addFragment(Fragmnet_taikhoan(), "Tài Khoản")
        adapter.addFragment(Fragment_timkiem(), "Tìm Kiếm")
        MainViewpager!!.adapter = adapter
        mainTablayout!!.setupWithViewPager(MainViewpager)
        mainTablayout!!.getTabAt(0)!!.setIcon(R.drawable.icontrangchu)
        mainTablayout!!.getTabAt(1)!!.setIcon(R.drawable.cart32)
        mainTablayout!!.getTabAt(2)!!.setIcon(R.drawable.user)
        mainTablayout!!.getTabAt(3)!!.setIcon(R.drawable.iconsearch)
        MainViewpager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 1 || position == 2 || position == 3) {
                    mainTablayout!!.visibility = View.GONE
                } else {
                    mainTablayout!!.visibility = View.VISIBLE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun anhxa() {
        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        MainViewpager = findViewById(R.id.MainViewpager)
        mainTablayout = findViewById(R.id.mainTablayout)
    }

    fun reloaddulieu() {
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
        Handler().postDelayed(
            { mainTablayout!!.getTabAt(1)!!.select() }, 100
        )
    }

    companion object {
        var MainViewpager: ViewPager? = null
        @JvmField
        var mainTablayout: TabLayout? = null
        @JvmField
        var sharedPreferences: SharedPreferences? = null
        @JvmField
        var editor: SharedPreferences.Editor? = null
    }
}