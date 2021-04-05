package viettu.pvt.shopping_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.Ultis.ApiUtils;
import viettu.pvt.shopping_app.api.ApiService;
import viettu.pvt.shopping_app.models.KhachHang;
import viettu.pvt.shopping_app.models.SanPham;

public class LoginActivity extends AppCompatActivity {

      TextInputEditText tv_name, tv_pass;

    Button btn_login, btn_dki, btn_cannel;
    private ApiService mAPIService;
    SharedPreferences sharedPref;
    MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        tv_name = findViewById(R.id.login_ten);
        tv_pass = findViewById(R.id.login_pass);
        btn_login = findViewById(R.id.login_dn);
        btn_dki = findViewById(R.id.login_dki);
        btn_cannel = findViewById(R.id.login_cannel);
        toolbar = findViewById(R.id.topAppBar_login);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedPref =getSharedPreferences("data", Context.MODE_PRIVATE);

        mAPIService = ApiUtils.getAPIService();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tv_name.getText().toString();
                String pass = tv_pass.getText().toString();
                if ( "".equals(name)|| "".equals(pass)){
                    Toast.makeText(LoginActivity.this,"Xin điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }else{
                  kiemtralogin(name, pass);
                }
            }
        });
        btn_dki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(LoginActivity.this, DangKyActivity.class);

                startActivity(intent);
            }
        });
        btn_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                setResult(Activity.RESULT_CANCELED, intent);
               startActivity(intent);
            }
        });

    }
    void kiemtralogin(String ten, String mk){
            List<KhachHang> listkh = new ArrayList<>();
            mAPIService.checkLogin(ten,mk).enqueue(new Callback<List<KhachHang>>() {
                @Override
                public void onResponse(Call<List<KhachHang>> call, Response<List<KhachHang>> response) {
                    if (response.isSuccessful() && response.body() !=null) {
                        for (int i = 0; i < response.body().size(); i++) {
                            KhachHang khachHang = new KhachHang(response.body().get(i).getId(),
                                    response.body().get(i).getTendangnhap(),response.body().get(i).getPassword(),
                                    response.body().get(i).getSdt(), response.body().get(i).getDiachi());
                            listkh.add(khachHang);
                        }
                       if ( listkh.size() != 0) {
                           SharedPreferences.Editor editor = sharedPref.edit();
                           editor.putBoolean("check", true);
                           editor.putString("name", listkh.get(0).getTendangnhap());
                           editor.putInt("idkhachhang",listkh.get(0).getId() );
                           editor.commit();
                           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                           intent.putExtra("idkhach", listkh.get(0).getId());
                           setResult(Activity.RESULT_OK, intent);
                           startActivity(intent);
                       }

                    }
                }

                @Override
                public void onFailure(Call<List<KhachHang>> call, Throwable t) {

                }
            });
    }
}