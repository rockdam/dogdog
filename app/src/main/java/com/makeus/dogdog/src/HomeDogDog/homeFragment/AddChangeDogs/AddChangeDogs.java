package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.AddDogs;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.interfaces.AddDogsView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.models.Result;

import java.util.ArrayList;
import java.util.List;

public class AddChangeDogs extends BaseActivity implements AddDogsView {


    RecyclerView recyclerView;

    ArrayList<Result> adddogsData;
    AdddogsAdapter adddogsAdapter;
    ImageView close;
    AdddogsService adddogsService;
    ImageView addDosPlusButton;

    LinearLayout changeLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_change_dogs);
        setWindow();
        close = findViewById(R.id.close_addchangedogs);
        close.setOnClickListener(view ->
                finish()
        );


        recyclerView = findViewById(R.id.adddogsRecyclerview);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adddogsData = new ArrayList<>();
        adddogsAdapter = new AdddogsAdapter(getBaseContext(), adddogsData);


        recyclerView.setAdapter(adddogsAdapter);


        changeLinearLayout =findViewById(R.id.add_dogs_addchangedogs);


//        addDosPlusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });
        changeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddChangeDogs.this, AddDogs.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        adddogsService = new AdddogsService(this);
        adddogsService.refreshHomeView();


        adddogsAdapter.setOnItemClickListener(new AdddogsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position,int dogIdx) {

                adddogsService.updateHomeView(dogIdx);


            }
        });
    }

    private void setWindow() {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setGravity(Gravity.BOTTOM);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
//          바깥쪽 클릭 방지
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
//
    }

    @Override
    public void refresh(List<Result> result) {


        adddogsAdapter.updateItem(result);
//
        adddogsAdapter.notifyDataSetChanged();

//        recyclerView.invalidate();

//        recyclerView.notifyDataSetChanged();





    }

    @Override
    public void moveHomeFragemnt() {
        adddogsAdapter.notifyDataSetChanged();
        finish();
    }

//    private void init() {
//        int numberOfColumns = 1;// 한줄에 2개의 컬럼을 추가
//        mRecyclerViewEatDeals = mHomeActivity.findViewById(R.id.recyclerview_eatdeals);
//        mGridLayoutManager = new GridLayoutManager(mHomeActivity, numberOfColumns);
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        mRecyclerViewEatDeals.setLayoutManager(mGridLayoutManager);
//        mAdapter = new EatDealsRecyclerAdapter(mHomeActivity);
//        mRecyclerViewEatDeals.setAdapter(mAdapter);
//    }
}