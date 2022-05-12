package com.example.cuahang_doan.Activity.admin

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.APIServices
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.Activity.DangKyTaiKhoan
import com.example.cuahang_doan.Adapter.admin.Adapter_Quan_ly_tai_khoan
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cuahang_doan.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class QuanLytaikhoan : AppCompatActivity() {
    private var Recyclerview_Quan_ly_tai_khoan: RecyclerView? = null
    private var edtseachsanphamadmin: EditText? = null
    private var imgsearchadmin: ImageView? = null
    private var imgmenutaikhoanadmin: ImageView? = null
    private var toolbaradminquanlysp: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quan_lytaikhoan)
        anhxa()
        dataQuanLyTaiKhoanAdmin
        setActionBar()
    }

    fun reload() {
        finish()
        startActivity(intent)
    }

    private fun setActionBar() {
        setSupportActionBar(toolbaradminquanlysp)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbaradminquanlysp!!.setNavigationIcon(R.drawable.back)
        toolbaradminquanlysp!!.setNavigationOnClickListener { finish() }
    }

    private val dataQuanLyTaiKhoanAdmin: Unit
        private get() {
            val dataService = APIServices.getService()
            val callback = dataService.dataQuanLyTaiKhoanAdmin
            callback.enqueue(object : Callback<List<User?>?> {
                override fun onResponse(
                    call: Call<List<User?>?>,
                    response: Response<List<User?>?>
                ) {
                    Log.d("AAA", "GetdataquanlytaikhoanAdmin: $response")
                    if (response.isSuccessful) {
                        val arrayList = response.body() as ArrayList<User?>?
                        setRecyclerviewAdmin(arrayList)
                    } else {
                        Toast.makeText(
                            this@QuanLytaikhoan,
                            "Tạm thời không lấy được dữ liệu",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<User?>?>, t: Throwable) {}
            })
        }

    private fun timkim() {
        if (edtseachsanphamadmin!!.text.toString() != "") {
            val dataService = APIServices.getService()
            val callback = dataService.timkiem(
                edtseachsanphamadmin!!.text.toString()
            )
            callback.enqueue(object : Callback<List<User?>?> {
                override fun onResponse(
                    call: Call<List<User?>?>,
                    response: Response<List<User?>?>
                ) {
                    Log.d("AAA", "event timkiem: $response")
                    if (response.isSuccessful) {
                        val arrayList: ArrayList<*>? = response.body() as ArrayList<User?>?
                        setRecyclerviewAdmin(arrayList as ArrayList<User?>?)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Không tìm thấy kết quả",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<User?>?>, t: Throwable) {}
            })
        } else {
            Toast.makeText(applicationContext, "không để trống dữ liệu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun anhxa() {
        imgmenutaikhoanadmin = findViewById(R.id.imgmenutaikhoanadmin)
        imgmenutaikhoanadmin?.setOnClickListener(View.OnClickListener {
            val popupMenu = PopupMenu(this@QuanLytaikhoan, imgmenutaikhoanadmin)
            popupMenu.menuInflater.inflate(R.menu.menu_quan_ly_tai_khoan, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.khachhang) {
                    val intent = Intent(applicationContext, DangKyTaiKhoan::class.java)
                    intent.putExtra("khachhang", "khachhang")
                    startActivity(intent)
                }
                if (item.itemId == R.id.nhanvien) {
                    val intent = Intent(applicationContext, DangKyTaiKhoan::class.java)
                    intent.putExtra("nhanvien", "nhanvien")
                    startActivity(intent)
                }
                if (item.itemId == R.id.admin) {
                    val intent = Intent(applicationContext, DangKyTaiKhoan::class.java)
                    intent.putExtra("admin", "admin")
                    startActivity(intent)
                }
                false
            }
        })
        toolbaradminquanlysp = findViewById(R.id.toolbaradminquanlysp)
        imgsearchadmin = findViewById(R.id.imgsearchadmin)
        imgsearchadmin?.setOnClickListener(View.OnClickListener { timkim() })
        edtseachsanphamadmin = findViewById(R.id.edtseachsanphamadmin)
        Recyclerview_Quan_ly_tai_khoan = findViewById(R.id.Recyclerview_Quan_ly_tai_khoan)
    }

    private fun setRecyclerviewAdmin(arrayList: ArrayList<User?>?) {
        val adapter = Adapter_Quan_ly_tai_khoan(
            this@QuanLytaikhoan, R.layout.layout_quan_ly_tai_khoan_admin,
            arrayList
        )
        Recyclerview_Quan_ly_tai_khoan!!.setHasFixedSize(true)
        Recyclerview_Quan_ly_tai_khoan!!.layoutManager = GridLayoutManager(this, 1)
        Recyclerview_Quan_ly_tai_khoan!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}