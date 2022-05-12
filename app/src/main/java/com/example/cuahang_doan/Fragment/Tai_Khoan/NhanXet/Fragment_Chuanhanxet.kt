package com.example.cuahang_doan.Fragment.Tai_Khoan.NhanXet

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Adapter.Adapter_nhanxet
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cuahang_doan.model.Nhanxetcuaban
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Fragment_Chuanhanxet : Fragment() {
    private var view1: View? = null
    private var recyclerviewchuanhanxet: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_chuanhanxet, container, false)
        anhxa()
        getdatachuanhanxet()
        return view1
    }

    private fun getdatachuanhanxet() {
        if (MainActivity.sharedPreferences!!.getString("username", "") != "") {
            val dataService = APIServices.getService()
            val callback = dataService.getdatachuanhanxet(
                MainActivity.sharedPreferences!!.getInt("iduser", 0).toString() + "", "chuanhanxet"
            )
            callback.enqueue(object : Callback<List<Nhanxetcuaban?>?> {
                override fun onResponse(
                    call: Call<List<Nhanxetcuaban?>?>,
                    response: Response<List<Nhanxetcuaban?>?>
                ) {
                    Log.d("AAA", "get Data chua nhan xet: $response")
                    if (response.isSuccessful) {
                        val arrayList = response.body() as ArrayList<Nhanxetcuaban?>?
                        val adapter =
                            Adapter_nhanxet(R.layout.layout_chuanhanxet, context!!, arrayList)
                        recyclerviewchuanhanxet!!.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<List<Nhanxetcuaban?>?>, t: Throwable) {}
            })
        } else {
            Toast.makeText(context, "Đăng nhập tài khoản để tiếp tục", Toast.LENGTH_SHORT).show()
        }
    }

    private fun anhxa() {
        recyclerviewchuanhanxet = view1!!.findViewById(R.id.recyclerviewchuanhanxet)
        recyclerviewchuanhanxet?.setHasFixedSize(true)
        recyclerviewchuanhanxet?.setLayoutManager(GridLayoutManager(context, 1))
    }
}