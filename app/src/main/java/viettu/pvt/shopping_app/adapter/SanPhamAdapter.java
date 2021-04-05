package viettu.pvt.shopping_app.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.Ultis.SanPhamItemClick;
import viettu.pvt.shopping_app.models.SanPham;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHodler>  {
    private Context context;
    private List<SanPham> list;
    SanPhamItemClick sanPhamItemClick;
    NumberFormat nf = NumberFormat.getInstance();

    public SanPhamAdapter(Context context, List<SanPham> list, SanPhamItemClick sanPhamItemClick) {
        this.context = context;
        this.list = list;
        this.sanPhamItemClick = sanPhamItemClick;
    }



    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return  new SanPhamAdapter.ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {

        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHodler extends RecyclerView.ViewHolder{
        private TextView tv_tragop, tv_tensp, tv_giacu, tv_giamoi, tv_danhgia;
        private ImageView img_sp, img_tet,img_sa1, img_sa2,img_sa3,img_sa4, img_sa5;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            tv_tragop = itemView.findViewById(R.id.sp_tragop);
            tv_giacu = itemView.findViewById(R.id.sp_giacu);
            tv_tensp = itemView.findViewById(R.id.sp_ten_item);
            tv_giamoi = itemView.findViewById(R.id.sp_giamoi);
            tv_danhgia = itemView.findViewById(R.id.sp_luot_danhgia);
            img_sp = itemView.findViewById(R.id.sp_img);
            img_tet = itemView.findViewById(R.id.sp_dacbiet);
            img_sa1 = itemView.findViewById(R.id.sp_star_1);
            img_sa2 = itemView.findViewById(R.id.sp_star_2);
            img_sa3 = itemView.findViewById(R.id.sp_star_3);
            img_sa4 = itemView.findViewById(R.id.sp_star_4);
            img_sa5 = itemView.findViewById(R.id.sp_star_5);
            img_sp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sanPhamItemClick.onSPClick(list.get(getAdapterPosition() ), img_sp);
                }
            });
        }

        public void bindView(int position) {
            tv_tensp.setText(list.get(position).getTensp());
            Picasso.get().load(list.get(position).getHinhanhsp()).into(img_sp);
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
