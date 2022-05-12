package com.example.cuahang_doan.Fragment.Tai_Khoan.TaiKhoan

import android.widget.TextView
import android.widget.RelativeLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import com.squareup.picasso.Picasso
import android.widget.Toast
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.cuahang_doan.Services.APIServices
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.cuahang_doan.Activity.*
import com.example.cuahang_doan.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.ArrayList

class Fragmnet_taikhoan : Fragment() {
    private var view1: View? = null
    private var toolbartaikhoan: Toolbar? = null
    private var txttenuser: TextView? = null
    private var relivelayoutquanlytaikhoan: RelativeLayout? = null
    private var relivelayoutdonhangcuatoi: RelativeLayout? = null
    private var relivelayoutnhanxetcuatoi: RelativeLayout? = null
    private var relivelayoutdonhangdoitra: RelativeLayout? = null
    private var relivelayoutdangxuat: RelativeLayout? = null
    private var imghinhusser: ImageView? = null
    private var imguploadphotouser: ImageView? = null
    private val Requestcode = 123
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_taikhoan, container, false)
        anhxa()
        if (MainActivity.sharedPreferences!!.getInt("iduser", 0) == 0) {
            startActivity(Intent(activity, Login::class.java))
        } else {
            sukienclickview()
        }
        return view1
    }

    private fun sethinhusser() {
        if (MainActivity.sharedPreferences!!.getString("hinh", "") != "") {
            Picasso.with(activity).load(MainActivity.sharedPreferences!!.getString("hinh", ""))
                .into(imghinhusser)
            Log.d("AAA", "hinhuser: " + MainActivity.sharedPreferences!!.getString("hinh", ""))
        }
    }

    private fun sukienclickview() {
        sethinhusser()
        imguploadphotouser!!.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, Requestcode)
        }
        relivelayoutdangxuat!!.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    Login::class.java
                )
            )
        }
        relivelayoutdonhangdoitra!!.setOnClickListener {
            Toast.makeText(
                activity,
                "Bạn Chưa Có Đơn Hàng Đổi Trả Nào",
                Toast.LENGTH_SHORT
            ).show()
        }
        relivelayoutnhanxetcuatoi!!.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    Nhanxetcuaban::class.java
                )
            )
        }
        relivelayoutdonhangcuatoi!!.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    DonHangCuaBan::class.java
                )
            )
        }
        relivelayoutquanlytaikhoan!!.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    QuanLyTaiKhoan::class.java
                )
            )
        }
        txttenuser!!.text =
            MainActivity.sharedPreferences!!.getString("username", "chưa có tài khoản")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == Requestcode && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            try {
                val inputStream = activity!!.contentResolver.openInputStream(
                    uri!!
                )
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imghinhusser!!.setImageBitmap(bitmap)
                val bytehinh = imagetostring(bitmap)
                updatehinh(bytehinh)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updatehinh(bytehinh: String) {
        val dataService = APIServices.getService()
        val callback = dataService.updatephotouser(
            bytehinh,
            MainActivity.sharedPreferences!!.getInt("iduser", 0).toString() + ""
        )
        callback.enqueue(object : Callback<List<User?>?> {
            override fun onResponse(call: Call<List<User?>?>, response: Response<List<User?>?>) {
                Log.d("AAA", "Updatehinhuser: $response")
                if (response.isSuccessful) {
                    val arrayList = response.body() as ArrayList<User?>?
                    MainActivity.editor!!.putString(
                        "hinh", APIServices.urlhinh + MainActivity.sharedPreferences!!
                            .getInt("iduser", 0) + ".jpg"
                    )
                    MainActivity.editor!!.commit()
                    activity!!.finish()
                    startActivity(activity!!.intent)
                    handler = Handler()
                    runnable = Runnable { MainActivity.mainTablayout!!.getTabAt(2)!!.select() }
                    handler!!.postDelayed(runnable, 100)
                }
            }

            override fun onFailure(call: Call<List<User?>?>, t: Throwable) {}
        })
    }

    private fun imagetostring(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val hinhanh = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(hinhanh, Base64.DEFAULT)
    }

    private fun anhxa() {
        imguploadphotouser = view1!!.findViewById(R.id.imguploadphotouser)
        imghinhusser = view1!!.findViewById(R.id.imghinhusser)
        relivelayoutdangxuat = view1!!.findViewById(R.id.relivelayoutdangxuat)
        relivelayoutdonhangdoitra = view1!!.findViewById(R.id.relivelayoutdonhangdoitra)
        relivelayoutnhanxetcuatoi = view1!!.findViewById(R.id.relivelayoutnhanxetcuatoi)
        relivelayoutdonhangcuatoi = view1!!.findViewById(R.id.relivelayoutdonhangcuatoi)
        relivelayoutquanlytaikhoan = view1!!.findViewById(R.id.relivelayoutquanlytaikhoan)
        txttenuser = view1!!.findViewById(R.id.txttenuser)
        toolbartaikhoan = view1!!.findViewById(R.id.toolbartaikhoan)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbartaikhoan)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbartaikhoan?.setNavigationIcon(R.drawable.back)
        toolbartaikhoan?.setNavigationOnClickListener(View.OnClickListener {
            activity!!.finish()
            startActivity(activity!!.intent)
            activity!!.overridePendingTransition(0, 0)
        })
    }
}