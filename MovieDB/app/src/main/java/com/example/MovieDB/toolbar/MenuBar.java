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
    public MenuBar(Context context) {
        super(context);
        Log.d("Seungrok", "menubar");
    }
//    public int getMenu(){
//        return this.getMenu;
//    }
}
