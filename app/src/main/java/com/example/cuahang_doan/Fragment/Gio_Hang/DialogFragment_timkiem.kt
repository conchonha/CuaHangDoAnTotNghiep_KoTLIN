package com.example.cuahang_doan.Fragment.Gio_Hang

import com.example.cuahang_doan.Adapter.TimkiemTaikhoan_Adapter
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.APIServices
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cuahang_doan.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class DialogFragment_timkiem : DialogFragment() {
    private var view1: View? = null
    private var arrayList: ArrayList<User?>? = null
    private var adapter: TimkiemTaikhoan_Adapter? = null
    private var recyclerviewtimkiemtaikhoan: RecyclerView? = null
    private var edttimkiem: EditText? = null
    private var imgtimkiem: ImageView? = null
    private var txtdong: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.dialogfragment_timkiem, container, false)
        anhxa()
        getdatataikhoan()
        timkiem()
        dong()
        return view1
    }

    fun reload() {
        activity!!.finish()
        startActivity(activity!!.intent)
    }

    private fun dong() {
        txtdong!!.setOnClickListener {
            val prev = fragmentManager!!.findFragmentByTag("dialogtimkiem")
            if (prev != null) {
                val df = prev as DialogFragment
                df.dismiss()
            }
        }
    }

    private fun timkiem() {
        imgtimkiem!!.setOnClickListener { view ->
            if (edttimkiem!!.text.toString() != "" && arrayList!!.size > 0) {
                val dataService = APIServices.getService()
                val callback = dataService.timkiem(
                    edttimkiem!!.text.toString()
                )
                callback.enqueue(object : Callback<List<User?>?> {
                    override fun onResponse(
                        call: Call<List<User?>?>,
                        response: Response<List<User?>?>
                    ) {
                        Log.d("AAA", "event timkiem: $response")
                        if (response.isSuccessful) {
                            arrayList = response.body() as ArrayList<User?>?
                            adapter = TimkiemTaikhoan_Adapter(
                                arrayList,
                                R.layout.layout_taikhoan_giohang,
                                activity!!
                            )
                            adapter!!.notifyDataSetChanged()
                            recyclerviewtimkiemtaikhoan!!.adapter = adapter
                        } else {
                            Toast.makeText(
                                view.context,
                                "Không tìm thấy kết quả",
                                Toast.LENGTH_SHORT
                            ).show()
                            getdatataikhoan()
                        }
                    }

                    override fun onFailure(call: Call<List<User?>?>, t: Throwable) {}
                })
            } else {
                Toast.makeText(view.context, "không để trống dữ liệu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getdatataikhoan() {
        val dataService = APIServices.getService()
        val callback = dataService.getdatataikhoan()
        callback.enqueue(object : Callback<List<User?>?> {
            override fun onResponse(call: Call<List<User?>?>, response: Response<List<User?>?>) {
                Log.d("AAA", "getdata taikhoan: $response")
                if (response.isSuccessful) {
                    arrayList = response.body() as ArrayList<User?>?
                    adapter = TimkiemTaikhoan_Adapter(
                        arrayList,
                        R.layout.layout_taikhoan_giohang,
                        view1!!.context
                    )
                    adapter!!.notifyDataSetChanged()
                    recyclerviewtimkiemtaikhoan!!.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<User?>?>, t: Throwable) {}
        })
    }

    private fun anhxa() {
        txtdong = view1!!.findViewById(R.id.txtdong)
        edttimkiem = view1!!.findViewById(R.id.edttimkiem)
        imgtimkiem = view1!!.findViewById(R.id.imgtimkiem)
        arrayList = ArrayList()
        recyclerviewtimkiemtaikhoan = view1!!.findViewById(R.id.recyclerviewtimkiemtaikhoan)
        recyclerviewtimkiemtaikhoan?.setLayoutManager(GridLayoutManager(view1!!.context, 2))
        recyclerviewtimkiemtaikhoan?.setHasFixedSize(true)
        recyclerviewtimkiemtaikhoan?.setItemViewCacheSize(20)
    }
}