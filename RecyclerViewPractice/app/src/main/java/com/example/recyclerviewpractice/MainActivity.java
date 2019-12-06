package com.example.recyclerviewpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] textSet = {"리사이클러뷰를","승록이가","연습중","입니다."};
        int[] imgSet= {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground,
                R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

        adapter= new MyAdapter(textSet, imgSet);
        recyclerView.setAdapter(adapter);

    }
}
