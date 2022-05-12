package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cuahang_doan.Adapter.Adapter_Tintuc
import android.widget.EditText
import android.os.Bundle
import com.example.cuahang_doan.R
import android.text.TextWatcher
import android.text.Editable
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.Activity.admin.Inssert_TinTuc
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cuahang_doan.model.TinTuc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class TinTuc : AppCompatActivity() {
    private val toolbartintuc: Toolbar? = null
    private var recyclerviewtintuc: RecyclerView? = null
    private var recyclerviewtintucphu: RecyclerView? = null
    private var arrayList: ArrayList<TinTuc>? = null
    private val adapter: Adapter_Tintuc? = null
    private var edttimkimtintuc: EditText? = null
    private var imgsearch: ImageView? = null
    private var imgback: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tin_tuc)
        anhxa()
        getdatatintuc()
        setOnclick()
    }

    fun reload() {
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun setOnclick() {
        imgback!!.setOnClickListener { finish() }
        edttimkimtintuc!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val timkim = edttimkimtintuc!!.text.toString()
                val dataService = APIServices.getService()
                val callback = dataService.getdatatimkimtintuc(timkim)
                callback.enqueue(object : Callback<List<TinTuc>?> {
                    override fun onResponse(
                        call: Call<List<TinTuc>?>,
                        response: Response<List<TinTuc>?>
                    ) {
                        Log.d("AAA", "Getdata timkim tintuc: $response")
                        if (response.isSuccessful) {
                            val arrayList = response.body() as ArrayList<TinTuc>?
                            setdataRecyclerviewphu(arrayList)
                        }
                    }

                    override fun onFailure(call: Call<List<TinTuc>?>, t: Throwable) {}
                })
            }
        })
        imgsearch!!.setOnClickListener {
            val popupMenu = PopupMenu(this@TinTuc, imgsearch)
            popupMenu.menuInflater.inflate(R.menu.menu_quanlysanpham, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.them) {
                    startActivity(Intent(applicationContext, Inssert_TinTuc::class.java))
                }
                false
            }
            popupMenu.show()
        }
    }

    private fun setdataRecyclerview(arrayList: ArrayList<TinTuc>) {
        recyclerviewtintuc!!.layoutManager = GridLayoutManager(applicationContext, 1)
        recyclerviewtintuc!!.setHasFixedSize(true)
        val adapter = Adapter_Tintuc(this@TinTuc, R.layout.layout_tintuc, arrayList)
        recyclerviewtintuc!!.adapter = adapter
    }

    private fun setdataRecyclerviewphu(arrayList: ArrayList<TinTuc>?) {
        recyclerviewtintucphu!!.layoutManager = GridLayoutManager(applicationContext, 1)
        recyclerviewtintucphu!!.setHasFixedSize(true)
        val adapter = arrayList?.let { Adapter_Tintuc(this@TinTuc, R.layout.layouttintucphu, it) }
        recyclerviewtintucphu!!.adapter = adapter
    }

    private fun anhxa() {
        imgback = findViewById(R.id.imgback)
        imgsearch = findViewById(R.id.imgsearch)
        edttimkimtintuc = findViewById(R.id.edttimkimtintuc)
        recyclerviewtintucphu = findViewById(R.id.recyclerviewtintucphu)
        arrayList = ArrayList()
        recyclerviewtintuc = findViewById(R.id.recyclerviewtintuc)
    }

    private fun getdatatintuc() {
        val dataService = APIServices.getService()
        val callback = dataService.getdatatintuc()
        callback.enqueue(object : Callback<List<TinTuc>?> {
            override fun onResponse(call: Call<List<TinTuc>?>, response: Response<List<TinTuc>?>) {
                Log.d("AAA", "Get dtatatintuc: $response")
                if (response.isSuccessful) {
                    arrayList = response.body() as ArrayList<TinTuc>?
                    val arrayListchinh = ArrayList<TinTuc>()
                    arrayListchinh.add(
                        TinTuc(
                            arrayList!![0].id,
                            arrayList!![0].hinhbaiviet,
                            arrayList!![0].tenbaiviet,
                            arrayList!![0].noidung,
                            arrayList!![0].thoigian
                        )
                    )
                    setdataRecyclerview(arrayListchinh)
                    setdataRecyclerviewphu(arrayList)
                }
            }

            override fun onFailure(call: Call<List<TinTuc>?>, t: Throwable) {}
        })
    }
}