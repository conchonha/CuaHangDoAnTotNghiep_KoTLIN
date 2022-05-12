package com.example.cuahang_doan.Services;

import com.example.cuahang_doan.model.Chitietdondathang;
import com.example.cuahang_doan.model.DanhMuc;
import com.example.cuahang_doan.model.Danhgia;
import com.example.cuahang_doan.model.DonDatHang;
import com.example.cuahang_doan.model.GetdataSanphammoinhat;
import com.example.cuahang_doan.model.GioHang;
import com.example.cuahang_doan.model.GioithieuShop;
import com.example.cuahang_doan.model.HoaDon;
import com.example.cuahang_doan.model.Nhanxetcuaban;
import com.example.cuahang_doan.model.QuangCao;
import com.example.cuahang_doan.model.SanPham;
import com.example.cuahang_doan.model.TinTuc;
import com.example.cuahang_doan.model.User;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @GET("model/taikhoan/getdataquanlytaikhoanadmin")
    Call<List<User>>getDataQuanLyTaiKhoanAdmin();

    @FormUrlEncoded
    @POST("model/login/dangnhap")
    Call<List<User>>postLogin(@Field("user") String user,
                                  @Field("pass") String pass);

    @GET("model/quangcao/getdata")
    Call<List<QuangCao>>GetdataQuangCao();

    @GET("model/danhmuc/getdata")
    Call<List<DanhMuc>>getdataDanhmuc();

    @GET("model/danhmuc/getdatalaptopmoinhat")
    Call<List<GetdataSanphammoinhat>>getDataSanphammoinhat();

    @GET("model/linhkienlaptop/getdatalinhkienlaptop")
    Call<List<GetdataSanphammoinhat>>getDataLinhkienlaptop();

    @GET("model/danhmuc/getdatathietbiluutruphukien")
    Call<List<GetdataSanphammoinhat>>getdataThietbiluutruphukien();

    @GET("model/danhmuc/getdatathietbinghenhin")
    Call<List<GetdataSanphammoinhat>>getDataThietbinghenhin();

    @FormUrlEncoded
    @POST("model/sanpham/getdatasanphamchitiet")
    Call<List<SanPham>>postSanphamchitiet(@Field("id") String id);

    @FormUrlEncoded
    @POST("model/danhgia/getdanhgia")
    Call<List<Danhgia>>getdataDanhgia(@Field("masanpham") String masanpham);

    @FormUrlEncoded
    @POST("model/danhgia/postDanhgia")
    Call<String>postDanhgia(@Field("username") String username,
                                   @Field("id_sanpham") String idsanpham,
                                   @Field("ngaydanhgia") String ngaydanhgia,
                                   @Field("namsao") int namsao,
                                   @Field("bonsao") int bonsao,
                                   @Field("basao") int basao,
                                   @Field("haisao") int haisao,
                                   @Field("motsao") int motsao,
                                   @Field("comment") String comment);

    @FormUrlEncoded
    @POST("model/giohang/postgiohang")
    Call<String>postGiohang(@Field("iduser") int iduser,
                            @Field("idsanpham") int idsanpham,
                            @Field("giasp") int giasp
                            );

    @GET("model/giohang/getdatagiohang")
    Call<List<GioHang>>getdataGiohang();

    @FormUrlEncoded
    @POST("model/giohang/updategiohang")
    Call<String>updategiohang(@Field("idsanpham") String idsanpham,
                                    @Field("soluong") int soluong,
                                    @Field("thanhtien") int thanhtien);
    @FormUrlEncoded
    @POST("model/giohang/delete")
    Call<String>Deletesanpham(@Field("idsanpham") String idsanpham);

    @GET("model/taikhoan/gettaikhoan")
    Call<List<User>>getdatataikhoan();

    @FormUrlEncoded
    @POST("model/taikhoan/timkiem")
    Call<List<User>>timkiem(@Field("timkiem") String timkiem);

    @FormUrlEncoded
    @POST("model/chitietdonhang/dondathang")
    Call<String>posdondathang(@Field("idtaikhoan") String idtaikhoan,
                               @Field("trinhtrang") String trinhtrang,
                               @Field("ngaydat") String ngaydat,
                               @Field("username") String username,
                               @Field("diachi") String diachi,
                               @Field("email") String email,
                               @Field("sodienthoai") String sodienthoai);

    @FormUrlEncoded
    @POST("model/dondathang/getdatahoadondondathangnguoidung")
    Call<List<HoaDon>>getdatahoadon(@Field("idtaikhoan") String idtaikhoan);


    @FormUrlEncoded
    @POST("model/hoadon/getdatahoadondondathangadmin")
    Call<List<HoaDon>>getdatahoadondondathangadmin(@Field("iddondathang") String iddondathang);


    @FormUrlEncoded
    @POST("model/laptopmacbook/getdatalaptopmacbook")
    Call<List<GetdataSanphammoinhat>>getdatasanphamdanhmuc(@Field("id") String id);

    @FormUrlEncoded
    @POST("model/usser/updatephoto")
    Call<List<User>>updatephotouser(@Field("hinh") String bytehinh,
                                    @Field("idtaikhoan") String idtaikhoan);

    @FormUrlEncoded
    @POST("model/dondathang/donhangganday")
    Call<List<HoaDon>>getdatadonhangganday(@Field("idtaikhoan") String idtaikhoan);

    @FormUrlEncoded
    @POST("model/taikhoan/updatethongtin")
    Call<List<User>>updatethongtintaikhoan(@Field("username") String username,
                                       @Field("sodienthoai") String sodienthoai,
                                       @Field("email") String email,
                                       @Field("idtaikhoan") String idtaikhoan);

    @FormUrlEncoded
    @POST("model/taikhoan/updatediachi")
    Call<List<User>>updatediachi(@Field("diachi") String diachi,
                                 @Field("idtaikhoan") String idtaikhoan);
    @FormUrlEncoded
    @POST("model/dondathang/choxetduyet")
    Call<List<DonDatHang>>getdatachoxetduyet(@Field("idtaikhoan") String idtaikhoan);

    @FormUrlEncoded
    @POST("model/dondathang/dangvanchuyen")
    Call<List<DonDatHang>>getdatadangvanchuyen(@Field("idtaikhoan") String idtaikhoan);

    @FormUrlEncoded
    @POST("model/dondathang/dagiaohang")
    Call<List<DonDatHang>>getdatadagiaohang(@Field("idtaikhoan") String idtaikhoan);

    @FormUrlEncoded
    @POST("model/dondathang/dahuy")
    Call<List<DonDatHang>>getdatadahuy(@Field("idtaikhoan") String idtaikhoan);

    @FormUrlEncoded
    @POST("model/chitietdondathang/getdatachitietdondathang")
    Call<List<Chitietdondathang>>getdatachitietdonhang(@Field("iddondathang") String iddondathang);

    @FormUrlEncoded
    @POST("model/chitietdondathang/getdatachuanhanxet")
    Call<List<Nhanxetcuaban>>getdatachuanhanxet(@Field("idtaikhoan") String idtaikhoan,
                                                @Field("trinhtrang") String trinhtrang);

    @FormUrlEncoded
    @POST("model/sanpham/getdataphantrangsanpham")
    Call<List<GetdataSanphammoinhat>>getdataPhantrang(@Field("soluong") String soluong);

    @FormUrlEncoded
    @POST("model/sanpham/getdataTimkiem")
    Call<List<GetdataSanphammoinhat>>getdataTimkiemsanpham(@Field("timkim") String timkim);

    @FormUrlEncoded
    @POST("model/donathang/updatehuydon")
    Call<String>UpdateHuydondathang(@Field("iddondathang") String isdondathang);

    @GET("model/gioithieushop/gioithieushop")
    Call<List<GioithieuShop>>Getdatagioithieushop();

    @FormUrlEncoded
    @POST("model/taikhoan/dangkytaikhoan")
    Call<String>dangkytaikhoan(@Field("username") String username,
                               @Field("password") String password,
                               @Field("email") String email,
                               @Field("sodienthoai") String sodienthoai,
                               @Field("diachi") String diachi,
                               @Field("idloai") String idloai
                               );
    @GET("model/tintuc/getdatatintuc")
    Call<List<TinTuc>>getdatatintuc();

    @FormUrlEncoded
    @POST("model/tintuc/timkimtintuc")
    Call<List<TinTuc>>getdatatimkimtintuc(
                               @Field("timkim") String timkim
    );

    @FormUrlEncoded
    @POST("model/taikhoan/DeleteAdminTai_khoan")
    Call<String>deleteAdminTai_khoan(@Field("idtaikhoan") String idTaikhoan);

    @FormUrlEncoded
    @POST("model/thongtin/update")
    Call<String>Updatethongtinshop(
            @Field("TenCuaHang") String TenCuaHang,
            @Field("TruSoChinh") String TruSoChinh,
            @Field("DienThoai") String DienThoai,
            @Field("Email") String Email,
            @Field("Website") String Website,
            @Field("Fanpage") String Fanpage

    );

    @GET("model/dondathang/getdatadangvanchuyenadmin")
    Call<List<DonDatHang>>getdatadangvanchuyenadmin();

    @GET("model/dondathang/getdatadahuyadmin")
    Call<List<DonDatHang>>getdatadahuyadmin();

    @GET("model/dondathang/dagiaohangadmin")
    Call<List<DonDatHang>>getdatadagiaohangadmin();

    @GET("model/dondathang/choxetduyetadmin")
    Call<List<DonDatHang>>getdatachoxetduyetadmin();

    @FormUrlEncoded
    @POST("model/dondathang/updatedondathangadmin")
    Call<String>updatedondathangadmin(@Field("id") String id,
                                      @Field("trinhtrang") String trinhtrang);

    @FormUrlEncoded
    @POST("model/tintuc/delete_NewsAdmin")
    Call<String>delete_NewsItemAdmin(@Field("id_News") String id_News);

    @FormUrlEncoded
    @POST("model/tintuc/update_NewsAdmin")
    Call<String>update_NewsAdmin(@Field("img_PictureUpdate") String img_PictureUpdate,
                                 @Field("edt_ContentUpdate") String edt_ContentUpdate,
                                 @Field("edt_NameUpdate") String edt_NameUpdate,
                                 @Field("id_News") String id_News
    );

    @FormUrlEncoded
    @POST("model/tintuc/insert_NewsAdmin")
    Call<String>insert_NewsAdmin(@Field("img_PictureInsert") String img_PictureUpdate,
                                 @Field("edt_ContentInsert") String edt_ContentUpdate,
                                 @Field("edt_NameInsert") String edt_NameUpdate

    );

    @FormUrlEncoded
    @POST("model/quanlysanpham/insert_ProductAdmin")
    Call<String>insert_ProductAdmin(@Field("text_DescriptionProduct") String text_DescriptionProduct,
                                    @Field("text_SpecificationsProduct") String text_SpecificationsProduct,
                                    @Field("text_NameProduct") String text_NameProduct,
                                    @Field("text_PricaeProduct") String text_PricaeProduct,
                                    @Field("text_NumberProduct") String text_NumberProduct,
                                    @Field("text_PromotionDayProduct") String text_PromotionDayProduct,
                                    @Field("txt_PromotionPriceProduct") String txt_PromotionPriceProduct,
                                    @Field("id_TypeProduct") String id_TypeProduct,
                                    @Field("text_Today") String text_Today,
                                    @Field("text_TheFirm") String text_TheFirm,
                                    @Field("text_PictureProduct") String text_PictureProduct,
                                    @Field("text_PictureDescriptionProduct")JSONArray jsonArray



                                    );
}
