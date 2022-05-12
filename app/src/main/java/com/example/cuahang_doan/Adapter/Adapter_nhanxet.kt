package com.example.cuahang_doan.Adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.example.cuahang_doan.Activity.Chitietsanpham
import android.widget.TextView
import com.example.cuahang_doan.R
import com.example.cuahang_doan.model.Nhanxetcuaban
import java.util.ArrayList

class Adapter_nhanxet(
    private val layout: Int,
    private val context: Context,
    private val arrayList: ArrayList<Nhanxetcuaban?>?
) : RecyclerView.Adapter<Adapter_nhanxet.Viewhodler>() {
    private var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhodler {
        view = View.inflate(context, layout, null)
        return Viewhodler(view!!)
    }

    override fun onBindViewHolder(holder: Viewhodler, position: Int) {
        Picasso.with(context).load(arrayList?.get(position)?.hinhSanPham ?: "").into(holder.imgchuanhanxet)
        holder.btnnhanxet.setOnClickListener {
            val intent = Intent(context, Chitietsanpham::class.java)
            intent.putExtra("id", arrayList?.get(position)?.id ?: 0)
            context.startActivity(intent)
        }
        holder.txtngaygiaohang.text = "Đã giao hàng ngày: " + (arrayList?.get(position)?.ngayDanhGia ?: "")
        holder.txttensanpham_nhanxet.text = arrayList?.get(position)?.tenSanPham ?: ""
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?: 0
    }

    inner class Viewhodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgchuanhanxet: ImageView
        val txtngaygiaohang: TextView
        val txttensanpham_nhanxet: TextView
        val btnnhanxet: TextView

        init {
            imgchuanhanxet = itemView.findViewById(R.id.imgchuanhanxet)
            txtngaygiaohang = itemView.findViewById(R.id.txtngaygiaohang)
            txttensanpham_nhanxet = itemView.findViewById(R.id.txttensanpham_nhanxet)
            btnnhanxet = itemView.findViewById(R.id.btnnhanxet1)
        }
    }
}