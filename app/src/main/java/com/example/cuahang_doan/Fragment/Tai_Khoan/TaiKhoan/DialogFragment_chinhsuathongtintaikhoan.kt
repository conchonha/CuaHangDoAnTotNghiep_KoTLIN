package com.example.cuahang_doan.Fragment.Tai_Khoan.TaiKhoan

import android.widget.EditText
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Activity.MainActivity
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.cuahang_doan.Activity.Login
import com.example.cuahang_doan.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class DialogFragment_chinhsuathongtintaikhoan : DialogFragment() {
    private var view1: View? = null
    private var edttentaikhoan: EditText? = null
    private var edtsodienthoai: EditText? = null
    private var edtemail: EditText? = null
    private var btnsacnhanthongtintaikhoan: Button? = null
    private var btnhuythongtintaikhoan: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.layout_chinhsuathongtin_dialog, container, false)
        anhxa()
        init()
        sukienclick()
        return view1
    }

    private fun sukienclick() {
        btnhuythongtintaikhoan!!.setOnClickListener {
            edtemail!!.setText("")
            edtsodienthoai!!.setText("")
            edttentaikhoan!!.setText("")
        }
        btnsacnhanthongtintaikhoan!!.setOnClickListener {
            if (edtemail!!.text.toString().endsWith("@gmail.com") &&
                edtsodienthoai!!.text.toString().startsWith("086") ||
                edtsodienthoai!!.text.toString().startsWith("096")
                || edtsodienthoai!!.text.toString().startsWith("097") ||
                edtsodienthoai!!.text.toString().startsWith("098")
                || edtsodienthoai!!.text.toString().startsWith("032") ||
                edtsodienthoai!!.text.toString().startsWith("032")
                || edtsodienthoai!!.text.toString().startsWith("034") ||
                edtsodienthoai!!.text.toString().startsWith("035")
                || edtsodienthoai!!.text.toString().startsWith("036") ||
                edtsodienthoai!!.text.toString().startsWith("036")
                || edtsodienthoai!!.text.toString().startsWith("037") ||
                edtsodienthoai!!.text.toString().startsWith("038")
                || edtsodienthoai!!.text.toString().startsWith("039") ||
                edtsodienthoai!!.text.toString().startsWith("089")
                || edtsodienthoai!!.text.toString().startsWith("090") ||
                edtsodienthoai!!.text.toString().startsWith("093")
                || edtsodienthoai!!.text.toString().startsWith("070") ||
                edtsodienthoai!!.text.toString().startsWith("079")
                || edtsodienthoai!!.text.toString().startsWith("079") ||
                edtsodienthoai!!.text.toString().startsWith("077")
                || edtsodienthoai!!.text.toString()
                    .startsWith("076") || (edtsodienthoai!!.text.toString().startsWith("078")
                        && edttentaikhoan!!.text.toString() != "")
            ) {
                val dataService = APIServices.getService()
                val callback = dataService.updatethongtintaikhoan(
                    edttentaikhoan!!.text.toString(),
                    edtsodienthoai!!.text.toString(),
                    edtemail!!.text.toString(),
                    MainActivity.sharedPreferences!!.getInt("iduser", 0).toString() + ""
                )
                callback.enqueue(object : Callback<List<User>> {
                    override fun onResponse(
                        call: Call<List<User>>,
                        response: Response<List<User>>
                    ) {
                        Log.d("AAA", "Update thongtintaikhoan: $response")
                        if (response.isSuccessful) {
                            Toast.makeText(activity, "Update Thành Công", Toast.LENGTH_SHORT).show()
                            val arrayList = response.body() as ArrayList<User>
                            MainActivity.editor!!.putString(
                                "username",
                                edttentaikhoan!!.text.toString()
                            )
                            MainActivity.editor!!.putInt("iduser", arrayList[0]?.id ?: 0)
                            MainActivity.editor!!.putString("email", arrayList[0].email)
                            MainActivity.editor!!.putString("sodienthoai", arrayList[0].phoneNumBer)
                            MainActivity.editor!!.putString(
                                "hinh",
                                APIServices.urlhinh + arrayList[0].id + ".jpg"
                            )
                            MainActivity.editor!!.putString("diachi", arrayList[0].adress)
                            MainActivity.editor!!.commit()
                            activity!!.finish()
                            startActivity(activity!!.intent)
                            activity!!.overridePendingTransition(0, 0)
                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {}
                })
            }
        }
    }

    private fun init() {
        if (MainActivity.sharedPreferences!!.getString("username", "") != "") {
            edttentaikhoan!!.setText(MainActivity.sharedPreferences!!.getString("username", ""))
            edtemail!!.setText(MainActivity.sharedPreferences!!.getString("email", ""))
            edtsodienthoai!!.setText(MainActivity.sharedPreferences!!.getString("sodienthoai", ""))
        } else {
            startActivity(Intent(activity, Login::class.java))
        }
    }

    private fun anhxa() {
        btnhuythongtintaikhoan = view1!!.findViewById(R.id.btnhuythongtintaikhoan)
        btnsacnhanthongtintaikhoan = view1!!.findViewById(R.id.btnsacnhanthongtintaikhoan)
        edtsodienthoai = view1!!.findViewById(R.id.edtsodienthoai)
        edtemail = view1!!.findViewById(R.id.edtemail)
        edttentaikhoan = view1!!.findViewById(R.id.edttentaikhoan)
    }
}