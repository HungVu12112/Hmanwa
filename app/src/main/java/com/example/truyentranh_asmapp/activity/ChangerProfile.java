package com.example.truyentranh_asmapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.api.ApiService;
import com.example.truyentranh_asmapp.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangerProfile extends AppCompatActivity {
    private ImageView back;
    private EditText edFullname,edEmail;
    private Button btnUpdate;
    private String fullname,email;
    private boolean isCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changer_profile);
        back = findViewById(R.id.btnback3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        edFullname = findViewById(R.id.profile_fullname);
        edEmail = findViewById(R.id.profile_email);
        btnUpdate = findViewById(R.id.profile_btn_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                String idUser = sharedPreferences.getString("idUser","");
                fullname = edFullname.getText().toString();
                email = edEmail.getText().toString();
                User user = new User();
                user.setFullname(fullname);
                user.setEmail(email);
                ///
                updateInfo(idUser,user);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isCheck){
                            SharedPreferences sharedPreferences1 = getSharedPreferences("login",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            editor.putString("fullname",fullname);
                            editor.putString("email",email);
                            editor.apply();
                            Toast.makeText(ChangerProfile.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                },1500);
            }
        });



    }
    private void updateInfo(String idUser , User user){
        ApiService.apiService.updateUser(idUser,user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                isCheck = response.body().isCheck();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ChangerProfile.this, "Call không thành công ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}