package com.example.cuahang_doan.Fragment.TrangChu

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.model.GetdataSanphammoinhat
import com.example.cuahang_doan.Adapter.Adapter_Sanphammoinhat
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Fragment_Sanphammoinhat : Fragment() {
    private var recyclerviewSanphammoinhat: RecyclerView? = null
    private var view1: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_sanphammoinhat, container, false)
        anhxa()
        getdatasanphammoinhat()
        return view1
    }

    private fun getdatasanphammoinhat() {
        val dataService = APIServices.getService()
        val callback = dataService.dataSanphammoinhat
        callback.enqueue(object : Callback<List<GetdataSanphammoinhat>> {
            override fun onResponse(
                call: Call<List<GetdataSanphammoinhat>>,
                response: Response<List<GetdataSanphammoinhat>>
            ) {
                Log.d("AAA", "getdatasanphammoinhat$response")
                if (response.isSuccessful) {
                    val arrayList = response.body() as ArrayList<GetdataSanphammoinhat>
                    if (arrayList[0].hinhAnhSanPham != "") {
                        val adapter_sanphammoinhat = Adapter_Sanphammoinhat(
                            activity!!,
                            R.layout.layout_sanphammoinhat, arrayList
                        )
                        adapter_sanphammoinhat.notifyDataSetChanged()
                        recyclerviewSanphammoinhat!!.adapter = adapter_sanphammoinhat
                    }
                }
            }

            override fun onFailure(call: Call<List<GetdataSanphammoinhat>>, t: Throwable) {}
        })
    }

    private fun anhxa() {
        recyclerviewSanphammoinhat = view1!!.findViewById(R.id.recyclerviewSanphammoinhat)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewSanphammoinhat?.setLayoutManager(linearLayoutManager)
        recyclerviewSanphammoinhat?.setHasFixedSize(true)
        recyclerviewSanphammoinhat?.setItemViewCacheSize(20)
    }
}