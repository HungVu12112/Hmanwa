
package com.example.truyentranh_asmapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.adapter.CommentAdaper;
import com.example.truyentranh_asmapp.adapter.InterfaceLoad;
import com.example.truyentranh_asmapp.adapter.NotifyConfig;
import com.example.truyentranh_asmapp.api.ApiService;
import com.example.truyentranh_asmapp.models.Comment;
import com.example.truyentranh_asmapp.models.MessComment;
import com.example.truyentranh_asmapp.models.PhotoComic;
import com.example.truyentranh_asmapp.models.User;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Comic extends AppCompatActivity implements InterfaceLoad {
    private ImageView imgBanner,itemBack,imgSaveFavoite;
    private Button btnRead;
    private RecyclerView recyclerViewComment;
    private TextView tvName,tvNameTg,tvNamXB,tvDes;
    private EditText edComment;
    User userList = new User();
    private String name;
    List<MessComment> mCommentList = new ArrayList<>();
    private CommentAdaper mCommentAdaper;
    private boolean isFa;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://10.0.2.2:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private ImageView imgSendComment;
    private ArrayList<PhotoComic> comics = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);


        /////
        tvName = findViewById(R.id.tvitemName_comic);
        tvNameTg = findViewById(R.id.tvitemNametg_comic);
        tvNamXB = findViewById(R.id.tvitemNamxb_comic);
        tvDes = findViewById(R.id.tvitemDes_comic);
        imgBanner = findViewById(R.id.imgitembanner_comic);
        btnRead = findViewById(R.id.imgitemRead_comic);
        recyclerViewComment = findViewById(R.id.recyccelViewComent);
        itemBack = findViewById(R.id.itemDetail_back);
        edComment = findViewById(R.id.edComment);
        imgSendComment = findViewById(R.id.imgSendComment);
        imgSaveFavoite = findViewById(R.id.savefavorite);


        //////

        ///////




        itemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("object_idcomic");
        name = bundle.getString("object_name");
        String nametg = bundle.getString("object_nametg");
        String img = bundle.getString("object_img");
        String des = bundle.getString("object_des");
        String namxb = bundle.getString("object_yearxb");
        String imgnd[] = bundle.getStringArray("object_imgnd");
        tvName.setText(name);
        tvNameTg.setText(nametg);
        tvDes.setText(des);
        tvNamXB.setText(namxb);
        Glide.with(getApplicationContext()).load("http://10.0.2.2:3000"+img).into(imgBanner);
        ////Xủ lý comment

        imgSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                String idUser = sharedPreferences.getString("idUser","");
                String Nameuser = sharedPreferences.getString("fullname","");
                //lấy thời gian thực
                String time = new SimpleDateFormat("HH:mm-dd/MM", Locale.getDefault()).format(new Date());
                Log.d("time",time);
                String comment = edComment.getText().toString();
                CallAddComment(time,id,idUser,comment);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CallgetComment(id);

                    }
                },700);

                if(comment.length()<1){
                    Toast.makeText(getApplicationContext(),
                            "Chưa nhập nội dung", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSocket.emit("new comment",comment  );
                    }
                },500);
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReadComic.class);
                intent.putExtra("object_imgnd1",imgnd);
                startActivity(intent);

                finish();
            }
        });
        imgSaveFavoite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Danh sách yêu thích !");
                builder.setIcon(R.drawable.baseline_favorite_24);
                builder.setMessage("Bạn có muốn lưu vào danh sách yêu thích không ! ");
                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /// xử lý lưu trong này
                        Toast.makeText(Detail_Comic.this, "Lưu", Toast.LENGTH_SHORT).show();
                        AddFavorite(id,true);
                    }
                });
                builder.setNegativeButton("Bỏ lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///Xử lý bỏ lưu
                        AddFavorite(id, false);
                        Toast.makeText(Detail_Comic.this, "Không Lưu", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });


        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewComment.setLayoutManager(layoutManager);
        CallgetComment(id);
        Log.d("mLis" , mCommentList.toString());

        mSocket.connect();
        // lắng nghe su kien
        mSocket.on("new comment", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Detail_Comic.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String data_sv_send = (String) args[0];

                        // hiện notify lên status
                        postNotify("Thông báo từ server", data_sv_send );


                    }
                });






            }
        });


    }
    private void CallAddComment(String timeComment , String idTruyen , String idUser ,String Content){
        ApiService.apiService.AddComment(timeComment,idTruyen,idUser,Content).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(Detail_Comic.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    @Override
    public void CallgetComment(String idTruyen) {
        ApiService.apiService.getComment(idTruyen).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                mCommentList = Arrays.asList(response.body().getListcomment());
                mCommentAdaper = new CommentAdaper(getApplicationContext(),mCommentList,Detail_Comic.this);
                recyclerViewComment.setAdapter(mCommentAdaper);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.d("fall", t.getMessage());
                Toast.makeText(Detail_Comic.this, "Call không thành công"+  t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void AddFavorite(String idTruyen , Boolean status){
        ApiService.apiService.addFavorite(idTruyen,status).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Call không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void postNotify(String title, String content){
        // Khởi tạo layout cho Notify
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        String fullname = sharedPreferences.getString("fullname","");
        Notification customNotification = new NotificationCompat.Builder(Detail_Comic.this, NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.star_big_on)
                .setContentTitle( title )
                .setContentText(fullname+" "+content + " " + name)
                .setAutoCancel(true)
                .build();
        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Detail_Comic.this);

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(Detail_Comic.this,
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(Detail_Comic.this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
            Toast.makeText(Detail_Comic.this, "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime();// lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy , customNotification);

    }
}