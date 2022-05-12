package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.example.cuahang_doan.R
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.model.GetdataSanphammoinhat
import com.example.cuahang_doan.Adapter.Adapter_SanPham
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SanPham : AppCompatActivity() {
    private var recyclerviewsanphamdanhmuc: RecyclerView? = null
    private var giatri = ""
    private var tollbarsanpham: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_san_pham)
        anhxa()
        getdatasp()
    }

    private fun settitleToolbar(id: String) {
        if (id == "1") {
            tollbarsanpham!!.title = "LapTop - Macbook"
        }
        if (id == "2") {
            tollbarsanpham!!.title = "Linh Kiện LapTop"
        }
        if (id == "3") {
            tollbarsanpham!!.title = "TB Lưu Trữ - Phụ Kiện"
        }
        if (id == "4") {
            tollbarsanpham!!.title = "Thiết Bị Nghe - Nhìn"
        }
    }

    private fun getdatasp() {
        val intent = intent
        if (intent.hasExtra("id")) {
            giatri = intent.getStringExtra("id")
            settitleToolbar(giatri)
            Toast.makeText(this, giatri, Toast.LENGTH_SHORT).show()
            val dataService = APIServices.getService()
            val callback = dataService.getdatasanphamdanhmuc(giatri)
            callback.enqueue(object : Callback<List<GetdataSanphammoinhat?>?> {
                override fun onResponse(
                    call: Call<List<GetdataSanphammoinhat?>?>,
                    response: Response<List<GetdataSanphammoinhat?>?>
                ) {
                    Log.d("AAA", "San Pham tim kiem: $response" + "---- post: "+giatri)
                    if (response.isSuccessful) {
                        val arrayList = response.body() as ArrayList<GetdataSanphammoinhat?>?
                        arrayList?.sortWith(Comparator { o1, o2 ->
                            if (o1?.loai == o2?.loai) {
                                1
                            } else {
                                1
                            }
                        })
                        val adapter =
                            Adapter_SanPham(this@SanPham, R.layout.layoutsanpham, arrayList)
                        recyclerviewsanphamdanhmuc!!.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<List<GetdataSanphammoinhat?>?>, t: Throwable) {}
            })
        }
    }

    private fun anhxa() {
        tollbarsanpham = findViewById(R.id.tollbarsanpham)
        setSupportActionBar(tollbarsanpham)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        tollbarsanpham?.setNavigationIcon(R.drawable.back)
        tollbarsanpham?.setNavigationOnClickListener(View.OnClickListener { finish() })
        recyclerviewsanphamdanhmuc = findViewById(R.id.recyclerviewsanphamdanhmuc)
        recyclerviewsanphamdanhmuc?.setHasFixedSize(true)
        recyclerviewsanphamdanhmuc?.setLayoutManager(GridLayoutManager(applicationContext, 1))
    }
}