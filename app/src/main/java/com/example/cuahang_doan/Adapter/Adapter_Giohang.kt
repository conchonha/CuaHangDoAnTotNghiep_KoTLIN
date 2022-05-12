package com.example.cuahang_doan.Adapter

import android.app.AlertDialog
import com.example.cuahang_doan.Activity.MainActivity
import com.example.cuahang_doan.model.GioHang
import android.view.ViewGroup
import com.example.cuahang_doan.R
import com.squareup.picasso.Picasso
import com.example.cuahang_doan.Services.APIServices
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.*
import com.example.cuahang_doan.Services.DataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.DecimalFormat
import java.util.ArrayList

class Adapter_Giohang     //    private ImageView imggiohang11;
//    private TextView txttensanphamgiohang,txtgiasanphamgiohang,txtsoluonggiohang;
//    private Button btngiam,btntang;
//    private ImageButton imgbttonxoa;
    (private val context: MainActivity, private val arrayList: ArrayList<GioHang>) : BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(i: Int): Any {
        return arrayList[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    inner class Viewhodler {
        var imggiohang11: ImageView? = null
        var txttensanphamgiohang: TextView? = null
        var txtgiasanphamgiohang: TextView? = null
        var txtsoluonggiohang: TextView? = null
        var btngiam: Button? = null
        var btntang: Button? = null
        var imgbttonxoa: ImageButton? = null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        var view = view
        var viewhodler: Viewhodler? = null
        if (view == null) {
            viewhodler = Viewhodler()
            view = View.inflate(context, R.layout.layout_giohang, null)
            viewhodler!!.imggiohang11 = view.findViewById(R.id.imggiohang11)
            viewhodler.txttensanphamgiohang = view.findViewById(R.id.txttensanphamgiohang)
            viewhodler.txtgiasanphamgiohang = view.findViewById(R.id.txtgiasanphamgiohang)
            viewhodler.txtsoluonggiohang = view.findViewById(R.id.txtsoluonggiohang)
            viewhodler.btngiam = view.findViewById(R.id.btngiam)
            viewhodler.btntang = view.findViewById(R.id.btntang)
            viewhodler.imgbttonxoa = view.findViewById(R.id.imgbttonxoa)
            viewhodler.btngiam = view.findViewById(R.id.btngiam)
            view.tag = viewhodler
        } else {
            viewhodler = view.tag as Viewhodler
        }
        val gioHang = arrayList[i]
        val decimalFormat = DecimalFormat("###,###,###")
        try {
            if (gioHang.hinh?.endsWith("news.jpg") == true) {
                Picasso.with(context).load(APIServices.urlhinhsanpham + gioHang.hinh).into(
                    viewhodler!!.imggiohang11
                )
            } else {
                Picasso.with(context).load(gioHang.hinh).into(viewhodler!!.imggiohang11)
            }
        } catch (e1: Exception) {
            Log.d("AAA", "loi picaso: $e1")
        }
        viewhodler!!.txttensanphamgiohang!!.text = gioHang.tenSanpham
        viewhodler.txtgiasanphamgiohang!!.text = decimalFormat.format(gioHang.thanhTien) + ""
        viewhodler.txtsoluonggiohang!!.text = gioHang.soLuong.toString() + ""
        viewhodler.btngiam!!.setOnClickListener {
            gioHang.thanhTien?.let { it1 ->
                gioHang.idSanpham?.let { it2 ->
                    gioHang.soLuong?.let { it3 ->
                        updategiohanggiam(
                            it3,
                            it1,
                            it2
                        )
                    }
                }
            }
        }
        viewhodler.btntang!!.setOnClickListener {
            gioHang.soLuong?.let { it1 ->
                gioHang.thanhTien?.let { it2 ->
                    gioHang.idSanpham?.let { it3 ->
                        updategiohangTang(
                            it1,
                            it2,
                            it3
                        )
                    }
                }
            }
        }
        viewhodler.imgbttonxoa!!.setOnClickListener { gioHang.idSanpham?.let { it1 ->
            deletesanpham(
                it1
            )
        } }
        return view!!
    }

    private fun deletesanpham(idSanpham: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Bạn có muốn xóa sản phẩm?")
        builder.setNegativeButton("không") { dialogInterface, i -> }
        builder.setPositiveButton("Có") { dialogInterface, i ->
            val dataService = APIServices.getService()
            val callback = dataService.Deletesanpham(idSanpham)
            callback.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    Log.d("AAA", "delete sanpham giohang: $response")
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                        context.reloaddulieu()
                    } else {
                        Toast.makeText(
                            context,
                            "Không thành công vui lòng thử lại sau",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Log.d("AAA", "delete sanpham giohang: $t")
                }
            })
        }
        builder.show()
    }

    private fun updategiohangTang(soLuong: Int, thanhTien: Int, idSanpham: String) {
        if (soLuong < 10) {
            val giacu = thanhTien / soLuong
            val soluongmoi = soLuong + 1
            val giamoi = thanhTien + giacu
            Log.d("AAA", "slcu: $soLuong")
            Log.d("AAA", "giacu: $giacu")
            Log.d("AAA", "slmoi: $soluongmoi")
            Log.d("AAA", "gia moi: $giamoi")
            postgiohang(soluongmoi, giamoi, idSanpham)
        } else {
            Toast.makeText(context, "Số lượng sp đã đạt mức Cao nhất", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updategiohanggiam(soLuong: Int, thanhTien: Int, idSanpham: String) {
        if (soLuong > 1) {
            val giacu = thanhTien / soLuong
            val soluongmoi = soLuong - 1
            val giamoi = thanhTien - giacu
            Log.d("AAA", "slcu: $soLuong")
            Log.d("AAA", "giacu: $giacu")
            Log.d("AAA", "slmoi: $soluongmoi")
            Log.d("AAA", "gia moi: $giamoi")
            postgiohang(soluongmoi, giamoi, idSanpham)
        } else {
            Toast.makeText(context, "Số lượng sp đã đạt mức thấp nhất", Toast.LENGTH_SHORT).show()
        }
    }

    private fun postgiohang(soluongmoi: Int, giamoi: Int, idSanpham: String) {
        val dataService = APIServices.getService()
        val callback = dataService.updategiohang(idSanpham, soluongmoi, giamoi)
        callback.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                Log.d("AAA", "Cập nhật giam giohang: $response")
                if (response.isSuccessful) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    context.reloaddulieu()
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.d("AAA", "erro giam: $t")
            }
        })
    }
}