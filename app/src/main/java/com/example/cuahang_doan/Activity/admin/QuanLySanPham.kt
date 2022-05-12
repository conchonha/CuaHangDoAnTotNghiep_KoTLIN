package com.example.cuahang_doan.Activity.admin

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.model.GetdataSanphammoinhat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cuahang_doan.Adapter.Adapter_SanPham
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class QuanLySanPham : AppCompatActivity() {
    private var recyclerviewquanlysanphamadmin: RecyclerView? = null
    private var txttendanhmucquanlyadmin: TextView? = null
    private var txtluachonadminsanpham: TextView? = null
    private var toolbaradminquanlysp: Toolbar? = null
    private var edtseachsanphamadmin: EditText? = null
    private var imgsearchadmin: ImageView? = null
    private var img_MenuProductAdmin: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quan_ly_san_pham)
        anhxa()
        getdatasanpham("1")
        PopupMenuluachon()
        timkim()
        onclick_Views()
    }

    private fun onclick_Views() {
        setSupportActionBar(toolbaradminquanlysp)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbaradminquanlysp!!.setNavigationIcon(R.drawable.back)
        toolbaradminquanlysp!!.setNavigationOnClickListener { finish() }
        img_MenuProductAdmin!!.setOnClickListener {
            val popupMenu = PopupMenu(applicationContext, img_MenuProductAdmin)
            popupMenu.menuInflater.inflate(R.menu.menu_quanlysanpham, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.them) {
                    startActivity(Intent(applicationContext, ThemSanPham::class.java))
                }
                false
            }
            popupMenu.show()
        }
    }

    private fun timkim() {
        imgsearchadmin!!.setOnClickListener {
            val search = edtseachsanphamadmin!!.text.toString()
            Toast.makeText(applicationContext, search, Toast.LENGTH_SHORT).show()
            val dataService = APIServices.getService()
            val callback = dataService.getdataTimkiemsanpham(search)
            callback.enqueue(object : Callback<List<GetdataSanphammoinhat?>?> {
                override fun onResponse(
                    call: Call<List<GetdataSanphammoinhat?>?>,
                    response: Response<List<GetdataSanphammoinhat?>?>
                ) {
                    Log.d("AAA", "Tim kim sp: $response")
                    if (response.isSuccessful) {
                        val arrayList = response.body() as ArrayList<GetdataSanphammoinhat?>?
                        addrecyclerview(arrayList)
                    } else {
                        Toast.makeText(
                            this@QuanLySanPham,
                            "Không tìm thấy dữ liệu",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<GetdataSanphammoinhat?>?>, t: Throwable) {
                    Log.d("AAA", "Loi timkim Sp: $t")
                }
            })
        }
    }

    private fun PopupMenuluachon() {
        txtluachonadminsanpham!!.setOnClickListener {
            val popupMenu = PopupMenu(this@QuanLySanPham, txtluachonadminsanpham)
            popupMenu.menuInflater.inflate(R.menu.menuluachonsanphamadmin, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.laptopmacbook) {
                    getdatasanpham("1")
                }
                if (item.itemId == R.id.linhkien) {
                    getdatasanpham("2")
                }
                if (item.itemId == R.id.tbluutrupk) {
                    getdatasanpham("3")
                }
                if (item.itemId == R.id.tbnghenhin) {
                    getdatasanpham("4")
                }
                false
            }
            popupMenu.show()
        }
    }

    private fun anhxa() {
        img_MenuProductAdmin = findViewById(R.id.img_MenuProductAdmin)
        edtseachsanphamadmin = findViewById(R.id.edtseachsanphamadmin)
        imgsearchadmin = findViewById(R.id.imgsearchadmin)
        toolbaradminquanlysp = findViewById(R.id.toolbaradminquanlysp)
        txtluachonadminsanpham = findViewById(R.id.txtluachonadminsanpham)
        txttendanhmucquanlyadmin = findViewById(R.id.txttendanhmucquanlyadmin)
    }

    private fun getdatasanpham(id: String) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
        val dataService = APIServices.getService()
        val callback = dataService.getdatasanphamdanhmuc(id)
        callback.enqueue(object : Callback<List<GetdataSanphammoinhat?>?> {
            override fun onResponse(
                call: Call<List<GetdataSanphammoinhat?>?>,
                response: Response<List<GetdataSanphammoinhat?>?>
            ) {
                Log.d("AAA", "San Pham tim kiem: $response")
                if (response.isSuccessful) {
                    val arrayList = response.body() as ArrayList<GetdataSanphammoinhat?>?
                    Collections.sort(arrayList) { o1, o2 ->
                        if (o1?.loai == o2?.loai) {
                            1
                        } else {
                            1
                        }
                    }
                    //  Adapter_SanPham adapter=new Adapter_SanPham(QuanLySanPham.this,R.layout.layoutsanpham,arrayList);
                    addrecyclerview(arrayList)
                    // adapter.notifyDataSetChanged();
                }
            }

            override fun onFailure(call: Call<List<GetdataSanphammoinhat?>?>, t: Throwable) {}
        })
    }

    fun addrecyclerview(arrayList: ArrayList<GetdataSanphammoinhat?>?) {
        recyclerviewquanlysanphamadmin = findViewById(R.id.recyclerviewquanlysanphamadmin)
        recyclerviewquanlysanphamadmin?.setHasFixedSize(true)
        recyclerviewquanlysanphamadmin?.setLayoutManager(GridLayoutManager(this, 1))
        val adapter = Adapter_SanPham(applicationContext, R.layout.layoutsanphamadmin, arrayList)
        recyclerviewquanlysanphamadmin?.setAdapter(adapter)
        adapter.notifyDataSetChanged()
    }
}