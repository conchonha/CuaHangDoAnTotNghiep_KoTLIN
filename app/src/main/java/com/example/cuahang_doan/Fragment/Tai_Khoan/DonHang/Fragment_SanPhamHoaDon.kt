package com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang

import com.example.cuahang_doan.model.HoaDon
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Adapter.Adapter_HoaDon
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.DecimalFormat

class Fragment_SanPhamHoaDon(private val arrayList: List<HoaDon>?) : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var view1: View? = null
    private var txtgiasanphamhoadon: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.dondathang, container, false)
        anhxa()
        getdatahoadon()
        return view1
    }

    private fun getdatahoadon() {
        if (arrayList != null) {
            val decimalFormat = DecimalFormat("###,###,###")
            val adapter = Adapter_HoaDon(activity!!, R.layout.layout_hoadon, arrayList)
            recyclerView!!.adapter = adapter
            adapter.notifyDataSetChanged()
            var tong = 0
            for (i in arrayList.indices) {
                tong += arrayList[i].giaSanPham!!
            }
            txtgiasanphamhoadon!!.text = decimalFormat.format(tong.toLong()) + " Đồng"
        }
    }

    private fun anhxa() {
        txtgiasanphamhoadon = view1!!.findViewById(R.id.txtgiasanphamhoadon)
        recyclerView = view1!!.findViewById(R.id.recyclerviewhoadon)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView?.setLayoutManager(linearLayoutManager)
        recyclerView?.setHasFixedSize(true)
    }
}