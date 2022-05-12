package com.example.cuahang_doan.Adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.example.cuahang_doan.R
import com.squareup.picasso.Picasso
import android.widget.TextView
import android.widget.RatingBar
import com.example.cuahang_doan.model.Nhanxetcuaban
import java.util.ArrayList

class Adapter_Danhanxet(
    private val context: Context,
    private val layout: Int,
    private val arrayList: ArrayList<Nhanxetcuaban?>?
) : RecyclerView.Adapter<Adapter_Danhanxet.Viewhodler>() {
    private var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhodler {
        view = View.inflate(context, R.layout.layout_danhanxet, null)
        return Viewhodler(view!!)
    }

    override fun onBindViewHolder(holder: Viewhodler, position: Int) {
        val nhanxetcuaban = arrayList?.get(position)
        if (nhanxetcuaban != null) {
            if (nhanxetcuaban.motsao!! > 0) {
                holder.rattingdanhanxet.rating = 1f
            }
        }
        if (nhanxetcuaban != null) {
            if (nhanxetcuaban.haisao!! > 0) {
                holder.rattingdanhanxet.rating = 2f
            }
        }
        if (nhanxetcuaban != null) {
            if (nhanxetcuaban.basao!! > 0) {
                holder.rattingdanhanxet.rating = 3f
            }
        }
        if (nhanxetcuaban != null) {
            if (nhanxetcuaban.bonsao!! > 0) {
                holder.rattingdanhanxet.rating = 4f
            }
        }
        if (nhanxetcuaban != null) {
            if (nhanxetcuaban.namsao!! > 0) {
                holder.rattingdanhanxet.rating = 5f
            }
        }
        if (nhanxetcuaban != null) {
            holder.txttensanpham_nhanxet.text = nhanxetcuaban.tenSanPham
        }
        if (nhanxetcuaban != null) {
            holder.txtnhanxet.text = nhanxetcuaban.comMent
        }
        if (nhanxetcuaban != null) {
            Picasso.with(context).load(nhanxetcuaban.hinhSanPham).into(holder.imgnhanxet)
        }
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?:0
    }

    inner class Viewhodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgnhanxet: ImageView
        val txttensanpham_nhanxet: TextView
        val txtnhanxet: TextView
        val rattingdanhanxet: RatingBar

        init {
            imgnhanxet = itemView.findViewById(R.id.imgnhanxet)
            txtnhanxet = itemView.findViewById(R.id.txtnhanxet)
            txttensanpham_nhanxet = itemView.findViewById(R.id.txttensanpham_nhanxet)
            rattingdanhanxet = itemView.findViewById(R.id.rattingdanhanxet)
        }
    }
}