package com.example.truyentranh_asmapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.models.PhotoComic;

import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ReadViewHoder>{
    private Context mContext;
    private List<PhotoComic> mPhotoComicList;

    public ReadAdapter(Context mContext, List<PhotoComic> mPhotoComicList) {
        this.mContext = mContext;
        this.mPhotoComicList = mPhotoComicList;
    }

    @NonNull
    @Override
    public ReadViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_readcomic,parent,false);
        return new ReadViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadViewHoder holder, int position) {
        PhotoComic photoComic = mPhotoComicList.get(position);
        if (photoComic == null){
            return;
        }
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Log.d("anh",photoComic.getImgnd());
        Glide.with(mContext).load("http://10.0.2.2:3000"+photoComic.getImgnd()).apply(requestOptions).into(holder.imgRead_comic);
    }

    @Override
    public int getItemCount() {
        if (mPhotoComicList != null){
           return mPhotoComicList.size();
        }
        return 0;
    }
    public void setData(List<PhotoComic> mPhotoComicList){
        this.mPhotoComicList = mPhotoComicList;
        notifyDataSetChanged();
    }

    public class ReadViewHoder extends RecyclerView.ViewHolder {
        private ImageView imgRead_comic;
        public ReadViewHoder(@NonNull View itemView) {
            super(itemView);
            imgRead_comic = itemView.findViewById(R.id.imgRead_comic);
        }
    }
}
