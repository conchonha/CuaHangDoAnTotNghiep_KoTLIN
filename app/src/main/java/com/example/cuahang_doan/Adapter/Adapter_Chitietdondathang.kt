package com.example.cuahang_doan.Adapter

import android.content.Context
import android.view.View
import com.example.cuahang_doan.model.Chitietdondathang
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.example.cuahang_doan.Services.APIServices
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import com.example.cuahang_doan.R
import java.text.DecimalFormat
import java.util.ArrayList

class Adapter_Chitietdondathang(
    private val context: Context,
    private val layout: Int,
    private val arrayList: ArrayList<Chitietdondathang>
) : RecyclerView.Adapter<Adapter_Chitietdondathang.Viewhodler>() {
    private var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhodler {
        view = View.inflate(context, layout, null)
        return Viewhodler(view!!)
    }

    override fun onBindViewHolder(holder: Viewhodler, position: Int) {
        val chitietdondathang = arrayList[position]
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtgiachitietdondathang.text =
            decimalFormat.format(chitietdondathang.giaSanPham) + "Đ"
        holder.txtsoluongchitietdondathang.text = chitietdondathang.soLuongSanPham.toString() + ""
        holder.txttensanphamchitietdondathang.text = chitietdondathang.tenSanPham
        if (chitietdondathang.hinhSanPham!!.endsWith("news.jpg")) {
            Picasso.with(context).load(APIServices.urlhinhsanpham + chitietdondathang.hinhSanPham)
                .into(holder.roundimageviewchitietdondathang)
        } else {
            Picasso.with(context).load(chitietdondathang.hinhSanPham)
                .into(holder.roundimageviewchitietdondathang)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class Viewhodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttensanphamchitietdondathang: TextView
        val txtgiachitietdondathang: TextView
        val txtsoluongchitietdondathang: TextView
        val roundimageviewchitietdondathang: RoundedImageView

        init {
            txttensanphamchitietdondathang =
                itemView.findViewById(R.id.txttensanphamchitietdondathang)
            txtgiachitietdondathang = itemView.findViewById(R.id.txtgiachitietdondathang)
            txtsoluongchitietdondathang = itemView.findViewById(R.id.txtsoluongchitietdondathang)
            roundimageviewchitietdondathang =
                itemView.findViewById(R.id.roundimageviewchitietdondathang)
        }
    }
}