package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
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
        adddogsData = new ArrayList<>();
        adddogsAdapter = new AdddogsAdapter(getBaseContext(), adddogsData);
        adddogsService = new AdddogsService(this);
        adddogsService.refreshHomeView();



        recyclerView.setAdapter(adddogsAdapter);

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


        adddogsData.addAll(result);
        adddogsAdapter.addItem(adddogsData);


//        recyclerView.invalidate();

        recyclerView.notifyDataSetChanged();





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