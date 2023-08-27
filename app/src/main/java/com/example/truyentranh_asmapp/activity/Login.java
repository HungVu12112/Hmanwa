package com.example.truyentranh_asmapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truyentranh_asmapp.MainActivity;
import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.api.ApiService;
import com.example.truyentranh_asmapp.models.MessegerUser;
import com.example.truyentranh_asmapp.models.User;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private TextView tvErrUsername,tvErrPassWord,tvDangki;
    private EditText edUsername, edPassWord;
    private Button btnDangNhap;
    private ProgressDialog mProgressDialog;
    private boolean isCheck = false;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private List<User> mUser;
    private User user;
    private CheckBox btnSaveLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername = findViewById(R.id.edusername);
        edPassWord = findViewById(R.id.edpassword);
        btnDangNhap = findViewById(R.id.btn_Login);
        tvErrUsername = findViewById(R.id.login_error_username);
        tvErrPassWord = findViewById(R.id.login_error_password);
        tvDangki = findViewById(R.id.tvDangKi);
        mProgressDialog = new ProgressDialog(Login.this);
        mProgressDialog.setTitle("Đang đăng nhập !");
        mProgressDialog.setMessage("Vui lòng đợi xíu ...");

        btnSaveLogin = findViewById(R.id.login_chk);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            edUsername.setText(loginPreferences.getString("username", ""));
            edPassWord.setText(loginPreferences.getString("password", ""));
            btnSaveLogin.setChecked(true);
        }
        ////save Login

        tvDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassWord.getText().toString();
                boolean validate =validateLogin(username,password);
                LoginApp(username,password);
                mProgressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (validateLogin(username,password)){
                        if (isCheck){
                            if (btnSaveLogin.isChecked()) {
                                loginPrefsEditor.putBoolean("saveLogin", true);
                                loginPrefsEditor.putString("username", username);
                                loginPrefsEditor.putString("password", password);
                                loginPrefsEditor.commit();
                            } else {
                                loginPrefsEditor.clear();
                                loginPrefsEditor.commit();
                            }
                            for (User user : mUser){
//                                Toast.makeText(Login.this, user.get_id(), Toast.LENGTH_LONG).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("idUser",user.get_id());
                                editor.putString("username",user.getUsername());
                                editor.putString("fullname",user.getFullname());
                                editor.putString("password",user.getPassword());
                                editor.putString("email",user.getEmail());
                                editor.apply();

                            }
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else {
                            loginPrefsEditor.clear();
                            loginPrefsEditor.commit();
                            mProgressDialog.dismiss();
                            edUsername.setBackground(getDrawable(R.drawable.bg_error));
                            edPassWord.setBackground(getDrawable(R.drawable.bg_error));
                            tvErrPassWord.setText("Tài khoản và mật khẩu không chính xác !");
                        }
                    }
                        else {
                            mProgressDialog.dismiss();
                        }
                    }
                },1100);

            }
        });
    }
    private void LoginApp(String username , String password){
        ApiService.apiService.Login(username,password).enqueue(new Callback<MessegerUser>() {
            @Override
            public void onResponse(Call<MessegerUser> call, Response<MessegerUser> response) {
                isCheck = response.body().getCheckLogin();
                mUser = Arrays.asList(response.body().getObjectUser());
            }

            @Override
            public void onFailure(Call<MessegerUser> call, Throwable t) {
                Toast.makeText(Login.this, "Call không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateLogin(String username , String password){
        if (username.isEmpty() || password.isEmpty()){
            edUsername.setBackground(getDrawable(R.drawable.bg_error));
            edPassWord.setBackground(getDrawable(R.drawable.bg_error));
            tvErrPassWord.setText("Vui lòng điền đầy đủ thông tin tài khoản mật khẩu");
            return false;
        }

        tvErrPassWord.setText("");
        edUsername.setBackground(getDrawable(R.drawable.textlogin));
        edUsername.setBackground(getDrawable(R.drawable.textlogin));
        return true;
    }
}