package com.example.cuahang_doan.Activity.admin

import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import org.json.JSONObject
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*

class ThemSanPham : AppCompatActivity() {
    private var img_AddImageviewsProducthai: ImageView? = null
    private var img_AddImageviewsProductmot: ImageView? = null
    private var img_PictureProduct: ImageView? = null
    private var linnerlayouthinhmota: LinearLayout? = null
    private val REQUEST_CODE_IMAGEVIEWMO_TA = 123
    private val REQUEST_CODE_IMAGEVIEW_PRODUCT = 456
    private var hinhanhsanpham = ""
    private var idloai = 0
    private var HangSanXuat = ""
    private var txt_InsertTypeProduct: TextView? = null
    private var txt_PromotionDay: TextView? = null
    private var txt_TheFirm: TextView? = null
    private var edt_InsertDescription: EditText? = null
    private var edt_InsertProduct: EditText? = null
    private var edt_InsertNameProduct: EditText? = null
    private var txt_InsertPriceProduct: EditText? = null
    private var txt_NumberOfProduct: EditText? = null
    private var txt_PromotionPriceProduct: EditText? = null
    private var btn_InsertProduct: Button? = null
    private var jsonArray: JSONArray? = null
    private var toolbar_UpdateProduct: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_them_san_pham)
        anhxa()
        addhinhmota()
        addhinhanhsanpham()
        loaisanpham()
        ngaykhuyenmai()
        hangsanxuat()
        onclickinssert()
    }

    private fun onclickinssert() {
        btn_InsertProduct!!.setOnClickListener {
            if (hinhanhsanpham != "" && jsonArray!!.length() > 0) {
                InsertProduct()
            } else {
                Toast.makeText(
                    this@ThemSanPham,
                    "Vui lòng điền đầy đủ thông tin ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun hangsanxuat() {
        txt_TheFirm!!.setOnClickListener {
            val popupMenu = PopupMenu(applicationContext, txt_TheFirm)
            popupMenu.menuInflater.inflate(R.menu.menuhangsanxuat, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.macbook -> {
                        HangSanXuat = "Macbook"
                        txt_TheFirm!!.text = "Macbook"
                    }
                    R.id.hp -> {
                        HangSanXuat = "HP"
                        txt_TheFirm!!.text = "HP"
                    }
                    R.id.asus -> {
                        HangSanXuat = "Asus"
                        txt_TheFirm!!.text = "Asus"
                    }
                    R.id.dell -> {
                        HangSanXuat = "Dell"
                        txt_TheFirm!!.text = "Dell"
                    }
                    R.id.lenovo -> {
                        HangSanXuat = "Lenovo"
                        txt_TheFirm!!.text = "Lenovo"
                    }
                    R.id.acer -> {
                        HangSanXuat = "Acer"
                        txt_TheFirm!!.text = "Acer"
                    }
                    R.id.masstel -> {
                        HangSanXuat = "Masstel"
                        txt_TheFirm!!.text = "Masstel"
                    }
                    R.id.samsung -> {
                        HangSanXuat = "Samsung"
                        txt_TheFirm!!.text = "Samsung"
                    }
                    R.id.sony -> {
                        HangSanXuat = "Sony"
                        txt_TheFirm!!.text = "Sony"
                        HangSanXuat = "Toshiba"
                        txt_TheFirm!!.text = "Toshiba"
                    }
                    R.id.tosiba -> {
                        HangSanXuat = "Toshiba"
                        txt_TheFirm!!.text = "Toshiba"
                    }
                }
                false
            }
        }
    }

    private fun InsertProduct() {
        if (edt_InsertDescription!!.text.toString() == "" || edt_InsertProduct!!.text.toString() == "" || edt_InsertNameProduct!!.text.toString() == "" || txt_InsertPriceProduct!!.text.toString() == "") {
            Toast.makeText(this, "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show()
        } else {
            val calendar = Calendar.getInstance()
            val ngay = calendar[Calendar.DATE]
            val thang = calendar[Calendar.MONTH]
            val nam = calendar[Calendar.YEAR]
            var ngaykhuyenmai = ""
            var khuyenmai = ""
            val ngayhientai = "$ngay-$thang-$nam"
            val mota = edt_InsertDescription!!.text.toString()
            val thongsokythuat = edt_InsertProduct!!.text.toString()
            val tensp = edt_InsertNameProduct!!.text.toString()
            val gia = txt_InsertPriceProduct!!.text.toString()
            val soluong = txt_NumberOfProduct!!.text.toString()
            if (txt_PromotionDay!!.text.toString() != "") {
                if (txt_PromotionPriceProduct!!.text.toString() != "") {
                    ngaykhuyenmai = txt_PromotionDay!!.text.toString()
                    khuyenmai = txt_PromotionPriceProduct!!.text.toString()
                }
            }
            val dataService = APIServices.getService()
            val callback = dataService.insert_ProductAdmin(
                mota, thongsokythuat, tensp, gia,
                soluong, ngaykhuyenmai, khuyenmai, idloai.toString() + "", ngayhientai, HangSanXuat,
                hinhanhsanpham, jsonArray
            )
            callback.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("AAA", "insertProductAdmin: $response")
                    if (response.isSuccessful) {
                        Toast.makeText(this@ThemSanPham, "Thành Công", Toast.LENGTH_SHORT).show()
                        Log.d("AAA", "Insert Product: " + response.body())
                        finish()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {}
            })
        }
    }

    private fun ngaykhuyenmai() {
        txt_PromotionDay!!.setOnClickListener {
            val calendar = Calendar.getInstance()
            val nam = calendar[Calendar.YEAR]
            val thang = calendar[Calendar.MONTH]
            val ngay = calendar[Calendar.DATE]
            val datePickerDialog = DatePickerDialog(
                this@ThemSanPham,
                { view, year, month, dayOfMonth ->
                    txt_PromotionDay!!.text = dayOfMonth.toString() + "-" + month + 1 + "-" + year
                },
                nam,
                thang,
                ngay
            )
            datePickerDialog.show()
        }
    }

    private fun loaisanpham() {
        txt_InsertTypeProduct!!.setOnClickListener {
            val popupMenu = PopupMenu(this@ThemSanPham, txt_InsertTypeProduct)
            popupMenu.menuInflater.inflate(R.menu.menuluachonsanphamadmin, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.laptopmacbook) {
                    txt_InsertTypeProduct!!.text = "LapTop-Macbook"
                    idloai = 1
                }
                if (item.itemId == R.id.linhkien) {
                    idloai = 2
                    txt_InsertTypeProduct!!.text = "Linh Kiện LapTop"
                }
                if (item.itemId == R.id.tbluutrupk) {
                    txt_InsertTypeProduct!!.text = "Lưu Trữ - Phụ Kiện"
                    idloai = 3
                }
                if (item.itemId == R.id.tbnghenhin) {
                    txt_InsertTypeProduct!!.text = "TB Nghe - Nhìn"
                    idloai = 4
                }
                false
            }
            popupMenu.show()
        }
    }

    private fun addhinhanhsanpham() {
        img_AddImageviewsProductmot!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGEVIEW_PRODUCT)
        }
    }

    private fun addhinhmota() {
        img_AddImageviewsProducthai!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGEVIEWMO_TA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_IMAGEVIEWMO_TA && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            try {
                val imageView = ImageView(applicationContext)
                val layoutParams = LinearLayout.LayoutParams(200, 200)
                layoutParams.setMargins(10, 10, 10, 10)
                imageView.layoutParams = layoutParams
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                linnerlayouthinhmota!!.addView(imageView)
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageView.setImageBitmap(bitmap)
                if (jsonArray != null) {
                } else {
                    jsonArray = JSONArray()
                }
                val jsonObject = JSONObject()
                var stringhinh = ""
                stringhinh = Tobyarrrayhinh(bitmap)
                jsonObject.put("hinhmota", stringhinh)
                jsonArray!!.put(jsonObject)
                stringhinh = ""
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        if (requestCode == REQUEST_CODE_IMAGEVIEW_PRODUCT && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                img_PictureProduct!!.setImageBitmap(bitmap)
                hinhanhsanpham = Tobyarrrayhinh(bitmap)

                // Log.d("AAA","hinhanhsp: "+hinhanhsanpham);
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun anhxa() {
        btn_InsertProduct = findViewById(R.id.btn_InsertProduct)
        txt_TheFirm = findViewById(R.id.txt_TheFirm)
        txt_PromotionPriceProduct = findViewById(R.id.txt_PromotionPriceProduct)
        txt_NumberOfProduct = findViewById(R.id.txt_NumberOfProduct)
        txt_InsertPriceProduct = findViewById(R.id.txt_InsertPriceProduct)
        edt_InsertNameProduct = findViewById(R.id.edt_InsertNameProduct)
        edt_InsertProduct = findViewById(R.id.edt_InsertProduct)
        edt_InsertDescription = findViewById(R.id.edt_InsertDescription)
        txt_PromotionDay = findViewById(R.id.txt_PromotionDay)
        txt_InsertTypeProduct = findViewById(R.id.txt_InsertTypeProduct)
        img_PictureProduct = findViewById(R.id.img_PictureProduct)
        img_AddImageviewsProductmot = findViewById(R.id.img_AddImageviewsProductmot)
        linnerlayouthinhmota = findViewById(R.id.linnerlayouthinhmota)
        img_AddImageviewsProducthai = findViewById(R.id.img_AddImageviewsProducthai)
        toolbar_UpdateProduct = findViewById(R.id.toolbar_UpdateProduct)
        setSupportActionBar(toolbar_UpdateProduct)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar_UpdateProduct?.setNavigationIcon(R.drawable.back)
        toolbar_UpdateProduct?.setNavigationOnClickListener(View.OnClickListener { finish() })
    }

    private fun Tobyarrrayhinh(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val array = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(array, Base64.DEFAULT)
    }
}