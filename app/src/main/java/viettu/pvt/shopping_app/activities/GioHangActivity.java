package viettu.pvt.shopping_app.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.Ultis.ApiUtils;
import viettu.pvt.shopping_app.Ultis.GioHangClick;
import viettu.pvt.shopping_app.adapter.GioHangAdapter;
import viettu.pvt.shopping_app.api.ApiService;
import viettu.pvt.shopping_app.models.GioHang;

public class GioHangActivity extends AppCompatActivity implements GioHangClick {

    RecyclerView rv_giohang;
    Button btnthanhtoan, btnxoa;
    TextView tongtien;
    SharedPreferences sharedPref;
    private ApiService mAPIService;
    private List<GioHang> hangList;
    GioHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        rv_giohang = findViewById(R.id.rv_giohang);
        btnxoa = findViewById(R.id.gh_xoa);
        btnthanhtoan = findViewById(R.id.btn_thanhtoan);

        mAPIService = ApiUtils.getAPIService();
        hangList = new ArrayList<>();
        sharedPref =getSharedPreferences("data", Context.MODE_PRIVATE);
        int idkhach = sharedPref.getInt("idkhachhang", 1);
        getListGH(idkhach);




    }
    private void truyendata(){
         adapter = new GioHangAdapter(this,hangList, this );

        rv_giohang.setAdapter(adapter);
        rv_giohang.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter.notifyDataSetChanged();
    }
    void getListGH( int idkhach){
        mAPIService.getlistCart( idkhach).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                  try {
                      int tong_tien =0;
                      for( int i =0 ; i < response.body().size(); i++){
                          GioHang hang = new GioHang(response.body().get(i).getIdgiohang(), response.body().get(i).getId(),
                                  response.body().get(i).getIdsanpham(), response.body().get(i).getSoluong(), response.body().get(i).getTongtien(),
                                  response.body().get(i).getTrangthai());

                          hangList.add(hang);

                      }

                      truyendata();
                      Toast.makeText(GioHangActivity.this, hangList.size()+"   ", Toast.LENGTH_LONG).show();
                       adapter.notifyDataSetChanged();
                      rv_giohang.invalidate();
                      btnxoa.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                         for ( GioHang gh : hangList){
                             mAPIService.uppdatecart(gh.getIdgiohang(), gh.getSoluong(), gh.getTongtien()).enqueue(new Callback<ResponseBody>() {
                                 @Override
                                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                 }

                                 @Override
                                 public void onFailure(Call<ResponseBody> call, Throwable t) {

                                 }
                             });

                         }
                              Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
                         startActivity(intent);
                          }
                      });
                      btnthanhtoan.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                               for(GioHang gh :hangList){
                                   mAPIService.uppdatethanhtoan(gh.getIdgiohang(), gh.getSoluong(), gh.getTongtien(),2).enqueue(new Callback<ResponseBody>() {
                                       @Override
                                       public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                       }

                                       @Override
                                       public void onFailure(Call<ResponseBody> call, Throwable t) {

                                       }
                                   });

                               }
                              Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
                              startActivity(intent);
                          }
                      });


                  }catch (Exception e){
                        e.printStackTrace();
                      Toast.makeText(GioHangActivity.this, " Error   "+ e.getMessage().toString(), Toast.LENGTH_LONG).show();
                  }
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                Toast.makeText(GioHangActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void Delxoaclick(GioHang gh) {
//        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(getBaseContext());
//        dialogxoa.setMessage("Bạn có muốn xoá sản phẩm này không ?");
//        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                         mAPIService.delcart(gh.getIdgiohang()).enqueue(new Callback<ResponseBody>() {
//                             @Override
//                             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                 Toast.makeText(getApplicationContext(), "Xoa thanh cong", Toast.LENGTH_LONG).show();
//                             }
//
//                             @Override
//                             public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                 Toast.makeText(getApplicationContext(), "Xoa thanh cong", Toast.LENGTH_LONG).show();
//                             }
//                         });
//            }
//        });
//        dialogxoa.setNegativeButton("Cannel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//            }
//        });
//        dialogxoa.show();
    }
}