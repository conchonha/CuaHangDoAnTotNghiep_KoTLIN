package com.example.cuahang_doan.Fragment.Tai_Khoan.TaiKhoan

import android.widget.EditText
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class DialogFragment_Chinhsuadiachi : DialogFragment() {
    private var view1: View? = null
    private var edtdiachi: EditText? = null
    private var btnhuythongtintaikhoan1: Button? = null
    private var btnsacnhanthongtintaikhoan1: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.layout_chinhsuadiachi_dialogfragment, container, false)
        anhxa()
        init()
        return view1
    }

    private fun init() {
        if (MainActivity.sharedPreferences!!.getString("diachi", "") != "") {
            edtdiachi!!.setText(MainActivity.sharedPreferences!!.getString("diachi", ""))
            btnhuythongtintaikhoan1!!.setOnClickListener { edtdiachi!!.setText("") }
            btnsacnhanthongtintaikhoan1!!.setOnClickListener {
                val dataService = APIServices.getService()
                val callback = dataService.updatediachi(
                    edtdiachi!!.text.toString(),
                    MainActivity.sharedPreferences!!.getInt("iduser", 0).toString() + ""
                )
                callback.enqueue(object : Callback<List<User>> {
                    override fun onResponse(
                        call: Call<List<User>>,
                        response: Response<List<User>>
                    ) {
                        Log.d("AAA", "Update diachi: $response")
                        if (response.isSuccessful) {
                            val arrayList = response.body() as ArrayList<User>
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

    private fun anhxa() {
        edtdiachi = view1!!.findViewById(R.id.edtdiachi)
        btnhuythongtintaikhoan1 = view1!!.findViewById(R.id.btnhuythongtintaikhoan1)
        btnsacnhanthongtintaikhoan1 = view1!!.findViewById(R.id.btnsacnhanthongtintaikhoan1)
    }
}