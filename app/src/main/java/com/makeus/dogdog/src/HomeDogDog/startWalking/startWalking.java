package com.makeus.dogdog.src.HomeDogDog.startWalking;
/*
 * 아직 해야되는 사항 .. 버튼 누를때 산책 되게 .. 그리고 view랑 산책 시간이랑 따로 논다.
 * */

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces.StartWalkingView;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.Result;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StopWalkingBody;

import java.util.Locale;

public class startWalking extends BaseActivity implements View.OnClickListener , StartWalkingView {

    DonutView mDonutView;
    Chronometer mWalkingTimeCronometer;
    int mWalkingTime;
    TextView mWalkingDistanceTextView;
    int mWalkingDistance;
    ImageView mStartWalking, mStopButton, mStartCamera;
    private double mTimetickin ;
    private int mPercent;
    private boolean mRunning;
    private long timeWhenStopped = 0;
    long time;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    LocationManager mLocationManager;
    private static final String TAG = "MainActivity";
    int LOCATION_REQUEST_CODE = 10001;
    boolean isFirst = true;
    FusedLocationProviderClient mFusedLocationClient;
    LocationRequest locationRequest;

    int sendTime;
    StopWalkingBody mStopWalkingBody;
    StartWalkingService mStartWalkingService,mStopWalkingService;
    int mDogIdx;

    float distance = 0;
    Location oldLocation;


    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }// 사진 어플 실행


    //https://stackoverflow.com/questions/42619863/how-to-calculate-distance-every-15-sec-with-using-gps-heavy-accuracy
//거리구현은 서비스로 하는게 좋습니다..gps
//    https://stackoverflow.com/questions/28535703/best-way-to-get-user-gps-location-in-background-in-android
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_walking);
        mStartWalking = findViewById(R.id.start_walking);
        mWalkingTimeCronometer = findViewById(R.id.walking_time_startwalking);
        mWalkingDistanceTextView = findViewById(R.id.walking_distance_startwalking);
        mDonutView = findViewById(R.id.donut);
        mStopButton = findViewById(R.id.stopbutton_startwalking);
        mStartCamera = findViewById(R.id.cameraApp_startWalking);

        mDogIdx =getIntent().getIntExtra("dogIdx",1);
//        if (getIntent().getStringExtra("timeTicking") != null) {
//            mTimetickin = Double.parseDouble(getIntent().getStringExtra("timeTicking"));
//        }
//        mPercent = getIntent().getIntExtra("percent", 0);
//        mStartTime = getIntent().getLongExtra("time", 0);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mDogIdx =getIntent().getIntExtra("dogIdx",0);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(6000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        mStartCamera.setOnClickListener(this);



        mStartWalking.setOnClickListener(this);
        mStopButton.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mStartWalkingService =new StartWalkingService(this, mDogIdx);
        mStartWalkingService.refreshStartWalkingView();


    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }


    // 얘는 inner로 나중에 정리
    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(locationSettingsResponse -> {
            //Settings of device are satisfied and we can start location updates
            startLocationUpdates();
        });
        locationSettingsResponseTask.addOnFailureListener(e -> {


            if (e instanceof ResolvableApiException) {
                ResolvableApiException apiException = (ResolvableApiException) e;
                try {
                    apiException.startResolutionForResult(startWalking.this, 1001);
                } catch (IntentSender.SendIntentException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {

        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "askLocationPermission: you should show an alert dialog...");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
//                getLastLocation();
                checkSettingsAndStartLocationUpdates();
            }
        }
    }


    //실제로 onCreate() 너무 많은걸 넣으면 동작을 안하네? onClick 같은건 onResume에 넣자 .
    @Override
    protected void onResume() {
        super.onResume();

//        mDonutView.setValue(mTimetickin, mPercent);

//        apiClient.connect();//connect ->onConnected() or OnConnectedFail()

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


//        출처: https://ghj1001020.tistory.com/14 [혁준 블로그]



//        provider=LocationManager.GPS_PROVIDER;
        //GPS가 켜져있는지 체크
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        }

    }

    public String calculate(long time) {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;

        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);


    }

    private void startLocationUpdate() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            getLastLocation();
            checkSettingsAndStartLocationUpdates();
        } else {
            askLocationPermission();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.start_walking:


                if (!mRunning) { //시작

                    mWalkingTimeCronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    mWalkingTimeCronometer.start();
                    mStartWalking.setImageResource(R.drawable.pause_pause);
                    mRunning = true;
                    startLocationUpdate();

                } else { //일시 중지

                    timeWhenStopped = mWalkingTimeCronometer.getBase() - SystemClock.elapsedRealtime();
                    mWalkingTimeCronometer.stop();
                    mStartWalking.setImageResource(R.drawable.start);
                    mRunning = false;
                    stopLocationUpdates();


                }

                break;
            case R.id.stopbutton_startwalking:


                if (mRunning) {

                    Toast.makeText(getApplicationContext(), "일시 중지를 눌러주세요 :)", Toast.LENGTH_SHORT).show();

                } else {
                    mStopWalkingBody=new StopWalkingBody();
                    mStopWalkingBody.setDogIdx(mDogIdx);
                    mStopWalkingBody.setWalkingDistance(0);
                    //이거 나중에 고쳐야됌
                    mStopWalkingBody.setWalkingTime(sendTime);
                    mStopWalkingService=new StartWalkingService(this,mStopWalkingBody);
                    mStopWalkingService.terminatedStartWalking();

                }

                break;
            case R.id.cameraApp_startWalking:

                dispatchTakePictureIntent();
                break;


        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        위의 메서드가 호출되면 액티비티가 종료 됩니다.
        if (mRunning) {

            Toast.makeText(getApplicationContext(), "일시 중지를 눌러주세요 :)", Toast.LENGTH_SHORT).show();

        } else {
            finish();
        }
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                Log.d(TAG, "onLocationResult: " + location.toString());
            }

            if (isFirst) {
                oldLocation = locationResult.getLastLocation();
                isFirst = false;
            } else {


                distance += oldLocation.distanceTo(locationResult.getLastLocation());
                //위의 코드가 원래는 지구 반지름 들어간 .. 뭐 공식있는거 그거였다고 한다..
                oldLocation = locationResult.getLastLocation();
                String dist = String.format(Locale.getDefault(), "%.1f", (distance / 1000));
                Log.e("산책 거리 ", "" + dist); //반환 m

                Toast.makeText(getBaseContext(), ""+ distance, Toast.LENGTH_SHORT).show();
                String output = dist + "km";
                mWalkingDistanceTextView.setText(output);
                Log.e("거리 ", "" + distance); //반환 m

            }
            //내가 짠코드 .. 새로 재생되면 늘어나게 .
