package viettu.pvt.shopping_app.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private  static Retrofit retrofit = null;
    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();
    public static  Retrofit getClient(String baseurl) {
        if (retrofit == null) {
        retrofit = new Retrofit.Builder().baseUrl(baseurl)
                .client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create())

                .build();
        }
        return retrofit;
    }
}
