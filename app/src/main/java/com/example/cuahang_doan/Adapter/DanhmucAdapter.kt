package com.example.cuahang_doan.Adapter

import android.content.Context
import android.view.View
import com.example.cuahang_doan.model.DanhMuc
import android.widget.BaseAdapter
import android.widget.TextView
import android.view.ViewGroup
import android.widget.ImageView
import com.example.cuahang_doan.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

class DanhmucAdapter(
    private val context: Context,
    private val layout: Int,
    private val arrayList: ArrayList<DanhMuc>
) : BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(i: Int): Any {
        return arrayList[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    private inner class Viewhodeler {
        var imageviewDanhmucLayout: ImageView? = null
        var txtTitleDanhmuc: TextView? = null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        var viewhodeler: Viewhodeler? = null
        var view1  = view
        if (view1 == null) {
            viewhodeler = Viewhodeler()
            view1 = View.inflate(context, layout, null)
            viewhodeler!!.imageviewDanhmucLayout = view1.findViewById(R.id.imageviewDanhmucLayout)
            viewhodeler.txtTitleDanhmuc = view1.findViewById(R.id.txtTitleDanhmuc)
            view1.tag = viewhodeler
        } else {
            viewhodeler = view1.tag as Viewhodeler
        }
        Picasso.with(context).load(arrayList[i].hinhicon).into(viewhodeler!!.imageviewDanhmucLayout)
        viewhodeler.txtTitleDanhmuc!!.text = arrayList[i].ten
        return view1!!
    }
}