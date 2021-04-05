package viettu.pvt.shopping_app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.Ultis.ApiUtils;
import viettu.pvt.shopping_app.Ultis.GioHangClick;
import viettu.pvt.shopping_app.api.ApiService;
import viettu.pvt.shopping_app.models.GioHang;
import viettu.pvt.shopping_app.models.SanPham;

public class GioHangAdapter  extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private Context context;
    private List<GioHang> list;
    GioHangClick gioHangClick;
    private ApiService mAPIService;
    SanPham sp;
    private List<SanPham> listsp;
    NumberFormat nf = NumberFormat.getInstance();

    public GioHangAdapter(Context context, List<GioHang> list, GioHangClick gioHangClick) {
        this.context = context;
        this.list = list;
        this.gioHangClick = gioHangClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return  new GioHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getSPById(list.get(position).getIdsanpham()).enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
               sp  =  new SanPham(response.body().get(0).getId(),
                        response.body().get(0).getTensp(), response.body().get(0).getGiasanpham(),
                        response.body().get(0).getHinhanhsp(), response.body().get(0).getMotasp(),
                        response.body().get(0).getRate(), response.body().get(0).getSl_rate(),
                        response.body().get(0).getUudai(), response.body().get(0).getDacbiet(),
                        response.body().get(0).getIdloaisp(), response.body().get(0).getLoaiSP());
                Picasso.get().load(sp.getHinhanhsp()).into(holder.img_sp);
                holder.tv_name.setText(sp.getTensp());
                int donggia = (int) (sp.getGiasanpham()- (int) sp.getUudai());
                holder.tv_dongia.setText(nf.format(donggia)+"");
                holder.tv_soluong.setText(list.get(position).getSoluong()+"");
                holder.tv_tongtien.setText(nf.format(list.get(position).getTongtien())+"");
                holder.img_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(context);
                        dialogxoa.setMessage("Bạn có muốn xoá sản phẩm này không ?");
                        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAPIService.delcart(list.get(position).getIdgiohang()).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_LONG).show();
                                        list.remove(list.get(position));
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                        dialogxoa.setNegativeButton("Cannel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        dialogxoa.show();
                    }
                });

                holder.btn_cong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int x = Integer.parseInt(holder.tv_soluong.getText().toString().trim());
                        x +=1;
                        if ( x > 1)  holder.btn_tru.setVisibility(View.VISIBLE);
                        if ( x >9){
                            x = 10;
                            holder.btn_cong.setVisibility(View.INVISIBLE);
                        }
                        else {
                            holder.btn_cong.setVisibility(View.VISIBLE);

                        }
                        holder.tv_soluong.setText(x+"");
                        list.get(position).setSoluong(x);

                        list.get(position).setTongtien(x *donggia);
                        holder.tv_soluong.setText(nf.format(list.get(position).getSoluong())+"");
                        holder.tv_tongtien.setText(nf.format(list.get(position).getTongtien())+"");

                    }
                });

                holder.btn_tru.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int y = Integer.parseInt(holder.tv_soluong.getText().toString().trim());
                        y -=1;
                        if ( y < 10)  holder.btn_cong.setVisibility(View.VISIBLE);
                        if ( y < 1){
                            y = 1;
                            holder.btn_tru.setVisibility(View.INVISIBLE);
                        }
                        else {
                            holder.btn_tru.setVisibility(View.VISIBLE);

                        }
                        holder.tv_soluong.setText(y+"");
                        list.get(position).setSoluong(y);

                        list.get(position).setTongtien(y *donggia);
                        holder.tv_soluong.setText(nf.format(list.get(position).getSoluong())+"");
                        holder.tv_tongtien.setText(nf.format(list.get(position).getTongtien())+"");
                    }
                });



            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_sp, img_del;
        TextView tv_name, tv_dongia, tv_soluong, tv_tongtien;
        Button btn_cong, btn_tru;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_sp = itemView.findViewById(R.id.gh_img);
            img_del = itemView.findViewById(R.id.gioahang_xoa);
            tv_name = itemView.findViewById(R.id.gh_tensp);
            tv_dongia = itemView.findViewById(R.id.gh_dongia);
            tv_soluong = itemView.findViewById(R.id.gh_soluong);
            tv_tongtien = itemView.findViewById(R.id.gh_tong);
            btn_cong = itemView.findViewById(R.id.gh_btncong);
            btn_tru = itemView.findViewById(R.id.gh_btntru);
            itemView.getTag();

        }

    }
}
