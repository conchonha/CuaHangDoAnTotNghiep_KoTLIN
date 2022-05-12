package com.example.cuahang_doan.Activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.example.cuahang_doan.model.GioithieuShop
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import android.util.Log
import android.widget.Button
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Activity.Gioi_Thieu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateThongtin : AppCompatActivity() {
    private var edttencuahang: EditText? = null
    private var edttrusochinh: EditText? = null
    private var edtsodienthoaigioithieu: EditText? = null
    private var edittexemail: EditText? = null
    private var edittextwebsite: EditText? = null
    private var edittexFanpage: EditText? = null
    private var gioithieu: GioithieuShop? = null
    private var btnupdatethongtin: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_thongtin)
        anhxa()
        setdulieuchocactruong()
    }

    private fun setdulieuchocactruong() {
        val intent = intent
        if (intent != null) {
            gioithieu = intent.getSerializableExtra("updatethongtinshop") as GioithieuShop
            edittexemail!!.setText(gioithieu!!.email)
            edittexFanpage!!.setText(gioithieu!!.fanpage)
            edittextwebsite!!.setText(gioithieu!!.website)
            edtsodienthoaigioithieu!!.setText(gioithieu!!.dienThoai)
            edttencuahang!!.setText(gioithieu!!.tenCuaHang)
            edttrusochinh!!.setText(gioithieu!!.truSoChinh)
            btnupdatethongtin!!.setOnClickListener {
                val dataService = APIServices.getService()
                val callback = dataService.Updatethongtinshop(
                    edttencuahang!!.text.toString(),
                    edttrusochinh!!.text.toString(),
                    edtsodienthoaigioithieu!!.text.toString(),
                    edittexemail!!.text.toString(),
                    edittextwebsite!!.text.toString(),
                    edittexFanpage!!.text.toString()
                )
                callback.enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        Log.d("AAA", "Update thongtinshop: $response")
                        if (response.isSuccessful) {
                            startActivity(Intent(applicationContext, Gioi_Thieu::class.java))
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {}
                })
            }
        }
    }

    private fun anhxa() {
        btnupdatethongtin = findViewById(R.id.btnupdatethongtin)
        edttencuahang = findViewById(R.id.edttencuahang)
        edttrusochinh = findViewById(R.id.edttrusochinh)
        edtsodienthoaigioithieu = findViewById(R.id.edtsodienthoaigioithieu)
        edittexemail = findViewById(R.id.edittexemail)
        edittextwebsite = findViewById(R.id.edittextwebsite)
        edittexFanpage = findViewById(R.id.edittexFanpage)
    }
}