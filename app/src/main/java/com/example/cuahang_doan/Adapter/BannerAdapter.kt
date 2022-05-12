package com.example.cuahang_doan.Adapter

import android.content.Context
import com.example.cuahang_doan.model.QuangCao
import androidx.viewpager.widget.PagerAdapter
import android.widget.TextView
import android.view.ViewGroup
import com.example.cuahang_doan.R
import com.squareup.picasso.Picasso
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.example.cuahang_doan.Activity.Chitietsanpham
import java.util.ArrayList

class BannerAdapter(private val context: Context, private val arrayList: ArrayList<QuangCao>) :
    PagerAdapter() {
    private var view: View? = null
    private var imageBackground: ImageView? = null
    private var imageHinhsanpham: ImageView? = null
    private var txtTitle: TextView? = null
    private var txtMota: TextView? = null
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        view = View.inflate(context, R.layout.layout_fragmentbanner, null)
        anhxa()
        Picasso.with(context).load(arrayList[position].hinhAnh).into(imageBackground)
        Picasso.with(context).load(arrayList[position].hinhAnhSanPham).into(imageHinhsanpham)
        txtTitle!!.text = arrayList[position].tenSanPham
        txtMota!!.text = arrayList[position].noiDung
        view?.setOnClickListener(View.OnClickListener {
            val intent = Intent(context.applicationContext, Chitietsanpham::class.java)
            intent.putExtra("id", arrayList[position].id)
            context.startActivity(intent)
        })
        container.addView(view)
        return view!!
    }

    private fun anhxa() {
        imageBackground = view!!.findViewById(R.id.imageBackground)
        imageHinhsanpham = view!!.findViewById(R.id.imageHinhsanpham)
        txtTitle = view!!.findViewById(R.id.txtTitle)
        txtMota = view!!.findViewById(R.id.txtMota)
    }
}