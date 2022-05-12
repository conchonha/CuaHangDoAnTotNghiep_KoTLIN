package com.example.cuahang_doan.Fragment

import com.example.cuahang_doan.Activity.Chitietsanpham
import com.example.cuahang_doan.model.Danhgia
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import com.example.cuahang_doan.Activity.Login
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Adapter.Comment_Adapter
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class Fragment_Danhgiasanpham : Fragment() {
    private var view1: View? = null
    private val chitietsanpham: Chitietsanpham? = null
    private var arrayDanhgia: ArrayList<Danhgia>? = ArrayList()
    private var txt5sao: TextView? = null
    private var txt4sao: TextView? = null
    private var txt3sao: TextView? = null
    private var txt2sao: TextView? = null
    private var txt1sao: TextView? = null
    private var txtsoluongdanhgia: TextView? = null
    private var ratingdanhgiacuabanla: RatingBar? = null
    private var edtcommentt: EditText? = null
    private var btnguicomment: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_danhgiasanpham, container, false)
        danhgiasanpham()
        val bundle = arguments
        if (bundle != null) {
            val msp = bundle.getString("masp")
            Log.d("AAA", "Mã san pham fragment danhgia$msp")
            if (msp != "0") {
                getdatadanhgia(msp)
                btnguicomment!!.setOnClickListener { danhgiacuaban(msp) }
            }
        }
        return view1
    }

    private fun danhgiacuaban(msp: String?) {
        Toast.makeText(view1!!.context, "co cloc", Toast.LENGTH_SHORT).show()
        if (MainActivity.sharedPreferences!!.getString("username", "") == "" &&
            MainActivity.sharedPreferences!!.getInt("iduser", 0) == 0
        ) {
            val intent = Intent(activity, Login::class.java)
            intent.putExtra("phandanhgia", msp)
            startActivity(intent)
            activity!!.finish()
        } else {
            var commentt = "ok"
            commentt = edtcommentt!!.text.toString()
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val calendar = Calendar.getInstance()
            val ngay = simpleDateFormat.format(calendar.time)
            // Toast.makeText(getActivity(), MainActivity.sharedPreferences.getString("username",comment), Toast.LENGTH_SHORT).show();
            var namsao = 0
            var bonsao = 0
            var basao = 0
            var haisao = 0
            var motsao = 0
            if (ratingdanhgiacuabanla!!.rating > 0) {
                if (ratingdanhgiacuabanla!!.rating == 5f) {
                    namsao = 1
                }
                if (ratingdanhgiacuabanla!!.rating == 4f) {
                    bonsao = 1
                }
                if (ratingdanhgiacuabanla!!.rating == 3f) {
                    basao = 1
                }
                if (ratingdanhgiacuabanla!!.rating == 2f) {
                    haisao = 1
                }
                if (ratingdanhgiacuabanla!!.rating == 1f) {
                    motsao = 1
                }
                Log.d(
                    "AAA",
                    "da co tai khoan: " + MainActivity.sharedPreferences!!.getString("username", "")
                )
                Log.d("AAA", "ngay$ngay")
                Log.d("AAA", "5sao$namsao")
                Log.d("AAA", "4sao$bonsao")
                Log.d("AAA", "3sao$basao")
                Log.d("AAA", "2sao$haisao")
                Log.d("AAA", "1sao$motsao")
                Log.d("AAA", "msp$msp")
                Log.d("AAA", "comment$commentt")
                val username = MainActivity.sharedPreferences!!.getString("username", "")
                Log.d("AAA", "username$username")
                postbai(username, msp, ngay, namsao, bonsao, basao, haisao, motsao, commentt)
            }
        }
    }

    private fun postbai(
        username: String?,
        msp: String?,
        ngay: String,
        namsao: Int,
        bonsao: Int,
        basao: Int,
        haisao: Int,
        motsao: Int,
        comment: String
    ) {
        val dataService = APIServices.getService()
        val callback = dataService.postDanhgia(
            MainActivity.sharedPreferences!!.getString("username", ""),
            msp, ngay, namsao, bonsao, basao, haisao, motsao, comment
        )
        callback.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                Log.d("AAA", "Danhgiacuaban$response")
                if (response.isSuccessful) {
                    val mess = response.body()
                    Log.d("AAA", "bien mess$mess")
                    if (mess == "Faid") {
                        Toast.makeText(
                            activity,
                            "Không Thành công - bạn đã đánh giá rùi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    if (mess == "sussces") {
                        Toast.makeText(
                            activity,
                            "Đánh Giá Của Bạn Đã Được Gửi Đi",
                            Toast.LENGTH_SHORT
                        ).show()
                        activity!!.finish()
                        startActivity(activity!!.intent)
                    }
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {}
        })
    }

    private fun danhgiasanpham() {
        btnguicomment = view1!!.findViewById(R.id.btnguicomment)
        ratingdanhgiacuabanla = view1!!.findViewById(R.id.ratingdanhgiacuabanla)
        edtcommentt = view1!!.findViewById(R.id.edtcommentt)
        txt5sao = view1!!.findViewById(R.id.txt5sao)
        txt4sao = view1!!.findViewById(R.id.txt4sao)
        txt3sao = view1!!.findViewById(R.id.txt3sao)
        txt2sao = view1!!.findViewById(R.id.txt2sao)
        txt1sao = view1!!.findViewById(R.id.txt1sao)
        txtsoluongdanhgia = view1!!.findViewById(R.id.txtsoluongdanhgia)
    }

    private fun getdatadanhgia(msp: String?) {
        if (msp != "") {
            val dataService = APIServices.getService()
            val callback = dataService.getdataDanhgia(msp + "")
            callback.enqueue(object : Callback<List<Danhgia>?> {
                override fun onResponse(
                    call: Call<List<Danhgia>?>,
                    response: Response<List<Danhgia>?>
                ) {
                    Log.d("AAA", "getdataDanhgia$response")
                    if (response != null) {
                        var namsao = 0
                        var bonsao = 0
                        var basao = 0
                        var haisao = 0
                        var motsao = 0
                        arrayDanhgia = response.body() as ArrayList<Danhgia>?
                        if (arrayDanhgia != null) {
                            for (i in arrayDanhgia!!.indices) {
                                namsao += arrayDanhgia!![i].get5sao() ?: 0
                                bonsao += arrayDanhgia!![i].get4sao() ?: 0
                                basao += arrayDanhgia!![i].get3sao() ?: 0
                                haisao += arrayDanhgia!![i].get2sao() ?: 0
                                motsao += arrayDanhgia!![i].get1sao() ?: 0
                            }
                            val soluong = namsao + bonsao + basao + haisao + motsao
                            txtsoluongdanhgia!!.text = "$soluong lượt đánh giá"
                            txt5sao!!.text = "$namsao Đánh Giá"
                            txt4sao!!.text = "$bonsao Đánh Giá"
                            txt3sao!!.text = "$basao Đánh Giá"
                            txt2sao!!.text = "$haisao Đánh Giá"
                            txt1sao!!.text = "$motsao Đánh Giá"
                            val listView = view1!!.findViewById<ListView>(R.id.listviewcomment)
                            val comment_adapter = Comment_Adapter(
                                activity!!,
                                R.layout.layout_dongcomment,
                                arrayDanhgia!!
                            )
                            comment_adapter.notifyDataSetChanged()
                            listView.adapter = comment_adapter
                            setListViewHeightBasedOnChildren(listView)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Danhgia>?>, t: Throwable) {
                    Log.d("AAA", "Ero getdata danhgia san pham$t")
                }
            })
        }
    }

    fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter
            ?: // pre-condition
            return
        var totalHeight = listView.paddingTop + listView.paddingBottom
        val desiredWidth =
            View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.AT_MOST)
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            if (listItem != null) {
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += listItem.measuredHeight
            }
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
        listView.requestLayout()
    }
}