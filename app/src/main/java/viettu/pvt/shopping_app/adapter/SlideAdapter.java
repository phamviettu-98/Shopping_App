package viettu.pvt.shopping_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import viettu.pvt.shopping_app.R;
import viettu.pvt.shopping_app.models.Slide;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.ViewHolder> {
    private Context context;
    private List<Slide> list;

    public SlideAdapter(Context context, List<Slide> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_view,parent,false);
        return new ViewHolder(view);
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
        private ImageView imageslide;
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageslide = itemView.findViewById(R.id.slide_image);
            title = itemView.findViewById(R.id.tv_slide);
        }

        public void bindView(int position) {
            Picasso.get().load(list.get(position).getHinhanh()).into(imageslide);
            title.setText(list.get(position).getTitile());
        }
    }

}
