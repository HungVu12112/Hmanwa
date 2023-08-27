package com.example.truyentranh_asmapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.activity.Detail_Comic;
import com.example.truyentranh_asmapp.models.Comics;

import java.lang.annotation.Target;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicAdapterHolder>{
    private List<Comics> mList;
    private Context mContext;

    public ComicAdapter(List<Comics> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ComicAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);
        return new ComicAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicAdapterHolder holder, int position) {
        Comics comics = mList.get(position);
        if (comics == null){
            return;
        }
        Glide.with(mContext).load("http://10.0.2.2:3000"+comics.getImg()).into(holder.img);
        holder.tvName.setText(comics.getName());
        holder.tvNameTg.setText(comics.getNametg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Detail_Comic.class);
                Bundle bundle = new Bundle();
                bundle.putString("object_idcomic",comics.get_id());
                bundle.putString("object_name", comics.getName());
                bundle.putString("object_img", comics.getImg());
                bundle.putString("object_des", comics.getDes());
                bundle.putString("object_nametg", comics.getNametg());
                bundle.putString("object_yearxb", comics.getYearxb());
                bundle.putStringArray("object_imgnd", comics.getImgnd());
                Log.d("test", String.valueOf(comics.getImgnd()));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList != null ){
            return mList.size();
        }
        return 0;
    }
    public void setData(List<Comics> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public class ComicAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvName, tvNameTg;
        public ComicAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvnamecomic);
            img = itemView.findViewById(R.id.imgcomic);
            tvNameTg = itemView.findViewById(R.id.tvnametg);
        }
    }
}
