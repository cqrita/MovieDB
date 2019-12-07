package com.example.MovieDB.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieDB.R;
import com.example.MovieDB.adapter.MovieListAdapter;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    public FavoriteFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("fragment","Favorite Fragment loaded");
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        String[] list = new String[100];
        for (int i=0; i<100; i++) {
            list[i] = "1";
        }
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        //fragment 에서는 Getview 를 통해서 fragment root 의 view 를 받아온다.
        RecyclerView recyclerView = getView().findViewById(R.id.home_recycler_view) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;//해당 fragment 를
        // 관리하는 avcivity를 리턴하는 함수인 getActivity()

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        MovieListAdapter adapter = new MovieListAdapter(list, list) ;
        recyclerView.setAdapter(adapter) ;
        return inflater.inflate(R.layout.favorite, container, false);

    }

}
