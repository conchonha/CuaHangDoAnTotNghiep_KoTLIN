package com.example.cuahang_doan.Adapter

import android.content.Context
import android.view.View
import com.example.cuahang_doan.model.HoaDon
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.example.cuahang_doan.R
import java.text.DecimalFormat

class Adapter_HoaDon(
    private val context: Context,
    private val layout: Int,
    private val arrayList: List<HoaDon>
) : RecyclerView.Adapter<Adapter_HoaDon.Viewhodler>() {
    private var view: View? = null
    private var stt = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhodler {
        view = View.inflate(context, layout, null)
        return Viewhodler(view!!)
    }

    override fun onBindViewHolder(holder: Viewhodler, position: Int) {
        val decimalFormat = DecimalFormat("###,###,###")
        val hoadon = arrayList[position]
        holder.stt.text = stt.toString() + ""
        stt++
        val dongia = hoadon.giaSanPham?.div(hoadon.soLuongSanPham!!)
        if (dongia != null) {
            holder.txtdongia.text = decimalFormat.format(dongia.toLong()) + ""
        }
        holder.txtsoluong.text = hoadon.soLuongSanPham.toString() + ""
        holder.txttensanpham1.text = hoadon.tenSanPham
        holder.txtthanhtien.text = decimalFormat.format(hoadon.giaSanPham) + ""
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class Viewhodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stt: TextView
        val txttensanpham1: TextView
        val txtsoluong: TextView
        val txtdongia: TextView
        val txtthanhtien: TextView

        init {
            stt = itemView.findViewById(R.id.stt)
            txttensanpham1 = itemView.findViewById(R.id.txttensanpham1)
            txtsoluong = itemView.findViewById(R.id.txtsoluong)
            txtdongia = itemView.findViewById(R.id.txtdongia)
            txtthanhtien = itemView.findViewById(R.id.txtthanhtien)
        }
    }
}