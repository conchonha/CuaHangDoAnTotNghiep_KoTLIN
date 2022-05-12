package com.example.cuahang_doan.Fragment.Gio_Hang

import com.example.cuahang_doan.model.GioHang
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Activity.MainActivity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Adapter.Adapter_Giohang
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.util.ArrayList

class Fragment_giohang : Fragment() {
    private var toolbargiohang: Toolbar? = null
    private var listgiohang: ListView? = null
    private var view1: View? = null
    private var arrayList = ArrayList<GioHang>()
    private var txtgiasanphamgiohang: TextView? = null
    private var txttrinhtranggiohang: TextView? = null
    private var floattingactionbutondathang: FloatingActionButton? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_giohang, container, false)
        anhxa()
        actionbar()
        datagiohang
        thanhtoangiohang()
        return view1
    }

    fun reloaddulieu() {
        activity!!.finish()
        startActivity(activity!!.intent)
        activity!!.overridePendingTransition(0, 0)
        Handler().postDelayed(
            { MainActivity.mainTablayout!!.getTabAt(1)!!.select() }, 100
        )
    }

    private fun thanhtoangiohang() {
        floattingactionbutondathang!!.setOnClickListener {
            if (arrayList.size > 0) {
                val fragmentManager = fragmentManager
                val dialogFragment = DialogFragment_Thanhtoangiohang()
                dialogFragment.show(fragmentManager, "dialogkhachhangmoi")
            } else {
                Toast.makeText(activity, "Giỏ hàng trống", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun actionbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbargiohang)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbargiohang!!.setNavigationIcon(R.drawable.back)
        toolbargiohang!!.setNavigationOnClickListener {
            activity!!.finish()
            startActivity(activity!!.intent)
            activity!!.overridePendingTransition(0, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity!!.menuInflater.inflate(R.menu.reload, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reload) {
            reloaddulieu()
        }
        return super.onOptionsItemSelected(item)
    }

    private val datagiohang: Unit
        private get() {
            txtgiasanphamgiohang!!.text = ""
            val dataService = APIServices.getService()
            val callback = dataService.getdataGiohang()
            callback.enqueue(object : Callback<List<GioHang>> {
                override fun onResponse(
                    call: Call<List<GioHang>>,
                    response: Response<List<GioHang>>
                ) {
                    Log.d("AAA", "get data giohang: $response")
                    if (response.isSuccessful) {
                        val mainActivity = activity as MainActivity?
                        arrayList = response.body() as ArrayList<GioHang>
                        val adapter = Adapter_Giohang(mainActivity!!, arrayList)
                        adapter.notifyDataSetChanged()
                        listgiohang!!.adapter = adapter
                        adapter.notifyDataSetChanged()
                        setListViewHeightBasedOnChildren(listgiohang)
                        var tong = 0
                        for (i in arrayList.indices) {
                            tong += arrayList[i].thanhTien!!
                        }
                        val decimalFormat = DecimalFormat("###,###,###")
                        txtgiasanphamgiohang!!.text =
                            "Thành Tiền: " + decimalFormat.format(tong.toLong()) + ""
                        txttrinhtranggiohang!!.visibility = View.GONE
                    } else {
                        txttrinhtranggiohang!!.visibility = View.VISIBLE
                        txttrinhtranggiohang!!.text = "Giỏ Hàng Trống"
                    }
                }

                override fun onFailure(call: Call<List<GioHang>>, t: Throwable) {}
            })
        }

    private fun anhxa() {
        txttrinhtranggiohang = view1!!.findViewById(R.id.txttrinhtranggiohang)
        txtgiasanphamgiohang = view1!!.findViewById(R.id.txtgiasanphamgiohang)
        listgiohang = view1!!.findViewById(R.id.listgiohang)
        toolbargiohang = view1!!.findViewById(R.id.toolbargiohang)
        floattingactionbutondathang = view1!!.findViewById(R.id.floattingactionbutondathang)
    }

    fun setListViewHeightBasedOnChildren(listView: ListView?) {
        val listAdapter = listView!!.adapter
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