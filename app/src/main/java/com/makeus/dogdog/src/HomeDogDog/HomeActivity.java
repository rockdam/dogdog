package com.makeus.dogdog.src.HomeDogDog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.FeedFragment.Feed;
import com.makeus.dogdog.src.HomeDogDog.MypageFragment.Mypage;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.Ranking;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.TrackingNote;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.Home;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class HomeActivity extends FragmentActivity {
    private BottomNavigationView mBottomNavigationView;
    private FragmentManager mfragmentManager = getSupportFragmentManager();
    private FragmentTransaction mFragmentTransaction;
    Home mHomeFragment = new Home();
    Feed mFeedFragment = new Feed();
    Mypage mMypage = new Mypage();
    Ranking mRanking = new Ranking();
    TrackingNote mTrackingNote = new TrackingNote();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mFragmentTransaction = mfragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.FrameChanger, mHomeFragment).commitNowAllowingStateLoss();

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mFragmentTransaction = mfragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                        mFragmentTransaction.replace(R.id.FrameChanger, mHomeFragment).commitAllowingStateLoss();
                        mBottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case R.id.feed:
                        mFragmentTransaction.replace(R.id.FrameChanger, mFeedFragment).commitAllowingStateLoss();
                        mBottomNavigationView.getMenu().findItem(R.id.feed).setChecked(true);
                        break;
                    case R.id.tracking_note:
                        mBottomNavigationView.getMenu().findItem(R.id.tracking_note).setChecked(true);
                        mFragmentTransaction.replace(R.id.FrameChanger, mTrackingNote).commitAllowingStateLoss();
                        break;
                    case R.id.ranking:
                        mBottomNavigationView.getMenu().findItem(R.id.ranking).setChecked(true);
                        mFragmentTransaction.replace(R.id.FrameChanger, mRanking).commitAllowingStateLoss();
                        break;
                    case R.id.mypage:
                        mBottomNavigationView.getMenu().findItem(R.id.mypage).setChecked(true);
                        mFragmentTransaction.replace(R.id.FrameChanger, mMypage).commitAllowingStateLoss();
                        break;


                }
                return false;
            }
        });

    }
//bottomnavigation 을 쓸 때는 스와이프를 쓰지 않는 것을 권장한다. 스와이프가 된다는 것은 둘간의 관계를 압시한다 .
}

