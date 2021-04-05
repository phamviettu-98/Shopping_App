package viettu.pvt.shopping_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SanPham {
    @SerializedName("id")
    @Expose
    private  int id;
    @SerializedName("tensp")
    @Expose
    private String tensp;
    @SerializedName("giasanpham")
    @Expose
    private int giasanpham;
    @SerializedName("hinhanhsp")
    @Expose
    private String hinhanhsp;
    @SerializedName("motasp")
    @Expose
    private String motasp;
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("sl_rate")
    @Expose
    private  int sl_rate;
    @SerializedName("uudai")
    @Expose
    private float uudai;
    @SerializedName("dacbiet")
    @Expose
    private  int dacbiet;
    @SerializedName("idloaisp")
    @Expose
    private  int idloaisp;
    @SerializedName("loaiSP")
    @Expose
    private  int loaiSP;

    public SanPham() {
    }

    public SanPham(int id, String tensp, int giasanpham, String hinhanhsp, String motasp, float rate, int sl_rate, float uudai, int dacbiet, int idloaisp, int loaiSP) {
        this.id = id;
        this.tensp = tensp;
        this.giasanpham = giasanpham;
        this.hinhanhsp = hinhanhsp;
        this.motasp = motasp;
        this.rate = rate;
        this.sl_rate = sl_rate;
        this.uudai = uudai;
        this.dacbiet = dacbiet;
        this.idloaisp = idloaisp;
        this.loaiSP = loaiSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getSl_rate() {
        return sl_rate;
    }

    public void setSl_rate(int sl_rate) {
        this.sl_rate = sl_rate;
    }

    public float getUudai() {
        return uudai;
    }

    public void setUudai(float uudai) {
        this.uudai = uudai;
    }

    public int getDacbiet() {
        return dacbiet;
    }

    public void setDacbiet(int dacbiet) {
        this.dacbiet = dacbiet;
    }

    public int getIdloaisp() {
        return idloaisp;
    }

    public void setIdloaisp(int idloaisp) {
        this.idloaisp = idloaisp;
    }

    public int getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(int loaiSP) {
        this.loaiSP = loaiSP;
    }
}
