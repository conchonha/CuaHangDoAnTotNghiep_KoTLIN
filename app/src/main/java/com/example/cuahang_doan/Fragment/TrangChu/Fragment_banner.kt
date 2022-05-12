package com.example.cuahang_doan.Fragment.TrangChu

import androidx.viewpager.widget.ViewPager
import com.rd.PageIndicatorView
import com.example.cuahang_doan.Adapter.BannerAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.model.QuangCao
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Fragment_banner : Fragment() {
    private var view1: View? = null
    private var FragmentBanerViewpager: ViewPager? = null
    private var pageIndicatorView: PageIndicatorView? = null
    private var adapter: BannerAdapter? = null
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private var CurrentItem = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_banner, container, false)
        anhxa()
        GetdataBanner()
        return view1
    }

    private fun GetdataBanner() {
        val dataService = APIServices.getService()
        val callback = dataService.GetdataQuangCao()
        callback.enqueue(object : Callback<List<QuangCao?>> {
            override fun onResponse(
                call: Call<List<QuangCao?>>,
                response: Response<List<QuangCao?>>
            ) {
                Log.d("AAA", "Banner$response")
                if (response.isSuccessful) {
                    val arrayList = response.body() as ArrayList<*>
                    if (arrayList.size > 0) {
                        Collections.shuffle(arrayList)
                        adapter = BannerAdapter(view1!!.context, arrayList as ArrayList<QuangCao>)
                        adapter!!.notifyDataSetChanged()
                        FragmentBanerViewpager!!.adapter = adapter
                        pageIndicatorView!!.setViewPager(FragmentBanerViewpager)
                        autoSlideViewpager()
                    }
                }
            }

            override fun onFailure(call: Call<List<QuangCao?>>, t: Throwable) {
                Toast.makeText(
                    activity,
                    "Tạm thời không lấy được dữ liệu vui lòng quay lại sau",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("AAA", "Banner$t")
            }
        })
    }

    private fun anhxa() {
        FragmentBanerViewpager = view1!!.findViewById(R.id.FragmentBanerViewpager)
        pageIndicatorView = view1!!.findViewById(R.id.PageIndicatorview)
    }

    private fun autoSlideViewpager() {
        handler = Handler()
        runnable = Runnable {
            CurrentItem = FragmentBanerViewpager!!.currentItem
            CurrentItem++
            if (CurrentItem >= FragmentBanerViewpager!!.adapter!!.count) {
                CurrentItem = 0
            }
            FragmentBanerViewpager!!.setCurrentItem(CurrentItem, true)
            handler!!.postDelayed(runnable, 4500)
        }
        handler!!.postDelayed(runnable, 4500)
    }
}