package com.example.cuahang_doan.Adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.cuahang_doan.Adapter.Adapter_Tintuc.Viewhdler
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.example.cuahang_doan.Services.APIServices
import android.view.animation.Animation
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import com.example.cuahang_doan.Services.DataService
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.PopupMenu
import com.example.cuahang_doan.Activity.admin.UpdateTinTuc
import android.widget.TextView
import com.example.cuahang_doan.Activity.ThongTinChiTietTinTuc
import com.example.cuahang_doan.Activity.TinTuc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.ArrayList

class Adapter_Tintuc(
    private val context: TinTuc,
    private val layout: Int,
    private val arrayList: ArrayList<com.example.cuahang_doan.model.TinTuc>
) : RecyclerView.Adapter<Viewhdler>() {
    private var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhdler {
        view = View.inflate(context, layout, null)
        return Viewhdler(view!!)
    }

    override fun onBindViewHolder(holder: Viewhdler, position: Int) {
        val tinTuc = arrayList[position]
        val simpleDateFormat = SimpleDateFormat("yyy,MM,dd")
        if (tinTuc.hinhbaiviet.endsWith("news.jpg")) {
            Picasso.with(context).load(APIServices.urlhinh + tinTuc.hinhbaiviet)
                .into(holder.imageBackground)
        } else {
            Picasso.with(context).load(tinTuc.hinhbaiviet).into(holder.imageBackground)
        }
        holder.txtnoidungbantin.text = tinTuc.noidung
        holder.txttitlebantin.text = tinTuc.tenbaiviet
        holder.txtngaytintuc.text = "Ngày Đăng: " + tinTuc.thoigian
        val animationUtils = AnimationUtils.loadAnimation(
            context, R.anim.xoay
        )
        holder.txtsothutu.animation = animationUtils
        if (arrayList.size == 1) {
            holder.txtsothutu.text = "0"
        } else {
            holder.txtsothutu.setText((position + 1).toString())
        }
        if (MainActivity.sharedPreferences!!.getString("admin", "") == "") {
            holder.imgMenuItemTin_tuc.visibility = View.GONE
        } else {
            holder.imgMenuItemTin_tuc.visibility = View.VISIBLE
        }
        holder.imgMenuItemTin_tuc.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.imgMenuItemTin_tuc)
            popupMenu.menuInflater.inflate(R.menu.menu_item_quanly_xoa_xua, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.xoa) {
                    val dataService = APIServices.getService()
                    val callback = dataService.delete_NewsItemAdmin(
                        arrayList[position].id.toString() + ""
                    )
                    callback.enqueue(object : Callback<String?> {
                        override fun onResponse(call: Call<String?>, response: Response<String?>) {
                            Log.d("AAA", "delete_NewsAdmin: $response")
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show()
                                context.reload()
                            }
                        }

                        override fun onFailure(call: Call<String?>, t: Throwable) {}
                    })
                }
                if (item.itemId == R.id.sua) {
                    val intent = Intent(context, UpdateTinTuc::class.java)
                    intent.putExtra("News", arrayList[position])
                    context.startActivity(intent)
                }
                false
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class Viewhdler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageBackground: ImageView
        val imgMenuItemTin_tuc: ImageView
        val txttitlebantin: TextView
        val txtnoidungbantin: TextView
        val txtngaytintuc: TextView
        val txtsothutu: TextView

        init {
            imageBackground = itemView.findViewById(R.id.imageBackground)
            txttitlebantin = itemView.findViewById(R.id.txttitlebantin)
            txtnoidungbantin = itemView.findViewById(R.id.txtnoidungbantin)
            txtngaytintuc = itemView.findViewById(R.id.txtngaytintuc)
            txtsothutu = itemView.findViewById(R.id.txtsothutu)
            imgMenuItemTin_tuc = itemView.findViewById(R.id.imgMenuItemTin_tuc)
            itemView.setOnClickListener {
                val intent = Intent(context, ThongTinChiTietTinTuc::class.java)
                intent.putExtra("tintuc", arrayList[position])
                context.startActivity(intent)
            }
        }
    }
}