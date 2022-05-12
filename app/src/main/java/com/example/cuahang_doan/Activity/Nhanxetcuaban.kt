package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Adapter.MainAdapter
import com.example.cuahang_doan.Fragment.Tai_Khoan.NhanXet.Fragment_Chuanhanxet
import com.example.cuahang_doan.Fragment.Tai_Khoan.NhanXet.Fragment_Danhanxet

class Nhanxetcuaban : AppCompatActivity() {
    private var viewpagernhanxetcuaban: ViewPager? = null
    private var tablayoutnhanxetcuaban: TabLayout? = null
    private var toolbarnhanxetcuaban: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nhanxetcuaban)
        anhxa()
        actionbar()
    }

    private fun actionbar() {
        toolbarnhanxetcuaban = findViewById(R.id.toolbarnhanxetcuaban)
        setSupportActionBar(toolbarnhanxetcuaban)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbarnhanxetcuaban?.setNavigationIcon(R.drawable.back)
        toolbarnhanxetcuaban?.setNavigationOnClickListener(View.OnClickListener { finish() })
    }

    private fun anhxa() {
        viewpagernhanxetcuaban = findViewById(R.id.viewpagernhanxetcuaban)
        tablayoutnhanxetcuaban = findViewById(R.id.tablayoutnhanxetcuaban)
        val mainAdapter = MainAdapter(supportFragmentManager)
        mainAdapter.addFragment(Fragment_Chuanhanxet(), "Nhận Xét")
        mainAdapter.addFragment(Fragment_Danhanxet(), "Lịch Sử")
        viewpagernhanxetcuaban?.setAdapter(mainAdapter)
        tablayoutnhanxetcuaban?.setupWithViewPager(viewpagernhanxetcuaban)
        tablayoutnhanxetcuaban?.getTabAt(0)!!.setIcon(R.drawable.ratingdanhanxet)
        tablayoutnhanxetcuaban?.getTabAt(1)!!.setIcon(R.drawable.ratingchuanhanxet)
    }
}