//            https://stackoverflow.com/questions/34551318/calculate-actual-distance-travelled-by-mobile/34575257#34575257
//            여기 참조
//            Location newLocation =locationResult.getLastLocation();
//            https://stackoverflow.com/questions/38072228/android-getting-different-latitude-and-longitude-on-same-location
//            정확도는 어쩔 수가 없다.;;; 10m 오차 남;
            //add(Location.api) play-services 라이브러리와 그 라이브러리에서 제공하는 GoogleApiClient 는 Fused Location API 만을 제공하는 라이브러리 및 클래스가
//        아닙니다. 다양한 구글의 서비스를 활용하기 위한.. 그래서 그중에서 LocationService를 사용하겠다는 구문 .

////https://stackoverflow.com/questions/32635704/android-permission-doesnt-work-even-if-i-have-declared-it
////        Target api 23 이상인 경우 .. ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION 위험해서 이런 설정을 해야한다.
        }
    };

    @Override
    public void refresh(Result result) {
        mPercent=result.getPercent();
        mWalkingDistance =result.getDistance();
        mWalkingTime=result.getWalkingTime();
        mTimetickin=(double)mWalkingTime/(double)18;
        mWalkingTimeCronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        mWalkingTimeCronometer.setText(calculate(mWalkingTime*1000));
        mWalkingDistanceTextView.setText(""+mWalkingDistance);
        mDonutView.setValue(mTimetickin, mPercent);
        mWalkingTime *=1000;
        mWalkingTimeCronometer.setOnChronometerTickListener(chronometer -> {


            time = mWalkingTime + SystemClock.elapsedRealtime() - chronometer.getBase();

            chronometer.setText(calculate(time));

            //1000

            int h = (int) (time / 3600000);
//            int m = (int) (time - h * 3600000) / 60000;
            int s = (int) (time - h * 3600000) / 1000;


            mTimetickin = ((double) s / (18));
            mPercent = s / 18;
            mDonutView.setValue(mTimetickin, mPercent);
            System.out.println("mTimetickin" + mTimetickin);
            System.out.println("Time체크" + s);
            System.out.println("Time"+time);

            sendTime=s;

        });

    }

    @Override
    public void terminate() {
        Log.e("성공완료","굿");
        finish();
    }


// 넘어는 오는데 초기 시간 표시 , 처음 화면 바꿔야함


}