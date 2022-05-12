package com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.model.DonDatHang
import com.example.cuahang_doan.Adapter.Adapter_Donhangcuaban
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Fragment_DaHuy : Fragment() {
    private var view1: View? = null
    private var recyclerviewdahuydon: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_dahuydondathang, container, false)
        anhxxa()
        if (MainActivity.sharedPreferences!!.getString("admin", "") == "") {
            getdatadahuynguoidung()
        } else {
            getdatadahuyadmin()
        }
        return view1
    }

    private fun getdatadahuynguoidung() {
        val dataService = APIServices.getService()
        val callback = dataService.getdatadahuy(
            MainActivity.sharedPreferences!!.getInt("iduser", 0).toString() + ""
        )
        callback.enqueue(object : Callback<List<DonDatHang?>?> {
            override fun onResponse(
                call: Call<List<DonDatHang?>?>,
                response: Response<List<DonDatHang?>?>
            ) {
                Log.d("AAA", "getdata Dahuy: $response")
                if (response.isSuccessful) {
                    val arrayList: ArrayList<*>? = response.body() as ArrayList<*>?
                    val adapter = Adapter_Donhangcuaban(
                        arrayList as ArrayList<DonDatHang>,
                        activity!!,
                        R.layout.layout_donhangcuaban
                    )
                    recyclerviewdahuydon!!.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<DonDatHang?>?>, t: Throwable) {}
        })
    }

    private fun getdatadahuyadmin() {
        val dataService = APIServices.getService()
        val callback = dataService.getdatadahuyadmin()
        callback.enqueue(object : Callback<List<DonDatHang?>?> {
            override fun onResponse(
                call: Call<List<DonDatHang?>?>,
                response: Response<List<DonDatHang?>?>
            ) {
                Log.d("AAA", "getdata Dahuy: $response")
                if (response.isSuccessful) {
                    val arrayList: ArrayList<*>? = response.body() as ArrayList<*>?
                    val adapter = Adapter_Donhangcuaban(
                        arrayList as ArrayList<DonDatHang>,
                        activity!!,
                        R.layout.layout_donhangcuaban
                    )
                    recyclerviewdahuydon!!.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<DonDatHang?>?>, t: Throwable) {}
        })
    }

    private fun anhxxa() {
        recyclerviewdahuydon = view1!!.findViewById(R.id.recyclerviewdahuydon)
        recyclerviewdahuydon?.setHasFixedSize(true)
        recyclerviewdahuydon?.setLayoutManager(GridLayoutManager(context, 1))
    }
}