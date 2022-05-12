package com.example.cuahang_doan.Activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import com.example.cuahang_doan.Activity.*
import com.example.cuahang_doan.Activity.admin.ThongKe
import com.example.cuahang_doan.Activity.admin.QuanLySanPham
import com.example.cuahang_doan.Activity.admin.QuanLytaikhoan
import com.example.cuahang_doan.Activity.admin.fanpage

class Admin : AppCompatActivity() {
    private var linnerThontin: LinearLayout? = null
    private var linerThongKe: LinearLayout? = null
    private var linnerdangxuat: LinearLayout? = null
    private var linerdonhang: LinearLayout? = null
    private var linerFanpage: LinearLayout? = null
    private var carsanpham: CardView? = null
    private var cardtaikhoan: CardView? = null
    private var cardtintuc: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        anhxa()
        onclickview()
    }

    private fun onclickview() {
        linnerThontin!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext, Gioi_Thieu::class.java
                )
            )
        }
        linerThongKe!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    ThongKe::class.java
                )
            )
        }
        linnerdangxuat!!.setOnClickListener {
            startActivity(Intent(applicationContext, Login::class.java))
            MainActivity.editor?.remove("admin")?.commit()
        }
        linerdonhang!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    DonHangCuaBan::class.java
                )
            )
        }
        carsanpham!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    QuanLySanPham::class.java
                )
            )
        }
        cardtaikhoan!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    QuanLytaikhoan::class.java
                )
            )
        }
        cardtintuc!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    TinTuc::class.java
                )
            )
        }
        linerFanpage!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    fanpage::class.java
                )
            )
        }
    }

    private fun anhxa() {
        linerFanpage = findViewById(R.id.linerFanpage)
        cardtintuc = findViewById(R.id.cardtintuc)
        cardtaikhoan = findViewById(R.id.cardtaikhoan)
        carsanpham = findViewById(R.id.carsanpham)
        linerdonhang = findViewById(R.id.linerdonhang)
        linnerdangxuat = findViewById(R.id.dangxuat)
        linerThongKe = findViewById(R.id.linerThongKe)
        linnerThontin = findViewById(R.id.linnerThontin)
    }
}