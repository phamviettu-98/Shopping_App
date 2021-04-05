package viettu.pvt.shopping_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GioHang {
    @SerializedName("idgiohang")
    @Expose
    private int idgiohang;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("idsanpham")
    @Expose
    private int idsanpham;
    @SerializedName("soluong")
    @Expose
    private int soluong;
    @SerializedName("tongtien")
    @Expose
    private int tongtien;
    @SerializedName("trangthai")
    @Expose
    private int trangthai;

    public GioHang() {
    }

    public GioHang(int idgiohang, int id, int idsanpham, int soluong, int tongtien, int trangthai) {
        this.idgiohang = idgiohang;
        this.id = id;
        this.idsanpham = idsanpham;
        this.soluong = soluong;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public int getIdgiohang() {
        return idgiohang;
    }

    public void setIdgiohang(int idgiohang) {
        this.idgiohang = idgiohang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
