package com.example.MovieDB.toolbar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.MovieDB.R;

import butterknife.BindView;

public class MenuBar extends LinearLayout {
    @BindView(R.id.button1)
    private Button button1;
    @BindView(R.id.button2)
    private Button button2;
    @BindView(R.id.button3)
    private Button button3;
    private int getMenu=1;
    public MenuBar(Context context) {
        super(context);
        Log.d("a", "menubar");
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menubar,this,true);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getMenu=1;
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getMenu=2;
            }
        });
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getMenu=3;
            }
        });
    }
    public int getMenu(){
        return this.getMenu;
    }
}
