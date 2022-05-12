package com.example.cuahang_doan.Fragment.Tim_Kiem

import androidx.recyclerview.widget.RecyclerView
import com.example.cuahang_doan.Adapter.Adapter_Sanphammoinhat
import com.example.cuahang_doan.model.GetdataSanphammoinhat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.APIServices
import android.text.TextWatcher
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Fragment_timkiem : Fragment() {
    private var view1: View? = null
    private var imgback: ImageView? = null
    private var imgseach: ImageView? = null
    private var txttitlefragmenttimkiem: TextView? = null
    private var edtfragmenttimkiem: EditText? = null
    private var recyclerviewTimkimsp: RecyclerView? = null
    private var btnxemthem: Button? = null
    private var adpter: Adapter_Sanphammoinhat? = null
    private var dem = 1
    private var arrayList: ArrayList<GetdataSanphammoinhat>? = null
    private var relativeLayouttollbar: RelativeLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_timkiem, container, false)
        anhxa()
        init()
        getdataspPhantrang(0)
        sukienxemthem()
        return view1
    }

    private fun sukienxemthem() {
        btnxemthem!!.setOnClickListener {
            dem++
            val sl = dem * 7
            getdataspPhantrang(sl)
            adpter!!.notifyDataSetChanged()
        }
    }

    private fun getdataspPhantrang(soluong: Int) {
        val dataService = APIServices.getService()
        val callback = dataService.getdataPhantrang(soluong.toString() + "")
        callback.enqueue(object : Callback<List<GetdataSanphammoinhat>> {
            override fun onResponse(
                call: Call<List<GetdataSanphammoinhat>>,
                response: Response<List<GetdataSanphammoinhat>>
            ) {
                Log.d("AAA", "Getdataphantrang: $response")
                if (response.isSuccessful) {
                    val array = response.body() as? ArrayList<GetdataSanphammoinhat?>
                    if(array == null) return
                    for (i in array.indices) {
                        arrayList?.add(
                            GetdataSanphammoinhat(
                                array[i]?.id ?: 0,
                                array[i]?.tenSanPham ?: "",
                                array[i]?.hinhAnhSanPham ?: "",
                                array[i]?.thongSoKyThuat ?: "",
                                array[i]?.gia ?: 0,
                                array[i]?.ngayKhuyenMai ?: "",
                                array[i]?.giamGia ?: 0,
                                array[i]?.danhGiaSao ?: "",
                                array[i]?.loai ?: ""
                            )
                        )
                    }
                    setdatarecyclerview(arrayList)
                }
            }

            override fun onFailure(call: Call<List<GetdataSanphammoinhat>>, t: Throwable) {
                Log.d("AAA", "kocodulieuGetdata phantrang: $t")
                btnxemthem!!.visibility = View.GONE
            }
        })
    }

    private fun init() {
        relativeLayouttollbar!!.setOnClickListener {
            edtfragmenttimkiem!!.visibility = View.GONE
            txttitlefragmenttimkiem!!.visibility = View.VISIBLE
        }
        imgseach!!.setOnClickListener {
            edtfragmenttimkiem!!.visibility = View.VISIBLE
            txttitlefragmenttimkiem!!.visibility = View.GONE
        }
        imgback!!.setOnClickListener {
            activity!!.finish()
            startActivity(activity!!.intent)
        }
        edtfragmenttimkiem!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val search = edtfragmenttimkiem!!.text.toString()
                val dataService = APIServices.getService()
                val callback = dataService.getdataTimkiemsanpham(search)
                callback.enqueue(object : Callback<List<GetdataSanphammoinhat>?> {
                    override fun onResponse(
                        call: Call<List<GetdataSanphammoinhat>?>,
                        response: Response<List<GetdataSanphammoinhat>?>
                    ) {
                        Log.d("AAA", "Tim kim sp: $response")
                        if (response.isSuccessful) {
                            val arrayList = response.body() as ArrayList<GetdataSanphammoinhat>?
                            setdatarecyclerview(arrayList)
                        }
                    }

                    override fun onFailure(call: Call<List<GetdataSanphammoinhat>?>, t: Throwable) {
                        Log.d("AAA", "Loi timkim Sp: $t")
                        btnxemthem!!.visibility = View.GONE
                    }
                })
            }
        })
    }

    private fun anhxa() {
        if (arrayList == null) {
            arrayList = ArrayList()
        } else {
        }
        relativeLayouttollbar = view1!!.findViewById(R.id.ttt)
        btnxemthem = view1!!.findViewById(R.id.btnxemthem)
        recyclerviewTimkimsp = view1!!.findViewById(R.id.recyclerviewTimkimsp)
        imgback = view1!!.findViewById(R.id.imgback)
        imgseach = view1!!.findViewById(R.id.imgseach)
        txttitlefragmenttimkiem = view1!!.findViewById(R.id.txttitlefragmenttimkiem)
        edtfragmenttimkiem = view1!!.findViewById(R.id.edtfragmenttimkiem)
    }

    fun setdatarecyclerview(arrayList: ArrayList<GetdataSanphammoinhat>?) {
        recyclerviewTimkimsp!!.setHasFixedSize(true)
        recyclerviewTimkimsp!!.layoutManager = GridLayoutManager(context, 2)
        adpter = Adapter_Sanphammoinhat(context!!, R.layout.layout_linhkienlaptop, arrayList!!)
        recyclerviewTimkimsp!!.adapter = adpter
        adpter!!.notifyDataSetChanged()
    }
}