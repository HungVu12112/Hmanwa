package com.example.truyentranh_asmapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.truyentranh_asmapp.R;
import com.example.truyentranh_asmapp.adapter.ComicAdapter;
import com.example.truyentranh_asmapp.api.ApiService;
import com.example.truyentranh_asmapp.models.Comics;
import com.example.truyentranh_asmapp.models.ListComics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerViewFa;
    private List<Comics> mComicsList = new ArrayList<>();
    private ComicAdapter comicAdapter;
    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewFa = view.findViewById(R.id.recyccleViewFavorite);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerViewFa.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewFa.setLayoutManager(linearLayoutManager);
        GetComic();
    }
    private void GetComic(){
        ApiService.apiService.getComicsFa().enqueue(new Callback<ListComics>() {
            @Override
            public void onResponse(Call<ListComics> call, Response<ListComics> response) {
                mComicsList = Arrays.asList(response.body().getListComic());
                comicAdapter = new ComicAdapter(mComicsList,getContext());
                recyclerViewFa.setAdapter(comicAdapter);

            }

            @Override
            public void onFailure(Call<ListComics> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}