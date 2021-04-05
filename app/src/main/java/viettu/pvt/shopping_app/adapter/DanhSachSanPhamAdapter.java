package viettu.pvt.shopping_app.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Callback;
import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.Ultis.SanPhamClick;
import viettu.pvt.shopping_app.Ultis.SanPhamItemClick;
import viettu.pvt.shopping_app.activities.DanhSachSPActivity;
import viettu.pvt.shopping_app.activities.GioHangActivity;
import viettu.pvt.shopping_app.activities.ItemDetailActivity;
import viettu.pvt.shopping_app.activities.MainActivity;
import viettu.pvt.shopping_app.models.SanPham;

public class DanhSachSanPhamAdapter extends RecyclerView.Adapter<DanhSachSanPhamAdapter.ViewHolder>   {

    private Context context;
    private List<SanPham> list;
    SanPhamClick sanPhamItemClick;
    NumberFormat nf = NumberFormat.getInstance();

    public DanhSachSanPhamAdapter(Context context, List<SanPham> list, SanPhamClick sanPhamItemClick) {
        this.context = context;
        this.list = list;
        this.sanPhamItemClick = sanPhamItemClick;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ds, parent, false);
        return  new DanhSachSanPhamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                 holder.bindView(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
       private ImageView ds_img, img_sa1, img_sa2,img_sa3,img_sa4, img_sa5;
        private TextView tv_tensp, tv_giacu, tv_giamoi, tv_danhgia;
        private Button btnxemchitiet, btnAddCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ds_img = itemView.findViewById(R.id.ds_img);
            img_sa1 = itemView.findViewById(R.id.ds_s1);
            img_sa2 = itemView.findViewById(R.id.ds_s2);
            img_sa3 = itemView.findViewById(R.id.ds_s3);
            img_sa4 = itemView.findViewById(R.id.ds_s4);
            img_sa5 = itemView.findViewById(R.id.ds_s5);
            tv_tensp = itemView.findViewById(R.id.ds_tvTen);
            tv_danhgia = itemView.findViewById(R.id.ds_sosao);
            tv_giacu = itemView.findViewById(R.id.ds_giacu);
            tv_giamoi = itemView.findViewById(R.id.ds_giamoi);
            btnxemchitiet = itemView.findViewById(R.id.btnXemChiTiet);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);
            ds_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sanPhamItemClick.SanPhamClick(list.get(getAdapterPosition()), ds_img);
                }
            });
            btnxemchitiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sanPhamItemClick.SanPhamClickView(list.get(getAdapterPosition()));


                }
            });
            btnAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sanPhamItemClick.SanPhamClickAdd(list.get(getAdapterPosition()));
                }
            });




        }

        public void bindView(int position) {
            tv_tensp.setText(list.get(position).getTensp());
            Picasso.get().load(list.get(position).getHinhanhsp()).into(ds_img);
            tv_giacu.setText(nf.format(list.get(position).getGiasanpham())+" ₫");
            tv_giacu.setPaintFlags(tv_giacu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            int giacu = (int) (list.get(position).getGiasanpham()- list.get(position).getUudai());
            tv_giamoi.setText(nf.format(giacu) +" ₫");
            tv_danhgia.setText(list.get(position).getSl_rate()+" đánh giá.");
            float x = list.get(position).getRate();
            if ( x == 5) {
                img_sa1.setImageResource(R.drawable.ic_start);
                img_sa2.setImageResource(R.drawable.ic_start);
                img_sa3.setImageResource(R.drawable.ic_start);
                img_sa4.setImageResource(R.drawable.ic_start);
                img_sa5.setImageResource(R.drawable.ic_start);
            }
            else if ( 4 <x && x < 5 ){
                img_sa1.setImageResource(R.drawable.ic_start);
                img_sa2.setImageResource(R.drawable.ic_start);
                img_sa3.setImageResource(R.drawable.ic_start);
                img_sa4.setImageResource(R.drawable.ic_start);
                img_sa5.setImageResource(R.drawable.ic_star_half);
            } else if ( x == 4){
                img_sa1.setImageResource(R.drawable.ic_start);
                img_sa2.setImageResource(R.drawable.ic_start);
                img_sa3.setImageResource(R.drawable.ic_start);
                img_sa4.setImageResource(R.drawable.ic_start);
                img_sa5.setImageResource(R.drawable.ic_start_hide);
            } else if ( 3 < x && x < 4){
                img_sa1.setImageResource(R.drawable.ic_start);
                img_sa2.setImageResource(R.drawable.ic_start);
                img_sa3.setImageResource(R.drawable.ic_start);
                img_sa4.setImageResource(R.drawable.ic_star_half);
                img_sa5.setImageResource(R.drawable.ic_start_hide);
            }else if (x == 3){
                img_sa1.setImageResource(R.drawable.ic_start);
                img_sa2.setImageResource(R.drawable.ic_start);
                img_sa3.setImageResource(R.drawable.ic_start);
                img_sa4.setImageResource(R.drawable.ic_start_hide);
                img_sa5.setImageResource(R.drawable.ic_start_hide);
            }
            else  if ( 2 <x && x < 3){
                img_sa1.setImageResource(R.drawable.ic_start);
                img_sa2.setImageResource(R.drawable.ic_start);
                img_sa3.setImageResource(R.drawable.ic_star_half);
                img_sa4.setImageResource(R.drawable.ic_start_hide);
                img_sa5.setImageResource(R.drawable.ic_start_hide);
            }
            else if ( x == 2){
                img_sa1.setImageResource(R.drawable.ic_start);
                img_sa2.setImageResource(R.drawable.ic_start);
                img_sa3.setImageResource(R.drawable.ic_start_hide);
                img_sa4.setImageResource(R.drawable.ic_start_hide);
                img_sa5.setImageResource(R.drawable.ic_start_hide);
            }else  if ( x > 1 && x < 2){
                img_sa1.setImageResource(R.drawable.ic_start);
                img_sa2.setImageResource(R.drawable.ic_star_half);
                img_sa3.setImageResource(R.drawable.ic_start_hide);
                img_sa4.setImageResource(R.drawable.ic_start_hide);
                img_sa5.setImageResource(R.drawable.ic_start_hide);
            }else if  (x== 1){
                img_sa1.setImageResource(R.drawable.ic_start);
                img_sa2.setImageResource(R.drawable.ic_start_hide);
                img_sa3.setImageResource(R.drawable.ic_start_hide);
                img_sa4.setImageResource(R.drawable.ic_start_hide);
                img_sa5.setImageResource(R.drawable.ic_start_hide);
            }else if ( x> 0 && x < 1){
                img_sa1.setImageResource(R.drawable.ic_star_half);
                img_sa2.setImageResource(R.drawable.ic_start_hide);
                img_sa3.setImageResource(R.drawable.ic_start_hide);
                img_sa4.setImageResource(R.drawable.ic_start_hide);
                img_sa5.setImageResource(R.drawable.ic_start_hide);
            } else{
                img_sa1.setImageResource(R.drawable.ic_start_hide);
                img_sa2.setImageResource(R.drawable.ic_start_hide);
                img_sa3.setImageResource(R.drawable.ic_start_hide);
                img_sa4.setImageResource(R.drawable.ic_start_hide);
                img_sa5.setImageResource(R.drawable.ic_start_hide);
            }



        }
    }
}
