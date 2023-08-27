package com.example.truyentranh_asmapp.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.api.ApiService;
import com.example.truyentranh_asmapp.models.Comment;
import com.example.truyentranh_asmapp.models.MessComment;
import com.example.truyentranh_asmapp.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdaper extends RecyclerView.Adapter<CommentAdaper.CommentViewHolder> {
    private Context mContext;
    private List<MessComment> mCommentList;
    private InterfaceLoad interfaceLoad;

    public CommentAdaper(Context mContext, List<MessComment> mCommentList, InterfaceLoad interfaceLoad) {
        this.mContext = mContext;
        this.mCommentList = mCommentList;
        this.interfaceLoad = interfaceLoad;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment ,parent , false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        MessComment messComment = mCommentList.get(position);
        if (messComment == null){
            return;
        }
        holder.tvusername.setText(messComment.getId_use().getFullname());
        holder.tvcontent.setText(messComment.getContent());
        holder.tvtime.setText(messComment.getComment_time());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /// Xóa commnent
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Thông báo !");
                builder.setMessage("Bạn đã chắc chắn với quyết định này chưa ! ");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /// xử lý xóa trong này
                        CalldeleteComment(messComment.get_id());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                interfaceLoad.CallgetComment(messComment.getId_truyen().get_id());
                            }
                        },500);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///Xử lý cancel
                    }
                });
                builder.show();

                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.item_update_comment);
                dialog.create();
                dialog.getWindow().setLayout(1000,300);
                EditText edUpdateNew = dialog.findViewById(R.id.edcontentnews);
                Button btnUpdate = dialog.findViewById(R.id.btnupdatcomment);
                Button btnCancel = dialog.findViewById(R.id.btncancelcomment);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("login",mContext.MODE_PRIVATE);
                        String idUser = sharedPreferences.getString("idUser","");
                        String edupdate = edUpdateNew.getText().toString();
                        String idTruyen = messComment.getId_truyen().get_id();
                        String time = new SimpleDateFormat("HH:mm-dd/MM", Locale.getDefault()).format(new Date());
                        CallUpdateComment(messComment.get_id(),time,idTruyen,idUser,edupdate);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                interfaceLoad.CallgetComment(idTruyen);
                            }
                        },500);
                        dialog.cancel();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

    }
    public void setData(List<MessComment> mCommentList){
        this.mCommentList = mCommentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCommentList != null){
            return mCommentList.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvusername, tvcontent, tvtime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvusername = itemView.findViewById(R.id.tvnameUserComment);
            tvcontent = itemView.findViewById(R.id.tvcontentComment);
            tvtime = itemView.findViewById(R.id.tvtimeComment);
        }
    }
    private void CallUpdateComment(String idcomment,String time,String idTruyen,String idUser,String content){
        ApiService.apiService.updateComment(idcomment,time,idTruyen,idUser,content).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(mContext, "Call không thành công" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void CalldeleteComment(String idTruyen){
        ApiService.apiService.deleteComment(idTruyen).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(mContext, "Call không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
