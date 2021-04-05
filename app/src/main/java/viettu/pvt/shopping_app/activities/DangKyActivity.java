package viettu.pvt.shopping_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.Ultis.ApiUtils;
import viettu.pvt.shopping_app.api.ApiService;

public class DangKyActivity extends AppCompatActivity {
    TextInputEditText ed_name, ed_pass, ed_sdt, ed_diachi;
    Button btn_dki, btn_can;
    private ApiService mAPIService;
    SharedPreferences sharedPref;
    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        ed_name = findViewById(R.id.dk_hoten);
        ed_pass = findViewById(R.id.dk_pass);
        ed_sdt = findViewById(R.id.dk_sdt);
        ed_diachi = findViewById(R.id.dk_diachi);
        btn_dki = findViewById(R.id.btn_dki);
        btn_can = findViewById(R.id.btn_cannel);
        toolbar = findViewById(R.id.topAppBar_dki);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAPIService = ApiUtils.getAPIService();
        btn_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_dki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed_name.getText().toString();
                String pass = ed_pass.getText().toString();
                String sdt = ed_sdt.getText().toString();
                String diachi = ed_diachi.getText().toString();
                if ("".equals(name)||"".equals(pass)|| "".equals(sdt)||"".equals(diachi)){
                    Toast.makeText(DangKyActivity.this,"Xin điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }else{
                  dangki(name,pass,sdt,diachi);
                  finish();
                }
            }
        });
    }
    void dangki(String ten, String mk, String dt, String dc){
        mAPIService.dangKi(ten, mk,dt,dc).enqueue(new Callback<ResponseBody>() {
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