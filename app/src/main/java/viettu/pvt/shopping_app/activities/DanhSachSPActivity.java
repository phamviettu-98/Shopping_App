package viettu.pvt.shopping_app.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.Ultis.ApiUtils;
import viettu.pvt.shopping_app.Ultis.SanPhamClick;
import viettu.pvt.shopping_app.adapter.DanhSachSanPhamAdapter;
import viettu.pvt.shopping_app.api.ApiService;
import viettu.pvt.shopping_app.models.SanPham;

public class DanhSachSPActivity extends AppCompatActivity implements SanPhamClick {
    RecyclerView rv_dssp;
    private List<SanPham> listsp;
    private  int id =1;
    private ApiService mAPIService;
    DanhSachSanPhamAdapter danhSachSanPhamAdapter;
    SharedPreferences sharedPref;
    boolean checkLogin;
    TextView tv_ds;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_s_p);
         tv_ds = findViewById(R.id.view_tv);
        rv_dssp = findViewById(R.id.ds_rv);
        mAPIService = ApiUtils.getAPIService();
        toolbar = findViewById(R.id.appbarDS);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


        listsp = new ArrayList<>();
        Intent intent = getIntent();
        id = intent.getIntExtra("idloaisp", 1);
        if ( id == 1 ){
            tv_ds.setText("Danh Sách Điện Thoại");
        }else if ( id == 2){
            tv_ds.setText("Danh Sách LapTop");

        }
        else {
            tv_ds.setText("Danh Sách Tablet");
        }
       getDanhSach();


        Toast.makeText(DanhSachSPActivity.this, listsp.size()+" ", Toast.LENGTH_LONG).show();




    }

    private void truyendata() {
        danhSachSanPhamAdapter = new DanhSachSanPhamAdapter(this, listsp,this);
        rv_dssp.setAdapter(danhSachSanPhamAdapter);

        rv_dssp.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


    }

    private void getDanhSach(){
        mAPIService.getDanhSachSanPham(id).enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.isSuccessful() && response.body() !=null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        SanPham sp = new SanPham(response.body().get(i).getId(),
                                response.body().get(i).getTensp(), response.body().get(i).getGiasanpham(),
                                response.body().get(i).getHinhanhsp(), response.body().get(i).getMotasp(),
                                response.body().get(i).getRate(), response.body().get(i).getSl_rate(),
                                response.body().get(i).getUudai(), response.body().get(i).getDacbiet(),
                                response.body().get(i).getIdloaisp(), response.body().get(i).getLoaiSP());
                        listsp.add(sp);
                    }
                    truyendata();
                    Toast.makeText(DanhSachSPActivity.this, listsp.size() + " ", Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void SanPhamClick(SanPham sp, ImageView img) {
        Intent intent = new Intent(DanhSachSPActivity.this, ItemDetailActivity.class);
        intent.putExtra("item_id", sp.getId());
        intent.putExtra("item_ten", sp.getTensp());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DanhSachSPActivity.this,img,"shareName");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void SanPhamClickView(SanPham sp) {
        Intent intent = new Intent(DanhSachSPActivity.this, ItemDetailActivity.class);
        intent.putExtra("item_id", sp.getId());
        intent.putExtra("item_ten", sp.getTensp());
          startActivity(intent);
    }

    @Override
    public void SanPhamClickAdd(SanPham sp) {

        sharedPref =getSharedPreferences("data", Context.MODE_PRIVATE);

        checkLogin = sharedPref.getBoolean("check",false);
        if ( checkLogin == true) {
            int sluong = 1;
            int idkhach = 1;
            int trangthai = 1;
            int giatien = (int) (sp.getGiasanpham() -sp.getUudai());
            idkhach = sharedPref.getInt("idkhachhang", 1);
            themGiohang(idkhach, sp.getId(), sluong, giatien *sluong, trangthai );
            Intent intent = new Intent(DanhSachSPActivity.this, GioHangActivity.class);
            intent.putExtra("item_id", sp.getId());
            intent.putExtra("item_ten", sp.getTensp());
            startActivity(intent);
        }
        else {
            Toast.makeText(DanhSachSPActivity.this, "Can Dang nhap", Toast.LENGTH_LONG).show();
            showDialog();
        }
    }
    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_tb_login);
        Button ok = dialog.findViewById(R.id.btn_ok);
        Button cal = dialog.findViewById(R.id.btn_cal);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachSPActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void themGiohang(int idkhach, int id, int sluong, int tong, int trangthai) {
        mAPIService.addCart(idkhach, id, sluong, tong, trangthai ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {


                    Toast.makeText(getBaseContext(), "Sucess", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Log.e("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", t.toString());
                Toast.makeText(getBaseContext(), "onFailure", Toast.LENGTH_LONG).show();
            }
        });
    }
}