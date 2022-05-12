package com.example.cuahang_doan.Fragment.TrangChu

import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.model.GetdataSanphammoinhat
import com.example.cuahang_doan.Adapter.Adapter_Sanphammoinhat
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cuahang_doan.Activity.SanPham
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Fragment_Thietbinghenhin : Fragment() {
    private var view1: View? = null
    private var recyclerView: RecyclerView? = null
    private var txtxemthem2: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragmentthietbinghenhin, container, false)
        anhxa()
        getdataThietbinghenhin()
        return view1
    }

    private fun getdataThietbinghenhin() {
        val dataService = APIServices.getService()
        val callback = dataService.dataThietbinghenhin
        callback.enqueue(object : Callback<List<GetdataSanphammoinhat>> {
            override fun onResponse(
                call: Call<List<GetdataSanphammoinhat>>,
                response: Response<List<GetdataSanphammoinhat>>
            ) {
                Log.d("BB", "thietbinghenhin$response")
                if (response.isSuccessful) {
                    val arrayList = response.body() as ArrayList<GetdataSanphammoinhat>
                    val adapter = Adapter_Sanphammoinhat(
                        activity!!,
                        R.layout.layout_linhkienlaptop,
                        arrayList
                    )
                    adapter.notifyDataSetChanged()
                    recyclerView!!.adapter = adapter
                    txtxemthem2!!.setOnClickListener {
                        val intent = Intent(activity, SanPham::class.java)
                        intent.putExtra("id", arrayList[0].idDanhmuc.toString() + "")
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<List<GetdataSanphammoinhat>>, t: Throwable) {}
        })
    }

    private fun anhxa() {
        txtxemthem2 = view1!!.findViewById(R.id.txtxemthem2)
        recyclerView = view1!!.findViewById(R.id.recyclerviewthietbinghenhin)
        recyclerView?.setLayoutManager(GridLayoutManager(activity, 2))
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setItemViewCacheSize(20)
    }
}