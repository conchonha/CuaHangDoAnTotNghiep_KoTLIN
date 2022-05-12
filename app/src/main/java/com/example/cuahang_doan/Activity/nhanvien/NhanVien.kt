package com.example.cuahang_doan.Activity.nhanvien

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import com.example.cuahang_doan.Activity.Login
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.model.DonDatHang
import com.example.cuahang_doan.Adapter.nhanvien.Adapter_chitietdonhang_nhanviengiaohang
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class NhanVien : AppCompatActivity() {
    private var recyclerviewdangvanchuyennhanvien: RecyclerView? = null
    private var imageviewmenu: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nhan_vien)
        anhxa()
        getdatadangvanchuyennadmin()
        menu()
    }

    private fun menu() {
        imageviewmenu = findViewById(R.id.imageviewmenu)
        imageviewmenu?.setOnClickListener(View.OnClickListener {
            val popupMenu = PopupMenu(this@NhanVien, imageviewmenu)
            popupMenu.menuInflater.inflate(R.menu.menudangxuat, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.dangxuat) {
                    MainActivity.editor!!.remove("nhanvien")
                    startActivity(Intent(applicationContext, Login::class.java))
                    finish()
                }
                false
            }
        })
    }

    private fun getdatadangvanchuyennadmin() {
        val dataService = APIServices.getService()
        val callback = dataService.getdatadangvanchuyenadmin()
        callback.enqueue(object : Callback<List<DonDatHang?>?> {
            override fun onResponse(
                call: Call<List<DonDatHang?>?>,
                response: Response<List<DonDatHang?>?>
            ) {
                Log.d("AAA", "getdata Dangvanchuyen:$response")
                if (response.isSuccessful) {
                    val arrayList: ArrayList<*>? = response.body() as? ArrayList<*>?
                    val adapter = (arrayList as ArrayList<DonDatHang>?)?.let {
                        Adapter_chitietdonhang_nhanviengiaohang(
                            it,
                            this@NhanVien,
                            R.layout.layout_donhangcuaban
                        )
                    }
                    recyclerviewdangvanchuyennhanvien!!.adapter = adapter
                    adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<DonDatHang?>?>, t: Throwable) {}
        })
    }

    private fun anhxa() {
        recyclerviewdangvanchuyennhanvien = findViewById(R.id.recyclerviewdangvanchuyennhanvien)
        recyclerviewdangvanchuyennhanvien?.setHasFixedSize(true)
        recyclerviewdangvanchuyennhanvien?.setLayoutManager(GridLayoutManager(applicationContext, 1))
    }
}