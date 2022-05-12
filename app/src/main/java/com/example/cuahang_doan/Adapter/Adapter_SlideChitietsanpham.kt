package com.example.cuahang_doan.Adapter

import android.content.Context
import android.util.Log
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import android.view.ViewGroup
import android.widget.ImageView
import com.example.cuahang_doan.R
import com.squareup.picasso.Picasso
import com.example.cuahang_doan.Services.APIServices

class Adapter_SlideChitietsanpham(
    private val arrayhinh: Array<String>,
    private val context: Context
) : PagerAdapter() {
    private var view: View? = null
    private var imgslidechitietsanpham: ImageView? = null
    override fun getCount(): Int {
        return arrayhinh.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        view = View.inflate(context, R.layout.layout_slidechitietsanpham, null)
        imgslidechitietsanpham = view?.findViewById(R.id.imgslidechitietsanpham)
        if (arrayhinh[position].endsWith("news.jpg")) {
            Picasso.with(context).load(APIServices.urlhinhsanpham + arrayhinh[position])
                .into(imgslidechitietsanpham)
            Log.d("AAA", "hinhsp: " + APIServices.urlhinhsanpham + arrayhinh[position])
        } else {
            Picasso.with(context).load(arrayhinh[position]).into(imgslidechitietsanpham)
        }
        container.addView(view)
        return view!!
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}