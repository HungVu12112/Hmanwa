package com.example.truyentranh_asmapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.activity.ChangerPassword;
import com.example.truyentranh_asmapp.activity.ChangerProfile;
import com.example.truyentranh_asmapp.activity.Login;
import com.example.truyentranh_asmapp.activity.Register;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private LinearLayout btnChangerProfile,btnChangerPass,btnLogout;
    private TextView tvnameAccount,tvEmailAccount;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnChangerProfile = view.findViewById(R.id.btnchangeProfile);
        btnChangerPass = view.findViewById(R.id.btnchangepass);
        tvnameAccount = view.findViewById(R.id.tvnameaccount);
        tvEmailAccount = view.findViewById(R.id.tvEmailAccount);
        btnLogout = view.findViewById(R.id.btnlogoutTK);

        //////
        getData();
        ///////


        btnChangerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChangerProfile.class));
            }
        });
        btnChangerPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChangerPassword.class));
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Login.class);
                startActivity(intent);
                getActivity().finish();
                SharedPreferences settings = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
    private void getData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login",getContext().MODE_PRIVATE);
        String fullname = sharedPreferences.getString("fullname","");
        String email = sharedPreferences.getString("email","");
        tvnameAccount.setText(fullname);
        tvEmailAccount.setText(email);
    }
}