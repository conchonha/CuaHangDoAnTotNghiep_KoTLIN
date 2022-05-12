package com.example.cuahang_doan.Activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.graphics.Bitmap
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import android.widget.Toast
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.model.TinTuc
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class Inssert_TinTuc : AppCompatActivity() {
    private val tinTuc: TinTuc? = null
    private var edt_InsertContent: EditText? = null
    private var edt_NameNewsInsert: EditText? = null
    private var img_PictureInsert: ImageView? = null
    private var img_AddInsert: ImageView? = null
    private var btn_Insert: Button? = null
    private val REQUEST_CODE_IMAGEVIEW = 123
    private var bitmap: Bitmap? = null
    private var toolbar_InsertNews: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inssert__tin_tuc)
        anhxa()
        add_Imageview()
        insert_OnclickNews()
        Actionbar()
    }

    private fun Actionbar() {
        setSupportActionBar(toolbar_InsertNews)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar_InsertNews!!.setNavigationIcon(R.drawable.back)
        toolbar_InsertNews!!.setNavigationOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    com.example.cuahang_doan.Activity.TinTuc::class.java
                )
            )
            finish()
        }
    }

    private fun insert_OnclickNews() {
        btn_Insert!!.setOnClickListener { insert_News() }
    }

    private fun insert_News() {
        var arrayPicture = ""
        if (bitmap != null) {
            arrayPicture = toArrayString(bitmap!!)
        }
        if (arrayPicture == "") {
            Toast.makeText(this, "Vui lòng chọn hình ảnh sản phẩm", Toast.LENGTH_SHORT).show()
        } else if (edt_InsertContent!!.text.toString() == "") {
            Toast.makeText(this, "Không để trống nội dung bài viết", Toast.LENGTH_SHORT).show()
        } else if (edt_NameNewsInsert!!.text.equals("")) {
            Toast.makeText(this, "Không để trống tiêu đề", Toast.LENGTH_SHORT).show()
        } else {
            val dataService = APIServices.getService()
            val callback = dataService.insert_NewsAdmin(
                arrayPicture, edt_InsertContent!!.text.toString(),
                edt_NameNewsInsert!!.text.toString()
            )
            callback.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    Log.d("AAA", "insert_News: $response")
                    if (response.isSuccessful) {
                        Toast.makeText(this@Inssert_TinTuc, "thanh cong", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(
                                applicationContext,
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

    private fun add_Imageview() {
        img_AddInsert!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGEVIEW)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_IMAGEVIEW && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                bitmap = BitmapFactory.decodeStream(inputStream)
                img_PictureInsert!!.setImageBitmap(bitmap)
                img_PictureInsert!!.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun toArrayString(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val array = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(array, Base64.DEFAULT)
    }

    private fun anhxa() {
        toolbar_InsertNews = findViewById(R.id.toolbar_InsertNews)
        edt_InsertContent = findViewById(R.id.edt_InsertContent)
        edt_NameNewsInsert = findViewById(R.id.edt_NameNewsInsert)
        img_PictureInsert = findViewById(R.id.img_PictureInsert)
        img_AddInsert = findViewById(R.id.img_AddInsert)
        btn_Insert = findViewById(R.id.btn_Insert)
        Picasso.with(applicationContext)
            .load("http://icons.iconarchive.com/icons/dryicons/aesthetica-2/128/image-add-icon.png")
            .into(img_AddInsert)
        Picasso.with(applicationContext).load("https://i1.taimienphi.vn/Images/thumb.gif")
            .into(img_PictureInsert)
    }
}