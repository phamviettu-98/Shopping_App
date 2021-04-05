package viettu.pvt.shopping_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.Ultis.ApiUtils;
import viettu.pvt.shopping_app.Ultis.SanPhamItemClick;
import viettu.pvt.shopping_app.adapter.KMMoingayAdapter;
import viettu.pvt.shopping_app.adapter.SanPhamAdapter;
import viettu.pvt.shopping_app.adapter.SlideAdapter;
import viettu.pvt.shopping_app.api.ApiService;
import viettu.pvt.shopping_app.models.SanPham;
import viettu.pvt.shopping_app.models.Slide;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements SanPhamItemClick, NavigationView.OnNavigationItemSelectedListener {
    ViewPager2 viewpage;
    private List<Slide> SlideList;
    RecyclerView rv_km, rv_dt,rv_laptop, rv_tablet;
    private  List<SanPham> listKM;
    private  List<SanPham> listDT;
    private  List<SanPham> listLT;
    private  List<SanPham> listTB;
    private ApiService mAPIService;
    private TabLayout indicator;
    MaterialToolbar toolbar;
    CircularProgressIndicator pro_nb;
    private Handler handler = new Handler();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences sharedPref;
    Menu menu;
    TextView tv_hdname, tv_hdll;
    boolean checkLogin;
    private final  int REQUEST_CODE = 123;


    private int progressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        sharedPref =getSharedPreferences("data",Context.MODE_PRIVATE);

        checkLogin = sharedPref.getBoolean("check",false);

        mAPIService = ApiUtils.getAPIService();
        listKM = new ArrayList<>();
        listDT = new ArrayList<>();
        listLT = new ArrayList<>();
        listTB = new ArrayList<>();
        pro_nb.setProgress(0);
        final int totalTime = 100;

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            pro_nb.setProgress(progressStatus);

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        pro_nb.setVisibility(View.INVISIBLE);
        getSlide();
        getSPKM();

        getDTNB();
        getLaptopNB();
        SanPhamAdapter kmnb = new SanPhamAdapter(this, listKM,this);
        rv_km.setAdapter(kmnb);
        rv_km.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        SanPhamAdapter dtnb = new SanPhamAdapter(this, listDT,this);
        rv_dt.setAdapter(dtnb);
        rv_dt.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        SanPhamAdapter ltnb = new SanPhamAdapter(this, listLT,this);
        rv_laptop.setAdapter(ltnb);
        rv_laptop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        getTablet();
        SanPhamAdapter tbnb = new SanPhamAdapter(this, listTB,this);
        rv_tablet.setAdapter(tbnb);
        rv_tablet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        ShowMenu();
        Log.d("aab", checkLogin+"");





    }

    public void init(){
        viewpage = findViewById(R.id.viewPager2);
        indicator = findViewById(R.id.indicator);
        rv_km = findViewById(R.id.RV_km_mn);
        rv_dt = findViewById(R.id.rv_dt_nb);
        rv_laptop = findViewById(R.id.rv_laptop);
        rv_tablet = findViewById(R.id.rv_tablet);
        pro_nb = findViewById(R.id.pro_nb);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.topAppBar);
        // click item tao mau
        navigationView.bringToFront();
        //toolbar
        setSupportActionBar(toolbar);
        menu  = navigationView.getMenu();

        //hide or show item



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
         navigationView.setCheckedItem(R.id.nav_home);

         View hearderLayout = navigationView.getHeaderView(0);
         tv_hdname = hearderLayout.findViewById(R.id.hd_name);
         tv_hdll = hearderLayout.findViewById(R.id.hd_lienlac);



    }

    private void ShowMenu() {

        if (checkLogin == false){
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(true);
            menu.findItem(R.id.nav_dangki).setVisible(true);
            tv_hdname.setText("");
            tv_hdll.setText("");
        }
        else {
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_dangki).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
            tv_hdname.setText(sharedPref.getString("name","NoName"));
            tv_hdll.setText("Welcome!");

        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void getTablet(){
        mAPIService.gettablet().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                try {
                    for (int i = 0; i < response.body().size(); i++){
                        SanPham sp = new SanPham(response.body().get(i).getId(),
                                response.body().get(i).getTensp(), response.body().get(i).getGiasanpham(),
                                response.body().get(i).getHinhanhsp(), response.body().get(i).getMotasp(),
                                response.body().get(i).getRate(), response.body().get(i).getSl_rate(),
                                response.body().get(i).getUudai(), response.body().get(i).getDacbiet(),
                                response.body().get(i).getIdloaisp(), response.body().get(i).getLoaiSP());
                        listTB.add(sp);
                    }

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
    private void getLaptopNB(){
        mAPIService.getlaptopnb().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                try {
                    for (int i = 0; i < response.body().size(); i++){
                        SanPham sp = new SanPham(response.body().get(i).getId(),
                                response.body().get(i).getTensp(), response.body().get(i).getGiasanpham(),
                                response.body().get(i).getHinhanhsp(), response.body().get(i).getMotasp(),
                                response.body().get(i).getRate(), response.body().get(i).getSl_rate(),
                                response.body().get(i).getUudai(), response.body().get(i).getDacbiet(),
                                response.body().get(i).getIdloaisp(), response.body().get(i).getLoaiSP());
                        listLT.add(sp);
                    }

                }catch (Exception e){
                    Log.d("aaa", e.getMessage().toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }
        });
    }
    private  void getDTNB(){
        mAPIService.getdtnb().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                try {
                    for (int i = 0; i < response.body().size(); i++){
                        SanPham sp = new SanPham(response.body().get(i).getId(),
                                response.body().get(i).getTensp(), response.body().get(i).getGiasanpham(),
                                response.body().get(i).getHinhanhsp(), response.body().get(i).getMotasp(),
                                response.body().get(i).getRate(), response.body().get(i).getSl_rate(),
                                response.body().get(i).getUudai(), response.body().get(i).getDacbiet(),
                                response.body().get(i).getIdloaisp(), response.body().get(i).getLoaiSP());
                        listDT.add(sp);
                    }


                }catch (Exception e){
                    Log.d("aaa", e.getMessage().toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(MainActivity.this, "Error ", Toast.LENGTH_LONG).show();

            }
        });

    }
    private  void getSPKM(){

        mAPIService.getKMNgay().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                try {
                    for (int i = 0; i < response.body().size(); i++){
                        SanPham sp = new SanPham(response.body().get(i).getId(),
                                response.body().get(i).getTensp(), response.body().get(i).getGiasanpham(),
                                response.body().get(i).getHinhanhsp(), response.body().get(i).getMotasp(),
                                response.body().get(i).getRate(), response.body().get(i).getSl_rate(),
                                response.body().get(i).getUudai(), response.body().get(i).getDacbiet(),
                                response.body().get(i).getIdloaisp(), response.body().get(i).getLoaiSP());
                        listKM.add(sp);
                    }


                }catch (Exception e){
                    Log.d("aaa", e.getMessage().toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }
        });
    }
    private void getSlide(){
        SlideList = new ArrayList<>();
       mAPIService.getSilde().enqueue(new Callback<List<Slide>>() {
           @Override
           public void onResponse(Call<List<Slide>> call, Response<List<Slide>> response) {
               try {

                   String st ="";
                   for ( int i  =0 ; i < response.body().size(); i++){
                     Slide slide = new Slide(response.body().get(i).getHinhanh().toString(), response.body().get(i).getTitile().toString());
                     SlideList.add(slide);
                   }

                   SlideAdapter slideAdapter =new SlideAdapter(getApplicationContext(),SlideList);
                   viewpage.setAdapter(slideAdapter);
                   Timer timer = new Timer();
                   timer.scheduleAtFixedRate(new SliderTimer(), 4000,6000);
                   new TabLayoutMediator(indicator, viewpage,
                           new TabLayoutMediator.TabConfigurationStrategy() {
                               @Override
                               public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                               }
                           }
                   ).attach();
               }
               catch (Exception e){
                   Log.d("aaa", e.getMessage().toString());
                   e.printStackTrace();
               }



           }

           @Override
           public void onFailure(Call<List<Slide>> call, Throwable t) {
               Log.d("onFailure", t.toString());
           }
       });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSPClick(SanPham sv, ImageView imageView) {
        Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
      intent.putExtra("item_id", sv.getId());
      intent.putExtra("item_ten", sv.getTensp());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,imageView,"shareName");
        startActivity(intent, options.toBundle());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_dt:
                Intent intent = new Intent(MainActivity.this, DanhSachSPActivity.class);
                intent.putExtra("idloaisp", 1);
                startActivity(intent);
                break;
            case R.id.nav_laptop:
                Intent intent1 = new Intent(MainActivity.this, DanhSachSPActivity.class);
                intent1.putExtra("idloaisp", 2);
                startActivity(intent1);
                break;
            case R.id.nav_tablet:
                Intent intent2 = new Intent(MainActivity.this, DanhSachSPActivity.class);
                intent2.putExtra("idloaisp", 3);
                startActivity(intent2);
                break;
            case R.id.nav_dangki:
                Intent intent3 = new Intent(MainActivity.this, DangKyActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_login:
                Intent intent4 =new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent4, REQUEST_CODE);
                break;
            case  R.id.nav_profile:
                Intent intent5 = new Intent(MainActivity.this, GioHangActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_logout:
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("check", false);
                editor.commit();

                Intent refresh = new Intent(this, MainActivity.class);

                startActivity(refresh);
                this.finish();


                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_CODE ){
            if (resultCode == RESULT_OK){

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("check", true);
                editor.commit();

                ShowMenu();
                Intent refresh = new Intent(this, MainActivity.class);

                startActivity(refresh);

                Toast.makeText(MainActivity.this,  sharedPref.getBoolean("check",false)+" ", Toast.LENGTH_LONG).show();

            }
            else {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("check", false);
                editor.commit();
                ShowMenu();


            }

        }
    }

    class SliderTimer extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewpage.getCurrentItem()<SlideList.size()-1){
                        viewpage.setCurrentItem(viewpage.getCurrentItem()+1);
                    }
                    else viewpage.setCurrentItem(0);
                }
            });
        }
    }
}