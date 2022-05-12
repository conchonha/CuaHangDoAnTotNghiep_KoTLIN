package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.cuahang_doan.model.HoaDon
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang.Fragment_SanPhamHoaDon
import com.example.cuahang_doan.Activity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Hoadon : AppCompatActivity() {
    private var txtmadonhang: TextView? = null
    private var txtkhachhang: TextView? = null
    private var txtsodienthoai: TextView? = null
    private var txtdiachi: TextView? = null
    private var txtngaydat: TextView? = null
    private val txtgiasanphamhoadon: TextView? = null
    private var trinhtrang: TextView? = null
    private var arrayList: List<HoaDon>? = null
    private var imgback: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoadon)
        anhxa()
        getdatahoadonadmin()
    }

    private fun getdatahoadonadmin() {
        val intent = intent
        if (intent.hasExtra("idhoadon")) {
            val iddondathang = intent.getStringExtra("idhoadon")
            val dataService = APIServices.getService()
            val callback = dataService.getdatahoadondondathangadmin(iddondathang + "")
            callback.enqueue(object : Callback<List<HoaDon>?> {
                override fun onResponse(
                    call: Call<List<HoaDon>?>,
                    response: Response<List<HoaDon>?>
                ) {
                    Log.d("AAA", "GetdataHoaDonAdmin: $response")
                    if (response.isSuccessful) {
                        arrayList = response.body()
                        txtmadonhang!!.text = arrayList!![0].idDonHang.toString() + ""
                        txtkhachhang!!.text = arrayList!![0].username
                        txtdiachi!!.text = arrayList!![0].diaChi
                        txtngaydat!!.text = arrayList!![0].ngayDat
                        trinhtrang!!.text = arrayList!![0].trinhTrang
                        txtsodienthoai!!.text = arrayList!![0].soDienThoai
                        addfragment(arrayList)
                    }
                }

                override fun onFailure(call: Call<List<HoaDon>?>, t: Throwable) {}
            })
        } else {
            getdatahoadon()
        }
    }

    private fun addfragment(list: List<HoaDon>?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment_sanPhamHoaDon = Fragment_SanPhamHoaDon(arrayList)
        fragmentTransaction.add(
            R.id.linnerlaouthoadon,
            fragment_sanPhamHoaDon,
            "fragmentdondathang"
        )
        fragmentTransaction.commit()
    }

    private fun getdatahoadon() {
        val dataService = APIServices.getService()
        val callback = dataService.getdatahoadon(
            MainActivity.sharedPreferences!!.getInt("iduser", 0).toString() + ""
        )
        callback.enqueue(object : Callback<List<HoaDon>?> {
            override fun onResponse(call: Call<List<HoaDon>?>, response: Response<List<HoaDon>?>) {
                Log.d("AAA", "Hoa Don : $response")
                if (response.isSuccessful) {
                    arrayList = response.body()
                    txtmadonhang!!.text = arrayList!![0].idDonHang.toString() + ""
                    txtkhachhang!!.text = arrayList!![0].username
                    txtdiachi!!.text = arrayList!![0].diaChi
                    txtngaydat!!.text = arrayList!![0].ngayDat
                    txtsodienthoai!!.text = arrayList!![0].soDienThoai
                    trinhtrang!!.text = arrayList!![0].trinhTrang
                    addfragment(arrayList)
                }
            }

            override fun onFailure(call: Call<List<HoaDon>?>, t: Throwable) {}
        })
    }

    private fun anhxa() {
        trinhtrang = findViewById(R.id.trinhtrang)
        imgback = findViewById(R.id.imgback)
        setSupportActionBar(imgback)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        imgback?.setNavigationIcon(R.drawable.back)
        imgback?.setNavigationOnClickListener(View.OnClickListener { finish() })
        txtmadonhang = findViewById(R.id.txtmadonhang)
        txtkhachhang = findViewById(R.id.txtkhachhang)
        txtsodienthoai = findViewById(R.id.txtsodienthoai)
        txtdiachi = findViewById(R.id.txtdiachi)
        txtngaydat = findViewById(R.id.txtngaydat)
    }
}