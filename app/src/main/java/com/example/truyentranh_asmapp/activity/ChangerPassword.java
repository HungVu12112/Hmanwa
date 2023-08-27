package com.example.truyentranh_asmapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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

public class ChangerPassword extends AppCompatActivity {
    private ImageView back;
    private EditText edPassWord,edPasswordNew,edPasswordNew1;
    private Button btnChangPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changer_password);
        ///////
        back = findViewById(R.id.btnback2);
        edPassWord = findViewById(R.id.edchange_password);
        edPasswordNew = findViewById(R.id.edchange_newpassword);
        edPasswordNew1 = findViewById(R.id.edchange_newpassword2);
        btnChangPass = findViewById(R.id.btnchange_button);
        ////////
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnChangPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edPassWord.getText().toString();
                String newpassword = edPasswordNew.getText().toString();
                String newpassword1  = edPasswordNew1.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                String username = sharedPreferences.getString("username","");
                if (validate(password,newpassword,newpassword1)){
                    if (newpassword.equals(newpassword1)){
                        ChangerPass(username,password,newpassword);
                    }
                    else {
                        Toast.makeText(ChangerPassword.this, "Mật khẩu xác thực không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private void ChangerPass(String username ,String password ,String newpassword){
        ApiService.apiService.changerPass(username,password,newpassword).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(ChangerPassword.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ChangerPassword.this, "Call không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validate(String username ,String password ,String newpassword){
        if(username.isEmpty()||password.isEmpty()||newpassword.isEmpty()){
            Toast.makeText(this, "Vui lòng viết đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}