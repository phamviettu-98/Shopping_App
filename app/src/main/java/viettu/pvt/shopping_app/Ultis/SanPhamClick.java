package viettu.pvt.shopping_app.Ultis;

import android.widget.ImageView;

import viettu.pvt.shopping_app.models.SanPham;

public interface SanPhamClick {
    void SanPhamClick(SanPham sp, ImageView img);
    void SanPhamClickView(SanPham sp);
    void  SanPhamClickAdd(SanPham sp);
}
