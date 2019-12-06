package com.example.recyclerviewpractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] textSet;
    private int[] imgSet;
    //이 데이터들을 가지고 각 뷰 홀더에 들어갈 텍스트 뷰에 연결할 것
    //생성자 함수
    public MyAdapter(String[] textSet, int[] imgSet){
        this.textSet= textSet;
        this.imgSet = imgSet;
    }

    //리사이클러뷰에 들어갈 뷰 홀더, 그리고 그 뷰 홀더에 들어갈 아이템들을 지정함
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;

        public MyViewHolder(View view){
            super(view);
            this.imageView = view.findViewById(R.id.iv_pic);
            this.textView = view.findViewById(R.id.tv_text);

        }
    }


    //어댑터 클래스 상속시 구현해야할 함수 3가지: onCreateViewHolder, onBindViewHolder, getItemCount
    //리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수, 뷰 홀더는 실제 레이아웃 파일과 매핑되어야하며,
    // extends의 Adapter<>에서 <>안에 들어가는 타입을 따른다.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View holderView=
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holder_view   ,
                        viewGroup,
                        false);
        MyViewHolder myViewHolder =new MyViewHolder(holderView);
        return myViewHolder;
    }
    //실제 각 뷰 홀더에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.textView.setText(this.textSet[position]);
        myViewHolder.imageView.setBackgroundResource(this.imgSet[position]);

    }
    //리사이클러 뷰 안에 들어갈 뷰 홀더의 개수
    @Override
    public int getItemCount() {
        return textSet.length > imgSet.length ? textSet.length: imgSet.length;
    }
    //이 다음으로는 레이아웃 파일 holder_view를 만들어야 한다. gradle 에서 import 한 라이브러리 카드뷰를 사용해야 한다.
}
