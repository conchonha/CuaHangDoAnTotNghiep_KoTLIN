package com.example.cuahang_doan.Adapter

import android.content.Context
import com.example.cuahang_doan.model.GetdataSanphammoinhat
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.APIServices
import com.squareup.picasso.Picasso
import com.example.cuahang_doan.Activity.MainActivity
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import com.example.cuahang_doan.Activity.Chitietsanpham
import com.makeramen.roundedimageview.RoundedImageView
import android.widget.TextView
import android.widget.RatingBar
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Adapter_SanPham(
    private val context: Context,
    private val layout: Int,
    private val arrayList: ArrayList<GetdataSanphammoinhat?>?
) : RecyclerView.Adapter<Adapter_SanPham.Viewhodler>() {
    private var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhodler {
        view = View.inflate(context, layout, null)
        return Viewhodler(view!!)
    }

    override fun onBindViewHolder(holder: Viewhodler, position: Int) {
        val sanpham = arrayList?.get(position)
        val calendar = Calendar.getInstance()
        val simpleDateFormat = DecimalFormat("###,###,###")
        val format = SimpleDateFormat("dd-MM-yyyy")
        if (sanpham?.giamGia!! > 0 && sanpham?.ngayKhuyenMai != "") {
            var ngaykhuyenmai: Date? = null
            var ngayhientai: Date? = null
            try {
                ngaykhuyenmai = format.parse(sanpham?.ngayKhuyenMai + "")
                ngayhientai =
                    format.parse(calendar[Calendar.DATE].toString() + "-" + calendar[Calendar.MONTH] + "-" + calendar[Calendar.YEAR])
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            if (ngaykhuyenmai!!.compareTo(ngayhientai) > 0) {
                Log.d("AAA", "ngay$ngayhientai")
                holder.txtsale.text = "Giảm Giá: " + sanpham.giamGia + "%"
                holder.txtgiasp.paintFlags =
                    holder.txtgiasp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.txtgiasp.setTextColor(context.resources.getColor(R.color.maugiasanpham))
                holder.txtgiasp.text = simpleDateFormat.format(sanpham.gia) + ""
                val giagiam = (100 - sanpham.giamGia).toFloat() / 100
                val giaspsaukhuyenmai = giagiam * sanpham.gia
                Log.d("AAA", giaspsaukhuyenmai.toString() + "")
                holder.txtgiaspsaukhuyenmai.setText(
                    simpleDateFormat.format(
                        giaspsaukhuyenmai.toLong()
                    ) + "Đ"
                )
                holder.txtgiaspsaukhuyenmai.setTextColor(Color.RED)
                holder.txtngaygiamgia.text = "Hạn: " + sanpham.ngayKhuyenMai
            }
        } else {
            holder.txtgiaspsaukhuyenmai.text = ""
            holder.txtgiasp.text = simpleDateFormat.format(sanpham.gia) + "Đ"
            holder.txtgiasp.setTextColor(Color.RED)
        }
        holder.txttensanpham.text = sanpham.tenSanPham
        if (sanpham.hinhAnhSanPham.endsWith("news.jpg")) {
            Log.d("AAA", "hinh: " + APIServices.urlhinhsanpham + sanpham.hinhAnhSanPham)
            Picasso.with(context).load(APIServices.urlhinhsanpham + sanpham.hinhAnhSanPham)
                .into(holder.roundedImageView)
        } else {
            Picasso.with(context).load(sanpham.hinhAnhSanPham).into(holder.roundedImageView)
        }
        if (MainActivity.sharedPreferences!!.getString("admin", "") == "") {
            holder.txttensanpham.setOnClickListener {
                val intent = Intent(context, Chitietsanpham::class.java)
                arrayList?.get(position)?.let { it1 -> intent.putExtra("id", it1.id) }
                context.startActivity(intent)
            }
        }
        if (sanpham.loai != "") {
            holder.txtloai.text = sanpham.loai
        }
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?: 0
    }

    inner class Viewhodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var  roundedImageView: RoundedImageView
        lateinit var txtsale: TextView
        lateinit var txtgiasp: TextView
        lateinit var txttensanpham: TextView
        lateinit var txtloai: TextView
        lateinit var txtthongso: TextView
        lateinit var txtgiaspsaukhuyenmai: TextView
        lateinit var txtngaygiamgia: TextView
//        private val ratingBar: RatingBar

        init {
            try {
                txtngaygiamgia = itemView.findViewById(R.id.txtngaygiamgia)
                roundedImageView = itemView.findViewById(R.id.roundimageview)
                txttensanpham = itemView.findViewById(R.id.txttensanpham)
                txtgiasp = itemView.findViewById(R.id.txtgiasp)
                txtsale = itemView.findViewById(R.id.txtsale)
//            ratingBar = itemView.findViewById(R.id.ratingBar)
                txtgiaspsaukhuyenmai = itemView.findViewById(R.id.txtgiaspsaukhuyenmai)
                txtloai = itemView.findViewById(R.id.txtloai)
                txtthongso = itemView.findViewById(R.id.txtthongso)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}