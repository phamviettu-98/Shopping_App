package viettu.pvt.shopping_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KhachHang {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("tendangnhap")
    @Expose
    private String tendangnhap;

    @SerializedName("password")
    @Expose
    private  String password;
    @SerializedName("sdt")
    @Expose
    private String sdt;
    @SerializedName("diachi")
    @Expose
    private String diachi;

    public KhachHang() {
    }

    public KhachHang(int id, String tendangnhap, String password, String sdt, String diachi) {
        this.id = id;
        this.tendangnhap = tendangnhap;
        this.password = password;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
