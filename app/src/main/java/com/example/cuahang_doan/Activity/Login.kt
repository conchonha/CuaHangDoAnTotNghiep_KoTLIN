package com.example.cuahang_doan.Activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.os.Bundle
import com.example.cuahang_doan.R
import android.content.Intent
import com.example.cuahang_doan.Activity.DangKyTaiKhoan
import com.example.cuahang_doan.Activity.MainActivity
import android.view.animation.Animation
import com.example.cuahang_doan.Services.DataService
import com.example.cuahang_doan.Services.APIServices
import com.example.cuahang_doan.Activity.nhanvien.NhanVien
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.cuahang_doan.Activity.admin.Admin
import com.example.cuahang_doan.Activity.Chitietsanpham
import com.example.cuahang_doan.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Login() : AppCompatActivity() {
    private var edittextusername: TextInputEditText? = null
    private var edittextpassword: TextInputEditText? = null
    private var btndangnhap: Button? = null
    private var ckeckbox: CheckBox? = null
    private var arrayList: ArrayList<User>? = null
    private var textdangky: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setanim()
        anhxa()
        login()
        init()
    }

    private fun init() {
        textdangky!!.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext, DangKyTaiKhoan::class.java
                )
            )
        })
    }

    private fun login() {
        if (MainActivity.sharedPreferences?.getString("username", "") != null &&
            MainActivity.sharedPreferences?.getString("password", "") != null
        ) {
            edittextusername!!.setText(MainActivity.sharedPreferences!!.getString("username", ""))
            edittextpassword!!.setText(MainActivity.sharedPreferences!!.getString("password", ""))
        }
        btndangnhap!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                dangnhap()
            }
        })
    }

    private fun anhxa() {
        textdangky = findViewById(R.id.textdangky)
        edittextusername = findViewById(R.id.edittextusername)
        edittextpassword = findViewById(R.id.edittextpassword)
        btndangnhap = findViewById(R.id.btndangnhap)
        ckeckbox = findViewById(R.id.ckeckbox)
        arrayList = ArrayList()
    }

    private fun setanim() {
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.animalpha)
        val relativelayout = findViewById<RelativeLayout>(R.id.relativelayout)
        relativelayout.animation = animation
    }

    private fun dangnhap() {
        if (edittextusername!!.text.toString()
                .trim { it <= ' ' } != "" && edittextpassword!!.text.toString()
                .trim { it <= ' ' } != ""
        ) {
            val dataService = APIServices.getService()
            val callback = dataService.postLogin(
                edittextusername!!.text.toString(), edittextpassword!!.text.toString()
            )
            callback.enqueue(object : Callback<List<User>?> {
                override fun onResponse(call: Call<List<User>?>, response: Response<List<User>?>) {
                    Log.d("AAA", response.toString())
                    if (response.isSuccessful) {
                        arrayList = response.body() as ArrayList<User>?
                        if (arrayList!!.size > 0) {
                            if (arrayList!![0].loai == 1) {
                                MainActivity.editor!!.remove("admin")
                                MainActivity.editor!!.putString("nhanvien", "nhanvien")
                                MainActivity.editor!!.commit()
                                startActivity(Intent(applicationContext, NhanVien::class.java))
                                Toast.makeText(
                                    this@Login,
                                    "Đăng nhập thành công",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (arrayList!![0].loai == 2) {
                                val dialog = AlertDialog.Builder(this@Login)
                                dialog.setTitle("Bạn Đang Đăng Nhập Với Quyền Admin")
                                dialog.setPositiveButton(
                                    "Có",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, which: Int) {
                                            startActivity(
                                                Intent(
                                                    applicationContext,
                                                    Admin::class.java
                                                )
                                            )
                                            MainActivity.editor!!.remove("nhanvien")
                                            MainActivity.editor!!.putString(
                                                "admin",
                                                edittextusername!!.text.toString()
                                            )
                                            MainActivity.editor!!.putString(
                                                "username",
                                                edittextusername!!.text.toString()
                                            )
                                            MainActivity.editor!!.putString(
                                                "password",
                                                edittextpassword!!.text.toString()
                                            )
                                            arrayList!![0].id?.let {
                                                MainActivity.editor!!.putInt(
                                                    "iduser",
                                                    it
                                                )
                                            }
                                            MainActivity.editor!!.putString(
                                                "email",
                                                arrayList!![0].email
                                            )
                                            MainActivity.editor!!.putString(
                                                "sodienthoai",
                                                arrayList!![0].phoneNumBer
                                            )
                                            MainActivity.editor!!.putString(
                                                "hinh",
                                                APIServices.urlhinh + arrayList!![0].id + ".jpg"
                                            )
                                            MainActivity.editor!!.putString(
                                                "diachi",
                                                arrayList!![0].adress
                                            )
                                            MainActivity.editor!!.commit()
                                            Toast.makeText(
                                                this@Login,
                                                "Đăng nhập thành công",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                                dialog.setNegativeButton(
                                    "Không",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, which: Int) {
                                            startActivity(
                                                Intent(
                                                    applicationContext,
                                                    MainActivity::class.java
                                                )
                                            )
                                            MainActivity.editor!!.remove("nhanvien")
                                            MainActivity.editor!!.remove("admin")
                                            MainActivity.editor!!.putString(
                                                "username",
                                                edittextusername!!.text.toString()
                                            )
                                            MainActivity.editor!!.putString(
                                                "password",
                                                edittextpassword!!.text.toString()
                                            )
                                            arrayList!![0].id?.let {
                                                MainActivity.editor!!.putInt(
                                                    "iduser",
                                                    it
                                                )
                                            }
                                            MainActivity.editor!!.putString(
                                                "email",
                                                arrayList!![0].email
                                            )
                                            MainActivity.editor!!.putString(
                                                "sodienthoai",
                                                arrayList!![0].phoneNumBer
                                            )
                                            MainActivity.editor!!.putString(
                                                "hinh",
                                                APIServices.urlhinh + arrayList!![0].id + ".jpg"
                                            )
                                            MainActivity.editor!!.putString(
                                                "diachi",
                                                arrayList!![0].adress
                                            )
                                            MainActivity.editor!!.commit()
                                            Toast.makeText(
                                                this@Login,
                                                "Đăng nhập thành công",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                                dialog.show()
                            } else {
                                Toast.makeText(
                                    this@Login,
                                    "Đăng nhập thành công",
                                    Toast.LENGTH_SHORT
                                ).show()
                                MainActivity.editor!!.remove("admin")
                                MainActivity.editor!!.remove("nhanvien")
                                Toast.makeText(
                                    this@Login,
                                    "Đăng Nhập Thành Công",
                                    Toast.LENGTH_SHORT
                                ).show()
                                MainActivity.editor!!.putString(
                                    "username",
                                    edittextusername!!.text.toString()
                                )
                                MainActivity.editor!!.putString(
                                    "password",
                                    edittextpassword!!.text.toString()
                                )
                                arrayList!![0].id?.let {
                                    MainActivity.editor!!.putInt("iduser",
                                        it
                                    )
                                }
                                MainActivity.editor!!.putString("email", arrayList!![0].email)
                                MainActivity.editor!!.putString(
                                    "sodienthoai",
                                    arrayList!![0].phoneNumBer
                                )
                                MainActivity.editor!!.putString(
                                    "hinh",
                                    APIServices.urlhinh + arrayList!![0].id + ".jpg"
                                )
                                MainActivity.editor!!.putString("diachi", arrayList!![0].adress)
                                MainActivity.editor!!.remove("admin")
                                MainActivity.editor!!.commit()
                                Log.d(
                                    "AAA",
                                    "user sharedPreferences" + MainActivity.sharedPreferences!!.getString(
                                        "username",
                                        ""
                                    )
                                )
                                setintentt()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "tài khoản không chính sác",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>?>, t: Throwable) {
                    Toast.makeText(
                        this@Login,
                        "Xảy ra lỗi vui lòng quay lại sau",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("AAA", t.toString())
                }
            })
        }
    }

    private fun setintentt() {
        val intent = intent
        if (intent.hasExtra("phandanhgia")) {
            val msp = intent.getStringExtra("phandanhgia")
            if (msp != "") {
                val mspmoi = msp.toInt()
                Log.d("AAA", "masanpham$msp")
                val intent1 = Intent(applicationContext, Chitietsanpham::class.java)
                intent1.putExtra("id", mspmoi)
                startActivity(intent1)
                finish()
            }
        } else {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}