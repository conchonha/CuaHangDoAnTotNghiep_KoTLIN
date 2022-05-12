package com.example.cuahang_doan.Activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.graphics.Bitmap
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import com.squareup.picasso.Picasso
import com.example.cuahang_doan.Services.APIServices
import android.graphics.BitmapFactory
import com.example.cuahang_doan.Services.DataService
import android.widget.Toast
import android.app.Activity
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.model.TinTuc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class UpdateTinTuc : AppCompatActivity() {
    private var tinTuc: TinTuc? = null
    private var edt_UpdateContent: EditText? = null
    private var edt_NameNewsUpdate: EditText? = null
    private var img_PictureContentUpdate: ImageView? = null
    private var img_AddPictureUpdate: ImageView? = null
    private var btn_UpdateNews: Button? = null
    private val REQUEST_CODE_IMAGEVIEW = 123
    private var bitmap: Bitmap? = null
    private var toolbar_UpdateNews: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_tin_tuc)
        anhxa()
        actionbar()
        val intent = intent
        if (intent != null) {
            if (intent.hasExtra("News")) {
                tinTuc = intent.getSerializableExtra("News") as TinTuc
                add_ContentNewsUpdate(tinTuc)
            }
        }
    }

    private fun actionbar() {
        setSupportActionBar(toolbar_UpdateNews)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar_UpdateNews!!.setNavigationIcon(R.drawable.back)
        toolbar_UpdateNews!!.setNavigationOnClickListener { finish() }
    }

    private fun add_ContentNewsUpdate(tinTuc: TinTuc?) {
//        Picasso.with(getApplicationContext()).load("http://www.gibc.com.vn/public/img/default/no-image-icon.jpg").into(img_PictureContentUpdate);
        Picasso.with(applicationContext).load("https://www.sanadig.com/img/add.png")
            .into(img_AddPictureUpdate)
        edt_NameNewsUpdate!!.setText(tinTuc!!.tenbaiviet)
        edt_UpdateContent!!.setText(tinTuc.noidung)
        try {
            var urlLink: String? = "https://i1.taimienphi.vn/Images/thumb.gif"
            urlLink = if (tinTuc.hinhbaiviet.endsWith("news.jpg")) {
                APIServices.urlhinh + tinTuc.hinhbaiviet
            } else {
                tinTuc.hinhbaiviet
            }
            val url = URL(urlLink)
            val inputStream = url.openConnection().getInputStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
            img_PictureContentUpdate!!.setImageBitmap(bitmap)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        img_AddPictureUpdate!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGEVIEW)
        }
        btn_UpdateNews!!.setOnClickListener {
            val string_Picture = imagetostring(bitmap)
            if (string_Picture != null) {
                val dataService = APIServices.getService()
                val callback = dataService.update_NewsAdmin(
                    string_Picture, edt_UpdateContent!!.text.toString(),
                    edt_NameNewsUpdate!!.text.toString(), tinTuc.id.toString() + ""
                )
                callback.enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        Log.d("AAA", "update_NewsAdmin: $response")
                        if (response.isSuccessful) {
                            Toast.makeText(this@UpdateTinTuc, "thanh cong", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(
                                Intent(
                                    this@UpdateTinTuc,
                                    com.example.cuahang_doan.Activity.TinTuc::class.java
                                )
                            )
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {}
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_IMAGEVIEW && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                bitmap = BitmapFactory.decodeStream(inputStream)
                img_PictureContentUpdate!!.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun imagetostring(bitmap: Bitmap?): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val hinhanh = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(hinhanh, Base64.DEFAULT)
    }

    private fun anhxa() {
        toolbar_UpdateNews = findViewById(R.id.toolbar_UpdateNews)
        edt_UpdateContent = findViewById(R.id.edt_UpdateContent)
        edt_NameNewsUpdate = findViewById(R.id.edt_NameNewsUpdate)
        img_PictureContentUpdate = findViewById(R.id.img_PictureContentUpdate)
        img_AddPictureUpdate = findViewById(R.id.img_AddPictureUpdate)
        btn_UpdateNews = findViewById(R.id.btn_UpdateNews)
    }
}