package viettu.pvt.shopping_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slide {
    @SerializedName("hinhanh")
    @Expose
    private  String hinhanh;
    @SerializedName("titile")
    @Expose
    private String titile;

    public Slide() {
    }

    public Slide(String hinhanh, String titile) {
        this.hinhanh = hinhanh;
        this.titile = titile;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }
}
