package com.example.cuahang_doan.Fragment.Gio_Hang

import android.widget.EditText
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.cuahang_doan.R
import android.widget.Toast
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Activity.MainActivity
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.cuahang_doan.Activity.Hoadon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DialogFragment_Thanhtoangiohang : DialogFragment() {
    private lateinit var view1 : View
    private var imglaythongtin: ImageView? = null
    private var btnsacnhan: Button? = null
    private var edthoten: EditText? = null
    private var edtdiachi: EditText? = null
    private var edtsodienthoai: EditText? = null
    private var edtemail: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.dialogfragment_thanhtoangiohang, container, false)
        anhxa()
        sukienclick()
        return view1
    }

    private fun sukienclick() {
        imglaythongtin!!.setOnClickListener {
            val fragmentManager = fragmentManager
            val dialogFragment_timkiem = DialogFragment_timkiem()
            dialogFragment_timkiem.show(fragmentManager, "dialogtimkiem")
        }
        btnsacnhan!!.setOnClickListener {
            if (edthoten!!.text.toString() == "" || edtdiachi!!.text.toString() == "" || edtsodienthoai!!.text.toString() == "" || edtemail!!.text.toString() == "") {
                Toast.makeText(activity, "Vui lòng không để trống dữ liệu", Toast.LENGTH_SHORT)
                    .show()
            } else if (edtsodienthoai!!.text.toString().length != 10) {
                Toast.makeText(activity, "sai số điện thoại", Toast.LENGTH_SHORT).show()
            } else {
                val calendar = Calendar.getInstance()
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                val ngay = simpleDateFormat.format(calendar.time)
                val dataService = APIServices.getService()
                val callback = dataService.posdondathang(
                    MainActivity.sharedPreferences!!.getInt("iduser", 0).toString() + "",
                    "Chờ Xét Duyệt", ngay, edthoten!!.text.toString(), edtdiachi!!.text.toString(),
                    edtemail!!.text.toString(), edtsodienthoai!!.text.toString()
                )
                callback.enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        Log.d("AAA", "Post đơn đặt hang: $response")
                        if (response.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Đơn hàng của bạn đã được chuyển đi",
                                Toast.LENGTH_SHORT
                            ).show()
                            try {
                                Thread.sleep(3000)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                            startActivity(Intent(context, Hoadon::class.java))
                            Handler().postDelayed({
                                MainActivity.mainTablayout!!.getTabAt(0)!!.select()
                            }, 100)
                            val fragment = fragmentManager!!.findFragmentByTag("dialogkhachhangmoi")
                            if (fragment != null) {
                                val df = fragment as DialogFragment
                                df.dismiss()
                            }
                            Toast.makeText(
                                activity,
                                "Đơn hàng của bạn đã được chuyển đi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {}
                })
            }
        }
    }

    private fun anhxa() {
        imglaythongtin = view1!!.findViewById(R.id.imglaythongtin)
        edthoten = view1!!.findViewById(R.id.edthoten)
        edtdiachi = view1!!.findViewById(R.id.edtdiachi)
        edtemail = view1!!.findViewById(R.id.edtemail)
        edtsodienthoai = view1!!.findViewById(R.id.edtsodienthoai)
        btnsacnhan = view1!!.findViewById(R.id.btnsacnhan)
    }
}