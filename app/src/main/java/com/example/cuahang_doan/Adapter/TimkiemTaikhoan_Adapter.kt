package com.example.cuahang_doan.Adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.View
import com.example.cuahang_doan.Activity.Hoadon
import android.widget.Toast
import com.example.cuahang_doan.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class TimkiemTaikhoan_Adapter(
    private val arrayList: ArrayList<User?>?,
    private val layout: Int,
    private val context: Context
) : RecyclerView.Adapter<TimkiemTaikhoan_Adapter.Viewhodler>() {
    private var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhodler {
        view = View.inflate(context, layout, null)
        return Viewhodler(view!!)
    }

    override fun onBindViewHolder(holder: Viewhodler, position: Int) {
        holder.txtsdttaikhoan.text = arrayList?.get(position)?.phoneNumBer ?: ""
        holder.txttentaikhoan.text = arrayList?.get(position)?.userName ?: ""
        holder.txtdiachitaikhoan.text = arrayList?.get(position)?.adress ?: ""
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?:0
    }

    inner class Viewhodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttentaikhoan: TextView
        val txtdiachitaikhoan: TextView
        val txtsdttaikhoan: TextView

        init {
            txtdiachitaikhoan = itemView.findViewById(R.id.txtdiachitaikhoan)
            txttentaikhoan = itemView.findViewById(R.id.txttentaikhoan)
            txtsdttaikhoan = itemView.findViewById(R.id.txtsdttaikhoan)
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    if (arrayList?.get(position)?.id == MainActivity.sharedPreferences!!.getInt(
                            "iduser",
                            0
                        )
                    ) {
                        val calendar = Calendar.getInstance()
                        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                        val data = simpleDateFormat.format(calendar.time)
                        val dataService = APIServices.getService()
                        val callback = dataService.posdondathang(
                            MainActivity.sharedPreferences!!.getInt("iduser", 0).toString() + "",
                            "Chờ Xét Duyệt",
                            data,
                            arrayList[position]?.userName,
                            arrayList[position]?.adress,
                            arrayList[position]?.email,
                            arrayList[position]?.phoneNumBer
                        )
                        callback.enqueue(object : Callback<String?> {
                            override fun onResponse(
                                call: Call<String?>,
                                response: Response<String?>
                            ) {
                                Log.d("AAA", "post chitiet donhang: $response")
                                if (response.isSuccessful) {
                                    context.startActivity(
                                        Intent(
                                            context.applicationContext,
                                            Hoadon::class.java
                                        )
                                    )
                                    Handler().postDelayed(Runnable {
                                        MainActivity.mainTablayout!!.getTabAt(
                                            0
                                        )!!.select()
                                    }, 100)
                                }
                            }

                            override fun onFailure(call: Call<String?>, t: Throwable) {}
                        })
                    } else {
                        Toast.makeText(
                            context,
                            "Đây không phải là tài khoản của bạn",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }
}