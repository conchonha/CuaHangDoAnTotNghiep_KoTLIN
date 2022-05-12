package com.example.cuahang_doan.Fragment.Tai_Khoan.DonHang

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.RelativeLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import android.content.Intent
import android.util.Log
import android.view.View
import com.example.cuahang_doan.Activity.Hoadon
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Activity.DonHangCuaBan
import com.example.cuahang_doan.model.Chitietdondathang
import com.example.cuahang_doan.Adapter.Adapter_Chitietdondathang
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class DialogFragment_Chitietdonhang(val id: Int?, private val trinhtrang: String) :
    DialogFragment() {
    private var view1: View? = null
    private var txtiddonhang: TextView? = null
    private var txtdiachinhanhan: TextView? = null
    private var txtsodienthoa: TextView? = null
    private var txttrinhtrangdonhang: TextView? = null
    private var txtthongtin: TextView? = null
    private var recyclerviewchitietdonhang: RecyclerView? = null
    private var relativelupdatechitietdonhang: RelativeLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.dialogfragment_chitietdonhang, container, false)
        anhxa()
        if (MainActivity.sharedPreferences!!.getString("admin", "") == "") {
            relativelupdatechitietdonhang!!.visibility = View.GONE
        } else {
            relativelupdatechitietdonhang!!.visibility = View.VISIBLE
        }
        if (id != null) {
            getdatachitietdondathang(id)
        }
        txtthongtin!!.setOnClickListener {
            if (id != 0) {
                val intent = Intent(view1!!.getContext(), Hoadon::class.java)
                intent.putExtra("idhoadon", id.toString() + "")
                startActivity(intent)
            }
        }
        return view1
    }

    private fun updatedonhang(id: Int, trinhtrang: String) {
        if (MainActivity.sharedPreferences!!.getString("admin", "") == "") {
            if (MainActivity.sharedPreferences!!.getString("nhanvien", "") != "") {
                relativelupdatechitietdonhang!!.visibility = View.VISIBLE
            } else {
                relativelupdatechitietdonhang!!.visibility = View.GONE
            }
        } else {
            if (trinhtrang == "Đã Giao Hàng" || trinhtrang == "Đã Hủy" || trinhtrang == "Đang Vận Chuyển") {
                relativelupdatechitietdonhang!!.visibility = View.GONE
            } else {
                relativelupdatechitietdonhang!!.visibility = View.VISIBLE
            }
        }
        relativelupdatechitietdonhang!!.setOnClickListener {
            Toast.makeText(activity, "co clic", Toast.LENGTH_SHORT).show()
            if (trinhtrang == "Chờ Xét Duyệt") {
                val trinhtrangmoi = "Đang Vận Chuyển"
                updatechoxetduyetadmin(id.toString() + "", trinhtrangmoi)
            }
            if (trinhtrang == "Đang Vận Chuyển") {
                val trinhtrangmoi = "Đã Giao Hàng"
                updatechoxetduyetadmin(id.toString() + "", trinhtrangmoi)
            }
        }
    }

    private fun updatechoxetduyetadmin(iddondathang: String, trinhtrang: String) {
        val dataService = APIServices.getService()
        val callback = dataService.updatedondathangadmin(iddondathang, trinhtrang)
        callback.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                Log.d("AAA", "Update DangVanChuyen: $response")
                Log.d("AAA", iddondathang + "")
                Log.d("AAA", trinhtrang + "")
                if (response.isSuccessful) {
                    Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show()
                    if (MainActivity.sharedPreferences!!.getString("nhanvien", "") == "") {
                        startActivity(Intent(context, DonHangCuaBan::class.java))
                        val prev = fragmentManager!!.findFragmentByTag("chitietdonhang")
                        if (prev != null) {
                            val df = prev as DialogFragment
                            df.dismiss()
                        }
                    } else {
                        activity!!.finish()
                        startActivity(activity!!.intent)
                        val prev = fragmentManager!!.findFragmentByTag("chitietdonhang")
                        if (prev != null) {
                            val df = prev as DialogFragment
                            df.dismiss()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {}
        })
    }

    private fun getdatachitietdondathang(id: Int) {
        if (id != 0) {
            val dataService = APIServices.getService()
            val callback = dataService.getdatachitietdonhang(id.toString() + "")
            callback.enqueue(object : Callback<List<Chitietdondathang>> {
                override fun onResponse(
                    call: Call<List<Chitietdondathang>>,
                    response: Response<List<Chitietdondathang>>
                ) {
                    Log.d("AAA", "Getdata Chitietdonhang$response")
                    if (response.isSuccessful) {
                        val arrayList = response.body() as ArrayList<Chitietdondathang>
                        if (arrayList.size != 0) {
                            txtiddonhang!!.text = "Đơn Hàng: " + arrayList[0].idDonDatHang + ""
                            txtdiachinhanhan!!.text = arrayList[0].diaChi
                            txtsodienthoa!!.text = arrayList[0].soDienThoai + ""
                            txttrinhtrangdonhang!!.text = trinhtrang
                            val adapter = activity?.let {
                                Adapter_Chitietdondathang(
                                    it, R.layout.layout_chitietdonhang, arrayList
                                )
                            }
                            recyclerviewchitietdonhang!!.adapter = adapter
                            adapter?.notifyDataSetChanged()
                            updatedonhang(id, txttrinhtrangdonhang!!.text.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Chitietdondathang>>, t: Throwable) {}
            })
        }
    }

    private fun anhxa() {
        txtthongtin = view1!!.findViewById(R.id.txtthongtin)
        relativelupdatechitietdonhang = view1!!.findViewById(R.id.relativelupdatechitietdonhang)
        txtiddonhang = view1!!.findViewById(R.id.txtiddonhang)
        txtdiachinhanhan = view1!!.findViewById(R.id.txtdiachinhanhan)
        txtsodienthoa = view1!!.findViewById(R.id.txtsodienthoa)
        txttrinhtrangdonhang = view1!!.findViewById(R.id.txttrinhtrangdonhang)
        recyclerviewchitietdonhang = view1!!.findViewById(R.id.recyclerviewchitietdonhang)
        recyclerviewchitietdonhang?.setHasFixedSize(true)
        recyclerviewchitietdonhang?.setLayoutManager(GridLayoutManager(activity, 1))
    }
}