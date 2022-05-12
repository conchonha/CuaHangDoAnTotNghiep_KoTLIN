package com.example.cuahang_doan.Adapter.admin

import android.util.Log
import android.view.View
import com.example.cuahang_doan.Activity.admin.QuanLytaikhoan
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.example.cuahang_doan.Services.APIServices
import android.widget.Toast
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import com.example.cuahang_doan.R
import com.example.cuahang_doan.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Adapter_Quan_ly_tai_khoan(
    private val context: QuanLytaikhoan,
    private val layout: Int,
    private val arrayList: ArrayList<User?>?
) : RecyclerView.Adapter<Adapter_Quan_ly_tai_khoan.Viewhodler>() {
    private var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhodler {
        view = View.inflate(context, layout, null)
        return Viewhodler(view!!)
    }

    override fun onBindViewHolder(holder: Viewhodler, position: Int) {
        val taikhoan = arrayList?.get(position)
        //holder.textViewMat_khau.setText();
        if (taikhoan != null) {
            holder.textViewDia_chi.setText(taikhoan.adress)
            holder.textViewEmail?.text = taikhoan.email
            holder.textViewSDT.text = taikhoan.phoneNumBer
            holder.textViewTen_tai_khoan.text = taikhoan.userName
            Picasso.with(context).load(taikhoan.hinh).into(holder.roundImageViewQuan_ly_tai_khoan)

            if (taikhoan.loai == 0) {
                holder.txtloaitaikhoan.text = "Khách Hàng"
            } else if (taikhoan.loai == 1) {
                holder.txtloaitaikhoan.text = "Nhân Viên"
            } else {
                holder.txtloaitaikhoan.text = "Admin"
            }
        }

        holder.imageViewDeleteTai_khoan_admin.setOnClickListener {
            val dataService = APIServices.getService()
            val callback = dataService.deleteAdminTai_khoan(
                arrayList?.get(position)?.id.toString() + ""
            )
            callback.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    Log.d("AAA", "deleteAdminTai_khoan: $response")
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show()
                        context.reload()
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {}
            })
        }
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?: 0
    }

    inner class Viewhodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewMat_khau: TextView
        val textViewSDT: TextView
        val textViewEmail: TextView
        val textViewDia_chi: TextView
        val textViewTen_tai_khoan: TextView
        val txtloaitaikhoan: TextView
        val roundImageViewQuan_ly_tai_khoan: RoundedImageView
        val imageViewDeleteTai_khoan_admin: ImageView

        init {
            txtloaitaikhoan = itemView.findViewById(R.id.txtloaitaikhoan)
            textViewMat_khau = itemView.findViewById(R.id.textViewMat_khau)
            textViewSDT = itemView.findViewById(R.id.textViewSDT)
            textViewEmail = itemView.findViewById(R.id.textViewEmail)
            textViewDia_chi = itemView.findViewById(R.id.textViewDia_chi)
            textViewTen_tai_khoan = itemView.findViewById(R.id.textViewTen_tai_khoan)
            imageViewDeleteTai_khoan_admin =
                itemView.findViewById(R.id.imageViewDeleteTai_khoan_admin)
            roundImageViewQuan_ly_tai_khoan =
                itemView.findViewById(R.id.roundImageViewQuan_ly_tai_khoan)
        }
    }
}