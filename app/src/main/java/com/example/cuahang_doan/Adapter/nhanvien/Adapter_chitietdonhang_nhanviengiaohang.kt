package com.example.cuahang_doan.Adapter.nhanvien

import com.example.cuahang_doan.model.DonDatHang
import com.example.cuahang_doan.Activity.nhanvien.NhanVien
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import android.widget.Toast
import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang.DialogFragment_Chitietdonhang
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Adapter_chitietdonhang_nhanviengiaohang(
    private val arrayList: ArrayList<DonDatHang>,
    private val context: NhanVien,
    private val layout: Int
) : RecyclerView.Adapter<Adapter_chitietdonhang_nhanviengiaohang.Viewhodler>() {
    private var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhodler {
        view = View.inflate(context, layout, null)
        return Viewhodler(view!!)
    }

    override fun onBindViewHolder(holder: Viewhodler, position: Int) {
        val donDatHang = arrayList[position]
        holder.trinhtrang.text = donDatHang.trinhTrang
        holder.ngaydat.text = donDatHang.ngayDat
        holder.iddonhang.text = donDatHang.idDonHang.toString() + ""
        holder.imgdeletedonhangcuaban.visibility = View.VISIBLE
        holder.imgdeletedonhangcuaban.setOnClickListener {
            if (donDatHang.trinhTrang == "Chờ Xét Duyệt") {
                val dataService = APIServices.getService()
                val callback = dataService.UpdateHuydondathang(donDatHang.idDonHang.toString() + "")
                callback.enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        Log.d("AAA", "Upfate huy don dat hang; $response")
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Da Huy Don thanh Cong", Toast.LENGTH_SHORT)
                                .show()
                            context.startActivity((context as Activity).intent)
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {}
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class Viewhodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iddonhang: TextView
        val ngaydat: TextView
        val trinhtrang: TextView
        val imgdeletedonhangcuaban: ImageView

        init {
            iddonhang = itemView.findViewById(R.id.iddonhang)
            imgdeletedonhangcuaban = itemView.findViewById(R.id.imgdeletedonhangcuaban)
            ngaydat = itemView.findViewById(R.id.ngaydat)
            trinhtrang = itemView.findViewById(R.id.trinhtrang)
            itemView.setOnClickListener {
                val fragmentManager = context.supportFragmentManager
                val dialogFragment_chitietdonhang = arrayList[position].trinhTrang?.let { it1 ->
                    DialogFragment_Chitietdonhang(
                        arrayList[position].idDonHang, it1
                    )
                }
                dialogFragment_chitietdonhang?.show(fragmentManager, "chitietdonhang")
            }
        }
    }
}