package com.example.truyentranh_asmapp.fragment;

import android.app.Notification;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.adapter.ComicAdapter;
import com.example.truyentranh_asmapp.adapter.NotifyConfig;
import com.example.truyentranh_asmapp.adapter.PhotoAdapter;
import com.example.truyentranh_asmapp.api.ApiService;
import com.example.truyentranh_asmapp.models.Comics;
import com.example.truyentranh_asmapp.models.ListComics;
import com.example.truyentranh_asmapp.models.Photo;
import com.example.truyentranh_asmapp.models.User;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ViewPager viewPager;
    CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mPhotoList;
    private Timer timer;
    private List<Comics> mComicsList = new ArrayList<>();
    TextView actionhint;
    RecyclerView recyclerViewComic;
    private String fullnameio;
    private ComicAdapter comicAdapter;
    private SearchView searchView;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://10.0.2.2:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewComic = view.findViewById(R.id.recylerViewComic);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerViewComic.addItemDecoration(dividerItemDecoration);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewComic.setLayoutManager(linearLayoutManager);
//        comicAdapter = new ComicAdapter(getListComic(),getActivity());
//        recyclerViewComic.setAdapter(comicAdapter);
        mSocket.connect();
        // lắng nghe su kien

//        mSocket.on("new msg", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                String data_sv_send = (String) args[0];
//
//                // hiện notify lên status
//                if (data_sv_send.isEmpty()){
//                    return;
//                }
//                postNotify("Thông báo từ server", data_sv_send );
//            }
//        });
        mSocket.on("new", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String data_sv_send = (String) args[0];
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login",getActivity().MODE_PRIVATE);
                String fullname = sharedPreferences.getString("fullname","");
                // hiện notify lên status
                if (data_sv_send.isEmpty()){
                    return;
                }
                postNotify("Thông báo từ server",fullname + data_sv_send );
            }
        });



        actionhint = view.findViewById(R.id.actionhint);
        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circle_indication);
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filerList(newText);
                return false;
            }
        });

        mPhotoList =getListPhoto();

        photoAdapter = new PhotoAdapter(getActivity(),mPhotoList);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSlineImg();
        GetComic();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login",getContext().MODE_PRIVATE);
        String fullname = sharedPreferences.getString("fullname","");
        if(fullname.length()<1){
            Toast.makeText(getActivity(),
                    "Chưa nhập nội dung", Toast.LENGTH_SHORT).show();
            return;
        }

        // gửi dữ liệu
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSocket.emit("new msg", fullname );
            }
        },1000);

    }


//    private List<Comics> getListComic() {
//        List<Comics> list = new ArrayList<>();
//        list.add(new Comics("1","chú mèo máy đến từ tương lai ","Fukuda","Truyện Vui","https://upload.wikimedia.org/wikipedia/vi/9/90/One_Piece%2C_Volume_61_Cover_%28Japanese%29.jpg","2003"));
//        list.add(new Comics("2","chú mèo máy đến từ tương lai ","Fukuda","Truyện Vui","https://images2.thanhnien.vn/528068263637045248/2023/7/5/anime-16885290131791004759743.jpg","2003"));
//        list.add(new Comics("3","chú mèo máy đến từ tương lai ","Fukuda","Truyện Vui","https://images2.thanhnien.vn/528068263637045248/2023/7/5/anime-16885290131791004759743.jpg","2003"));
//        return list;
//    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.anh2));
        list.add(new Photo(R.drawable.anh3));
        list.add(new Photo(R.drawable.anh4));
        list.add(new Photo(R.drawable.anh5));
        return list;
    }
    private void autoSlineImg(){
        if (mPhotoList == null || mPhotoList.isEmpty() || viewPager == null){
            return;
        }
        if (timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totaitem  = mPhotoList.size() - 1;
                        if (currentItem < totaitem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        }
                        else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }
    private void GetComic(){
        ApiService.apiService.getComics().enqueue(new Callback<ListComics>() {
            @Override
            public void onResponse(Call<ListComics> call, Response<ListComics> response) {
                mComicsList = Arrays.asList(response.body().getListComic());
                comicAdapter = new ComicAdapter(mComicsList,getContext());
                recyclerViewComic.setAdapter(comicAdapter);

            }

            @Override
            public void onFailure(Call<ListComics> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void filerList(String newText) {
        ArrayList<Comics> filteredList = new ArrayList<>();
        for (Comics cm : mComicsList){
            if (cm.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(cm);
            }
        }

        if (!filteredList.isEmpty()){
            comicAdapter.setData(filteredList);
        }else {
            filteredList.clear();
            comicAdapter.setData(filteredList);

        }
    }


    void postNotify(String title, String content){
        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(getActivity(), NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.star_big_on)
                .setContentTitle( title )
                .setContentText(content)
                .setAutoCancel(true)
                .build();
        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
            Toast.makeText(getActivity(), "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime();// lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy , customNotification);

    }

}