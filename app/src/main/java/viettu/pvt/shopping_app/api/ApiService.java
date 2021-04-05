package viettu.pvt.shopping_app.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import viettu.pvt.shopping_app.models.GioHang;
import viettu.pvt.shopping_app.models.KhachHang;
import viettu.pvt.shopping_app.models.SanPham;
import viettu.pvt.shopping_app.models.Slide;

public interface ApiService {


    @GET("/shopapp/getSlide.php")
    Call<List<Slide>> getSilde();

    @GET("/shopapp/getkmngay.php")
    Call<List<SanPham>> getKMNgay();

    @GET("/shopapp/getdtnb.php")
    Call<List<SanPham>> getdtnb();

    @GET("/shopapp/getlaptopnb.php")
    Call<List<SanPham>> getlaptopnb();

    @GET("/shopapp/gettablet.php")
    Call<List<SanPham>> gettablet();

    @GET("/shopapp/getsanphanbyid.php")
    Call<List<SanPham>> getSPById(@Query("id") int id);

    @GET("/shopapp/getdanhsachsanpham.php")
    Call<List<SanPham>> getDanhSachSanPham(@Query("idloaisp") int idloaisp);

    @GET("/shopapp/checklogin.php")
    Call<List<KhachHang>> checkLogin(@Query("tendangnhap") String tendangnhap, @Query("password") String password);

    @FormUrlEncoded
    @POST("/shopapp/dangki.php")
    Call<ResponseBody> dangKi(@Field("tendangnhap")String name, @Field("password") String pass, @Field("sdt") String sdt, @Field("diachi") String diachi);

    @FormUrlEncoded
    @POST("/shopapp/addcart.php")
    Call<ResponseBody> addCart(@Field("id") int id, @Field("idsanpham") int idsanpham, @Field("soluong") int soluong, @Field("tongtien") int tongtien, @Field("trangthai") int trangthai);

    @GET("/shopapp/listcart.php")
    Call<List<GioHang>> getlistCart( @Query("id") int id);
    @FormUrlEncoded
    @POST("/shopapp/delcart.php")
    Call<ResponseBody> delcart(@Field("idgiohang") int idgihang);

    @FormUrlEncoded
    @POST("/shopapp/updatecart.php")
    Call<ResponseBody> uppdatecart(@Field("idgiohang") int idgiohang, @Field("soluong") int soluong, @Field("tongtien") int tongtien);

    @FormUrlEncoded
    @POST("/shopapp/updatethanhtoan.php")
    Call<ResponseBody> uppdatethanhtoan(@Field("idgiohang") int idgiohang, @Field("soluong") int soluong, @Field("tongtien") int tongtien, @Field("trangthai") int trangthai);
}
