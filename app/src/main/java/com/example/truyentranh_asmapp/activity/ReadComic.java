package com.example.truyentranh_asmapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.adapter.ReadAdapter;
import com.example.truyentranh_asmapp.models.PhotoComic;

import java.util.ArrayList;
import java.util.List;

public class ReadComic extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String[] imgnd;
    private String img;
    private ReadAdapter readAdapter;
    private ArrayList<PhotoComic> comics = new ArrayList<PhotoComic>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);
        recyclerView = findViewById(R.id.recylerViewComic);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        Bundle bundle = getIntent().getExtras();



        imgnd = bundle.getStringArray("object_imgnd1");
        Log.d("==============", "onCreate:hehe "+imgnd);
        for (int i = 0; i < imgnd.length; i++) {
            comics.add(new PhotoComic(imgnd[i]));
        }
        readAdapter = new ReadAdapter(getApplicationContext(), comics);
        readAdapter.setData(comics);
        recyclerView.setAdapter(readAdapter);

    }

    private void ReadData() {
        Bundle bundle = getIntent().getExtras();
        imgnd = bundle.getStringArray("object_imgnd1");

        for (int i = 0; i <= imgnd.length; i++) {
//            List<PhotoComic> list = new ArrayList<>();
//            PhotoComic photoComic = imgnd[];
//            photoComic.setImgnd(imgnd[i]);
//            comics.add(new PhotoComic(imgnd[]));
//            Log.d("URL",imgnd[i]);

        }


        readAdapter = new ReadAdapter(getApplicationContext(), comics);
        readAdapter.setData(comics);
        recyclerView.setAdapter(readAdapter);


    }
}