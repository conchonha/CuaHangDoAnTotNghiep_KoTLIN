package com.example.cuahang_doan.Fragment.TrangChu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cuahang_doan.Activity.Gioi_Thieu;
import com.example.cuahang_doan.Activity.Login;
import com.example.cuahang_doan.Activity.MainActivity;
import com.example.cuahang_doan.Activity.SanPham;
import com.example.cuahang_doan.Activity.TinTuc;
import com.example.cuahang_doan.Activity.Vitrishop;
import com.example.cuahang_doan.Adapter.DanhmucAdapter;
import com.example.cuahang_doan.R;
import com.example.cuahang_doan.Services.APIServices;
import com.example.cuahang_doan.Services.DataService;
import com.example.cuahang_doan.model.DanhMuc;
import com.example.cuahang_doan.model.GioithieuShop;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_trangchu extends Fragment {
    private Toolbar toolbarlayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private DrawerLayout drawerlayout;
    private View view;
    private MainActivity context;
    private ListView listviewDanhmuc;
    private TextView txtTitleDanhmucvitri,txtTitleDanhmuchotro,txtTitleDanhmucemail,txtTitleDanhmucsdt,
            txtTitleTaikhoandangnhap;

    public Fragment_trangchu(MainActivity context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_trangchu,container,false);
        anhxa();
        actionbar();
        getdataDanhmuc();
        init();
        getdatagioithieushop();
        return view;
    }

    private void init() {
        txtTitleDanhmuchotro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Gioi_Thieu.class));
            }
        });
        txtTitleDanhmucvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Vitrishop.class));
            }
        });
        if(!MainActivity.sharedPreferences.getString("username","").equals("")){
            txtTitleTaikhoandangnhap.setText("Đăng Xuất");
        }else{
            txtTitleTaikhoandangnhap.setText("Đăng Nhập");
        }
        txtTitleTaikhoandangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
    }

    private void getdataDanhmuc() {
        DataService dataService= APIServices.getService();
        Call<List<DanhMuc>>callback=dataService.getdataDanhmuc();
        callback.enqueue(new Callback<List<DanhMuc>>() {
            @Override
            public void onResponse(Call<List<DanhMuc>> call, Response<List<DanhMuc>> response) {
                Log.d("AAA","ListView_DanhMuc"+response.toString());
                if(response!=null){
                    ArrayList<DanhMuc>arrayList= (ArrayList<DanhMuc>) response.body();
                    Log.d("SSS", "onResponse: "+arrayList);
                    if(arrayList!=null) {
                        DanhmucAdapter adapter = new DanhmucAdapter(getActivity(), R.layout.layout_danhmuc, arrayList);
                        listviewDanhmuc.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(listviewDanhmuc);
                        setonclicklisview(arrayList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DanhMuc>> call, Throwable t) {
                Log.d("AAA","ListView_DanhMuc"+t.toString());
            }
        });
    }
    private void getdatagioithieushop() {
        DataService dataService= APIServices.getService();
        Call<List<GioithieuShop>>callback=dataService.Getdatagioithieushop();
        callback.enqueue(new Callback<List<GioithieuShop>>() {
            @Override
            public void onResponse(Call<List<GioithieuShop>> call, Response<List<GioithieuShop>> response) {
                Log.d("AAA","Gioi thieu shop: "+response.toString());
                if(response.isSuccessful()){
                    ArrayList<GioithieuShop>arrayList= (ArrayList<GioithieuShop>) response.body();
                    GioithieuShop gioithieuShop=arrayList.get(0);
                    txtTitleDanhmucemail.setText(gioithieuShop.getEmail());
                    txtTitleDanhmucsdt.setText(gioithieuShop.getDienThoai());

                }
            }

            @Override
            public void onFailure(Call<List<GioithieuShop>> call, Throwable t) {

            }
        });
    }
    private void setonclicklisview(final ArrayList<DanhMuc>arrayList){
        listviewDanhmuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), i+arrayList.get(i).getTen(), Toast.LENGTH_SHORT).show();
                switch (i){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        Intent intent=new Intent(getActivity(), SanPham.class);
                        intent.putExtra("id",arrayList.get(i).getId()+"");
                        startActivity(intent);
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), TinTuc.class));
                        break;
                    default:{
                        Toast.makeText(getActivity(), "Chua hoan thanh", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void actionbar() {

        //Expanded set màu mở rộng
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorwhile));
        //set màu thu hẹp
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        context.setSupportActionBar(toolbarlayout);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlayout.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarlayout.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void anhxa() {
        txtTitleTaikhoandangnhap=view.findViewById(R.id.txtTitleTaikhoandangnhap);
        txtTitleDanhmucsdt=view.findViewById(R.id.txtTitleDanhmucsdt);
        txtTitleDanhmucemail=view.findViewById(R.id.txtTitleDanhmucemail);
        txtTitleDanhmucvitri=view.findViewById(R.id.txtTitleDanhmucvitri);
        txtTitleDanhmuchotro=view.findViewById(R.id.txtTitleDanhmuchotro);
        listviewDanhmuc=view.findViewById(R.id.listviewDanhmuc);
        drawerlayout=view.findViewById(R.id.drawerlayout);
        collapsingToolbarLayout=view.findViewById(R.id.collapsingToolbarLayout);
        toolbarlayout =view.findViewById(R.id.toolbarlayout);
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
       try{
           ListAdapter listAdapter = listView.getAdapter();
           if (listAdapter == null) {
               // pre-condition
               return;
           }

           int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
           int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
           for (int i = 0; i < listAdapter.getCount(); i++) {

               View listItem = listAdapter.getView(i, null, listView);

               if(listItem != null){
                   // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                   listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                   listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                   totalHeight += listItem.getMeasuredHeight();

               }
           }

           ViewGroup.LayoutParams params = listView.getLayoutParams();
           params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
           listView.setLayoutParams(params);
           listView.requestLayout();
       }catch (Exception e){

       }
    }
}
