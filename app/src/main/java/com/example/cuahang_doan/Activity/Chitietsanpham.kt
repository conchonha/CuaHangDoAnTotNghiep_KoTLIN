package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rd.PageIndicatorView
import android.os.Bundle
import com.example.cuahang_doan.R
import com.example.cuahang_doan.Fragment.Fragment_Danhgiasanpham
import android.content.Intent
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Adapter.Adapter_SlideChitietsanpham
import android.widget.Toast
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.cuahang_doan.Activity.MainActivity
import com.example.cuahang_doan.Activity.Login
import com.example.cuahang_doan.model.SanPham
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Chitietsanpham : AppCompatActivity() {
    private var ChitietsanphamViewpaget: ViewPager? = null
    private var txttensanphamchitiet: TextView? = null
    private var txtthongsokythuat: TextView? = null
    private var txtmotasanphamchitiet: TextView? = null
    private var txtgiasanphamgoc: TextView? = null
    private var txtgiasanphamsaukhigiam: TextView? = null
    private var txtngaydang: TextView? = null
    private var txtngaykhuyenmai: TextView? = null
    private var txttrinhtrang: TextView? = null
    var arrayList: ArrayList<SanPham>? = ArrayList()
    var id = 0
    private var collapsingtoolbar: CollapsingToolbarLayout? = null
    private var toolbarchitietsp: Toolbar? = null
    private var floattingactionbuton: FloatingActionButton? = null
    private var PageIndicatorview: PageIndicatorView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chitietsanpham)
        actionbar()
        anhxa()
        init()
    }

    private fun addFragment(masp: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = Fragment_Danhgiasanpham()
        val bundle = Bundle()
        bundle.putString("masp", masp)
        fragment.arguments = bundle
        fragmentTransaction.add(R.id.linnerlaout, fragment)
        fragmentTransaction.commit()
    }

    private fun actionbar() {
        toolbarchitietsp = findViewById(R.id.toolbarchitietsp)
        setSupportActionBar(toolbarchitietsp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbarchitietsp?.setNavigationIcon(R.drawable.back)
        toolbarchitietsp?.setNavigationOnClickListener(View.OnClickListener { finish() })
    }

    private fun init() {
        val intent = intent
        if (intent != null) {
            if (intent.hasExtra("id")) {
                id = intent.getIntExtra("id", 0)
                Log.d("AAA", id.toString() + "Ma san pham chi tiet san pham")
                getdatasanpham(id)
                addFragment(id.toString() + "")
            }
        }
    }

    private fun anhxa() {
        txttrinhtrang = findViewById(R.id.txttrinhtrang)
        txtngaykhuyenmai = findViewById(R.id.txtngaykhuyenmai)
        txtngaydang = findViewById(R.id.txtngaydang)
        txtgiasanphamsaukhigiam = findViewById(R.id.txtgiasanphamsaukhigiam)
        txtgiasanphamgoc = findViewById(R.id.txtgiasanphamgoc)
        floattingactionbuton = findViewById(R.id.floattingactionbuton)
        ChitietsanphamViewpaget = findViewById(R.id.ChitietsanphamViewpaget)
        txttensanphamchitiet = findViewById(R.id.txttensanphamchitiet)
        txtthongsokythuat = findViewById(R.id.txtthongsokythuat)
        txtmotasanphamchitiet = findViewById(R.id.txtmotasanphamchitiet)
        collapsingtoolbar = findViewById(R.id.collapsingtoolbar)
        //Expanded set màu mở rộng
        collapsingtoolbar?.setExpandedTitleColor(resources.getColor(R.color.colorwhile))
        //set màu thu hẹp
        collapsingtoolbar?.setCollapsedTitleTextColor(Color.WHITE)
        PageIndicatorview = findViewById(R.id.PageIndicatorview)
    }

    private fun getdatasanpham(id: Int) {
        val dataService = APIServices.getService()
        val callback = dataService.postSanphamchitiet(id.toString() + "")
        callback.enqueue(object : Callback<List<SanPham>?> {
            override fun onResponse(
                call: Call<List<SanPham>?>,
                response: Response<List<SanPham>?>
            ) {
                Log.d("AAA", "chitietsanpham$response")
                if (response.isSuccessful) {
                    arrayList = response.body() as ArrayList<SanPham>?
                    if (arrayList != null) {
                        txtmotasanphamchitiet!!.text = arrayList!![0].mota
                        txttensanphamchitiet!!.text = arrayList!![0].tenSanPham
                        txtthongsokythuat!!.text = arrayList!![0].thongSoKyThuat
                        val arrayhinh = arrayList!![0].hinhMoTa?.split("@")?.toTypedArray()
                        val adapter1 =
                            arrayhinh?.let { Adapter_SlideChitietsanpham(it, this@Chitietsanpham) }
                        ChitietsanphamViewpaget!!.adapter = adapter1
                        PageIndicatorview!!.setViewPager(ChitietsanphamViewpaget)
                        val sanpham = arrayList!![0]
                        Tinhtrangsanpham(sanpham)
                    }
                }
            }

            override fun onFailure(call: Call<List<SanPham>?>, t: Throwable) {
                Toast.makeText(
                    this@Chitietsanpham,
                    "Đã hết hạn dữ liệu vui lòng thử lại sau",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    @SuppressLint("ResourceType")
    private fun Tinhtrangsanpham(sanpham: SanPham) {
        var giasanphamsaukhuyenmai = 0
        val calendar = Calendar.getInstance()
        val simpleDateFormat = DecimalFormat("###,###,###")
        val format = SimpleDateFormat("dd-MM-yyyy")
        txtngaydang!!.text = sanpham.ngayDang
        if (sanpham.soLuong!! > 0) {
            txttrinhtrang!!.text = "Còn Hàng"
        } else {
            txttrinhtrang!!.text = "Hết Hàng"
        }
        if (sanpham.giamGia!! > 0 && sanpham.ngayKhuyenMai != "") {
            var ngaykhuyenmai: Date? = null
            var ngayhientai: Date? = null
            try {
                ngaykhuyenmai = format.parse(sanpham.ngayKhuyenMai + "")
                ngayhientai =
                    format.parse(calendar[Calendar.DATE].toString() + "-" + calendar[Calendar.MONTH] + "-" + calendar[Calendar.YEAR])
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            if (ngaykhuyenmai!!.compareTo(ngayhientai) > 0) {
                Log.d("AAA", "ngay$ngayhientai")
                txtgiasanphamgoc!!.paintFlags =
                    txtgiasanphamgoc!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                txtgiasanphamgoc!!.text = simpleDateFormat.format(sanpham.gia) + ""
                val giagiam = (100 - sanpham.giamGia!!).toFloat() / 100
                val giaspsaukhuyenmai = giagiam * sanpham.gia!!
                giasanphamsaukhuyenmai = giaspsaukhuyenmai.toInt()
                Log.d("AAA", giaspsaukhuyenmai.toString() + "")
                txtgiasanphamsaukhigiam!!.text =
                    simpleDateFormat.format(giasanphamsaukhuyenmai.toLong()) + "Đ"
                txtngaykhuyenmai!!.text = sanpham.ngayKhuyenMai
            }
        } else {
            giasanphamsaukhuyenmai = sanpham.gia!!
            txtgiasanphamsaukhigiam!!.text =
                simpleDateFormat.format(giasanphamsaukhuyenmai.toLong()) + "Đ"
            txtngaykhuyenmai!!.text = ""
            txtgiasanphamgoc!!.text = ""
        }
        arrayList!![0].hinhAnhSanPham?.let {
            arrayList!![0].id?.let { it1 ->
                arrayList!![0].tenSanPham?.let { it2 ->
                    themvaogiohang(
                        it1, giasanphamsaukhuyenmai, it,
                        it2
                    )
                }
            }
        }
    }

    private fun themvaogiohang(
        id: Int,
        giasanphamsaukhuyenmai: Int,
        hinhAnhSanPham: String,
        tenSanPham: String
    ) {
        floattingactionbuton!!.setOnClickListener {
            if (MainActivity.sharedPreferences!!.getString("username", "") == "" &&
                MainActivity.sharedPreferences!!.getInt("iduser", 0) == 0
            ) {
                Toast.makeText(this@Chitietsanpham, "co click", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@Chitietsanpham, Login::class.java)
                intent.putExtra("phandanhgia", id.toString() + "")
                startActivity(intent)
                finish()
            } else {
                val iduser = MainActivity.sharedPreferences!!.getInt("iduser", 0)
                val idsanpham = id
                Log.d("AAA", "id_user: $iduser")
                Log.d("AAA", "id_sanpham: $idsanpham")
                Log.d("AAA", "hinh san pham: $hinhAnhSanPham")
                Log.d("AAA", "tensanpham$tenSanPham")
                Log.d("AAA", "gia san pham: $giasanphamsaukhuyenmai")
                postgiohang(iduser, idsanpham, giasanphamsaukhuyenmai, hinhAnhSanPham, tenSanPham)
            }
        }
    }

    private fun postgiohang(
        iduser: Int,
        idsanpham: Int,
        giasanpham: Int,
        hinhsanpham: String,
        tensanpham: String
    ) {
        val dataService = APIServices.getService()
        val callback = dataService.postGiohang(iduser, idsanpham, giasanpham)
        callback.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                Log.d("AAA", "post gio hang: $response")
                if (response.isSuccessful) {
                    val mess = response.body()
                    Log.d("AAA", "Messsage: $mess")
                    Toast.makeText(
                        this@Chitietsanpham,
                        "San pham da duoc them vao gio hang",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@Chitietsanpham,
                        "Tam thoi khong them vao dc vui long thu lai sau",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.d("AAA", "Faid gio hang: $t")
            }
        })
    }
}