package viettu.pvt.shopping_app.Ultis;

import viettu.pvt.shopping_app.api.ApiService;
import viettu.pvt.shopping_app.api.RetrofitClient;

public class ApiUtils {
    public static final String BASE_URL ="http://192.168.122.2";
    private ApiUtils(){

    }
    public static ApiService getAPIService(){
        return  RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
