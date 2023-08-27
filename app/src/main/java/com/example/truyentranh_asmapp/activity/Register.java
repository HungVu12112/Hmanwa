package com.example.truyentranh_asmapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.api.ApiService;
import com.example.truyentranh_asmapp.models.User;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private ImageView back;
    private EditText edUsername, edPassword, edEmail, edFullname;
    private Button btnDangKi;
    private boolean isRegister;
    private TextView tvErrRegister;
    private String messRegisterRespon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back = findViewById(R.id.btnback8);
        edUsername = findViewById(R.id.edusername_reg);
        edPassword = findViewById(R.id.edpass_reg);
        edEmail = findViewById(R.id.edemail_reg);
        edFullname = findViewById(R.id.edfullname_reg);
        btnDangKi = findViewById(R.id.btn_regall);
        tvErrRegister = findViewById(R.id.signup_error_phone);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                String email = edEmail.getText().toString();
                String fullname = edFullname.getText().toString();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setFullname(fullname);




                RegisterUser(user);
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (validateRegister(username, password, email, fullname)) {
                    if (!email.matches(emailPattern)) {
                        tvErrRegister.setText("Định dạng email không chính xác !");
                        edEmail.setBackground(getDrawable(R.drawable.bg_error));
                        return;
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (isRegister) {
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    finish();
                                } else {
                                    edUsername.setBackground(getDrawable(R.drawable.bg_error));
                                    tvErrRegister.setText("Tài khoản đã có sẵn vui lòng đăng kí tài khoản khác !");
                                }
                            }
                        }, 1100);
                    }

                }
            }
        });
    }

    private void RegisterUser(User user) {
        ApiService.apiService.Register(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String messRegister = response.body().toString();
//                try {
//                    String chuoi = "{\"id\":5, \"isCheck\":\"true\"}";
//                    JSONObject  object= new JSONObject(messRegister);
//                    isRegister = (String) object.get("isCheck");
//                    messRegisterRespon = (String) object.get("msg");
//                    Log.d("ZZZZZ", (String) object.get("isCheck"));
//
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    String chuoi = "{\"id\":5, \"isCheck\":\"true\"}";
//                    JSONObject obj = new JSONObject(chuoi).get("isCheck");
//                    Log.d("ZZZZZ",obj.toString());
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
                isRegister = response.body().isCheck();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Register.this, "Call không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateRegister(String username, String password, String email, String fullname) {
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || fullname.isEmpty()) {
            edUsername.setBackground(getDrawable(R.drawable.bg_error));
            edPassword.setBackground(getDrawable(R.drawable.bg_error));
            edEmail.setBackground(getDrawable(R.drawable.bg_error));
            edFullname.setBackground(getDrawable(R.drawable.bg_error));
            tvErrRegister.setText("Vui lòng không bỏ trống thông tin");
            return false;
        }
        edUsername.setBackground(getDrawable(R.drawable.textlogin));
        edPassword.setBackground(getDrawable(R.drawable.textlogin));
        edEmail.setBackground(getDrawable(R.drawable.textlogin));
        edFullname.setBackground(getDrawable(R.drawable.textlogin));
        tvErrRegister.setText("");
        return true;
    }
}