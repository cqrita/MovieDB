package com.example.MovieDB.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieDB.R;
import com.example.MovieDB.adapter.MovieListAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
//    private void setDB(Context ctx) {
//        AssetManager assetManager = ctx.getResources().getAssets();
//        File dbFile = ctx.getDatabasePath("movie.db");
//        InputStream is = null;
//        FileOutputStream fo = null;
//        long filesize = 0;
//        try {
//            Log.d("success","DB IMPORTED");
//            is = assetManager.open("movie.db", AssetManager.ACCESS_BUFFER);
//            OutputStream os = new FileOutputStream(dbFile);
//            byte[] buffer = new byte[1024];
//            while (is.read(buffer) > 0) {
//                os.write(buffer);
//            }
//            os.flush();
//            os.close();
//
//            is.close();
//        } catch (IOException e) {
//
//            Log.e("fail", e.getLocalizedMessage(), e);
//        }
//    }
//
//
//
//    class ProductDBHelper extends SQLiteOpenHelper {  //새로 생성한 adapter 속성은 SQLiteOpenHelper이다.
//        public ProductDBHelper(Context context) {
//            super(context, "movie.db", null, 1);    // db명과 버전만 정의 한다.
//            // TODO Auto-generated constructor stub
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            // TODO Auto-generated method stub
//        }
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // TODO Auto-generated method stub
//        }
//    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        String[] textSet = new String[100];
//        String[] imgSet = new String[100];
//        // db 불러온 후
//        Log.d("start", "start");
//
//        setDB(getContext());
//        ProductDBHelper mHelper = new ProductDBHelper(getContext());
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM popular_movie", null); //쿼리문
//        if(cursor.moveToFirst()){
//            do{
//                int i = 0;
//                textSet[i] = cursor.getString(0);
//                imgSet[i] = cursor.getString(1);
//                i = i + 1;
//            }while(cursor.moveToNext());
//        }
//
//
//
//
//        if(cursor.moveToFirst()) {
//            do {
//                Log.d("db", cursor.toString());
//                // tv_movie.setText(cursor.getString(0));
//            } while(cursor.moveToNext());
//        }
//
//
//        // 리사이클러뷰에 표시할 데이터 리스트 생성.
//
//
//        // home fragment 에서 activity_main의 fragment 를 리사이클러뷰로 지정을 해준다.
//        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
//        //fragment 에서는 Getview 를 통해서 fragment root 의 view 를 받아온다.
//        RecyclerView recyclerView = getView().findViewById(R.id.home_recycler_view) ;
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;//해당 fragment 를
//        // 관리하는 avcivity를 리턴하는 함수인 getActivity()
//
//        // 리사이클러뷰에 MovieListAdapter 지정
//        MovieListAdapter adapter = new MovieListAdapter(imgSet, textSet) ;
//        recyclerView.setAdapter(adapter) ;
        return inflater.inflate(R.layout.home, container, false);

    }

}
