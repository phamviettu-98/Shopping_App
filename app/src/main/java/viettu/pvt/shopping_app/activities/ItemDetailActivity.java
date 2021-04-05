package viettu.pvt.shopping_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.Ultis.ApiUtils;
import viettu.pvt.shopping_app.api.ApiService;
import viettu.pvt.shopping_app.models.SanPham;

public class ItemDetailActivity extends AppCompatActivity {
    ImageView img_sp,star1, star2, star3, star4 , star5;
    TextView tv_ten, tv_sl_rate, tv_giacu, tv_giamoi, tv_mota, tv_soluong;
    Button btn_tru, btn_cong, btn_them;
    private ApiService mAPIService;
   List<SanPham> list = new ArrayList<>();
   SanPham sp;
    SharedPreferences sharedPref;
    boolean checkLogin;
    NumberFormat nf;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Anhxa();

        sharedPref =getSharedPreferences("data", Context.MODE_PRIVATE);

        checkLogin = sharedPref.getBoolean("check",false);
        mAPIService = ApiUtils.getAPIService();
        nf = NumberFormat.getInstance();
        Intent intent = getIntent();
        int id = intent.getIntExtra("item_id", 1);
        Log.d("aa", id+" ");
        getSanPhamByID(id);
        btn_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = Integer.parseInt(tv_soluong.getText().toString())+1;
                if ( soLuong > 1)  btn_tru.setVisibility(View.VISIBLE);
                if ( soLuong >9){
                    soLuong = 10;
                    btn_cong.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_cong.setVisibility(View.VISIBLE);

                }
                tv_soluong.setText(soLuong+"");
            }
        });
        btn_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = Integer.parseInt(tv_soluong.getText().toString())-1;
                if ( soLuong < 10)   btn_cong.setVisibility(View.VISIBLE);
                if ( soLuong <2){
                    soLuong= 1;
                    btn_tru.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_tru.setVisibility(View.VISIBLE);


                }
                tv_soluong.setText(soLuong+"");
            }
        });





    }

    private void TruyenData() {
        Picasso.get().load(sp.getHinhanhsp()).into(img_sp);
        tv_ten.setText(sp.getTensp());
        tv_sl_rate.setText(sp.getSl_rate()+" đánh giá");
        tv_giacu.setText(nf.format(sp.getGiasanpham())+" ₫");
        tv_giacu.setPaintFlags(tv_giacu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        int giamoi = (int) (sp.getGiasanpham() - sp.getUudai());
        tv_giamoi.setText(nf.format(giamoi)+ " ₫" );
        tv_mota.setText(sp.getMotasp());
        float x = sp.getRate();
        if ( x == 5) {
            star1.setImageResource(R.drawable.ic_start);
            star2.setImageResource(R.drawable.ic_start);
            star3.setImageResource(R.drawable.ic_start);
            star4.setImageResource(R.drawable.ic_start);
            star5.setImageResource(R.drawable.ic_start);
        }
        else if ( 4 <x && x < 5 ){
            star1.setImageResource(R.drawable.ic_start);
            star2.setImageResource(R.drawable.ic_start);
            star3.setImageResource(R.drawable.ic_start);
            star4.setImageResource(R.drawable.ic_start);
            star5.setImageResource(R.drawable.ic_star_half);
        } else if ( x == 4){
            star1.setImageResource(R.drawable.ic_start);
            star2.setImageResource(R.drawable.ic_start);
            star3.setImageResource(R.drawable.ic_start);
            star4.setImageResource(R.drawable.ic_start);
            star5.setImageResource(R.drawable.ic_start_hide);
        } else if ( 3 < x && x < 4){
            star1.setImageResource(R.drawable.ic_start);
            star2.setImageResource(R.drawable.ic_start);
            star3.setImageResource(R.drawable.ic_start);
            star4.setImageResource(R.drawable.ic_star_half);
            star5.setImageResource(R.drawable.ic_start_hide);
        }else if (x == 3){
            star1.setImageResource(R.drawable.ic_start);
            star2.setImageResource(R.drawable.ic_start);
            star3.setImageResource(R.drawable.ic_start);
            star4.setImageResource(R.drawable.ic_start_hide);
            star5.setImageResource(R.drawable.ic_start_hide);
        }
        else  if ( 2 <x && x < 3){
            star1.setImageResource(R.drawable.ic_start);
            star2.setImageResource(R.drawable.ic_start);
            star3.setImageResource(R.drawable.ic_star_half);
            star4.setImageResource(R.drawable.ic_start_hide);
            star5.setImageResource(R.drawable.ic_start_hide);
        }
        else if ( x == 2){
            star1.setImageResource(R.drawable.ic_start);
            star2.setImageResource(R.drawable.ic_start);
            star3.setImageResource(R.drawable.ic_start_hide);
            star4.setImageResource(R.drawable.ic_start_hide);
            star5.setImageResource(R.drawable.ic_start_hide);
        }else  if ( x > 1 && x < 2){
            star1.setImageResource(R.drawable.ic_start);
            star2.setImageResource(R.drawable.ic_star_half);
            star3.setImageResource(R.drawable.ic_start_hide);
            star4.setImageResource(R.drawable.ic_start_hide);
            star5.setImageResource(R.drawable.ic_start_hide);
        }else if  (x== 1){
            star1.setImageResource(R.drawable.ic_start);
            star2.setImageResource(R.drawable.ic_start_hide);
            star3.setImageResource(R.drawable.ic_start_hide);
            star4.setImageResource(R.drawable.ic_start_hide);
            star5.setImageResource(R.drawable.ic_start_hide);
        }else if ( x> 0 && x < 1){
            star1.setImageResource(R.drawable.ic_star_half);
            star2.setImageResource(R.drawable.ic_start_hide);
            star3.setImageResource(R.drawable.ic_start_hide);
            star4.setImageResource(R.drawable.ic_start_hide);
            star5.setImageResource(R.drawable.ic_start_hide);
        } else{
            star1.setImageResource(R.drawable.ic_start_hide);
            star2.setImageResource(R.drawable.ic_start_hide);
            star3.setImageResource(R.drawable.ic_start_hide);
            star4.setImageResource(R.drawable.ic_start_hide);
            star5.setImageResource(R.drawable.ic_start_hide);
        }

    }
    private void Anhxa() {
        img_sp = findViewById(R.id.detail_img);
        tv_ten = findViewById(R.id.detail_ten);
        tv_sl_rate = findViewById(R.id.detai_sosao);
        tv_giacu = findViewById(R.id.detai_giacu);
        tv_giamoi = findViewById(R.id.detai_giamoi);
        tv_mota= findViewById(R.id.detai_mota);
        tv_soluong = findViewById(R.id.detail_sl);
        btn_cong = findViewById(R.id.sl_tang);
        btn_tru = findViewById(R.id.sl_giam);
        btn_them = findViewById(R.id.detail_themgiohang);
        star1 = findViewById(R.id.detail_s1);
        star2 = findViewById(R.id.detail_s2);
        star3 = findViewById(R.id.detail_s3);
        star4 = findViewById(R.id.detail_s4);
        star5 = findViewById(R.id.detail_s5);
        toolbar = findViewById(R.id.appbar_chitiet);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getSanPhamByID(int id){
                 mAPIService.getSPById(id).enqueue(new Callback<List<SanPham>>() {
                     @Override
                     public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                         try {
                             for (int i = 0; i < response.body().size(); i++){
                                 SanPham sanPham = new SanPham(response.body().get(i).getId(),
                                         response.body().get(i).getTensp(), response.body().get(i).getGiasanpham(),
                                         response.body().get(i).getHinhanhsp(), response.body().get(i).getMotasp(),
                                         response.body().get(i).getRate(), response.body().get(i).getSl_rate(),
                                         response.body().get(i).getUudai(), response.body().get(i).getDacbiet(),
                                         response.body().get(i).getIdloaisp(), response.body().get(i).getLoaiSP());
                                 list.add(sanPham);
                             }
                             sp = list.get(0);

                             TruyenData();
                             btn_them.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     sharedPref =getSharedPreferences("data",Context.MODE_PRIVATE);

                                     checkLogin = sharedPref.getBoolean("check",false);
                                     if ( checkLogin == true) {
                                         int sluong = Integer.parseInt(tv_soluong.getText().toString());
                                         int idkhach = 1;
                                         int trangthai = 1;
                                         int giatien = (int) (sp.getGiasanpham() -sp.getUudai());
                                         idkhach = sharedPref.getInt("idkhachhang", 1);
                                         themGiohang(idkhach, sp.getId(), sluong, giatien *sluong, trangthai );
                                         Intent intent = new Intent(ItemDetailActivity.this, GioHangActivity.class);
                                         startActivity(intent);
                                     }
                                     else {
                                         Toast.makeText(ItemDetailActivity.this, "Can Dang nhap", Toast.LENGTH_LONG).show();
                                         showDialog();
                                     }
                                 }
                             });


                         }catch (Exception e){
                             Log.d("aaa", e.getMessage().toString());
                             e.printStackTrace();
                         }
                     }

                     @Override
                     public void onFailure(Call<List<SanPham>> call, Throwable t) {

                     }
                 });
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

    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_tb_login);
        Button ok = dialog.findViewById(R.id.btn_ok);
        Button cal = dialog.findViewById(R.id.btn_cal);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailActivity.this, LoginActivity.class);
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


}