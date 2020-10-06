package com.makeus.dogdog.src.HomeDogDog.startWalking;
/*
* 아직 해야되는 사항 .. 버튼 누를때 산책 되게 .. 그리고 view랑 산책 시간이랑 따로 논다.
* */
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

import java.util.List;

public class startWalking extends BaseActivity implements View.OnClickListener{

    DonutView mDonutView;
    Chronometer mWalkingTime;
    TextView mWalkingDistance;
    ImageView mStartWalking, mStopButton, mStartCamera;
    private double mTimetickin = 0;
    private int mPercent;
    private boolean mRunning;
    private long timeWhenStopped = 0;
    private long mStartTime;
    long time;
    float distanceInMeters = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    LocationManager mLocationManager;
    private Location lastLocation;
    private static final String TAG = "MainActivity";
    int LOCATION_REQUEST_CODE = 10001;
    boolean isFirst=true;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;


    float distance = 0;
    Location oldLocation,newLocation;
    TextView test;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                Log.d(TAG, "onLocationResult: " + location.toString());
            }

            if(isFirst) {
                oldLocation = locationResult.getLastLocation();
                isFirst=false;
            }else{


                distance+=oldLocation.distanceTo(locationResult.getLastLocation());
//                이걸 계속 더하고 있네 ;;
                //위의 코드가 원래는 지구 반지름 들어간 .. 뭐 공식있는거 그거였다고 한다..
                oldLocation=locationResult.getLastLocation();
                String dist=String.format("%.2f",distance/1000);
                Log.e("산책 거리 ",""+dist); //반환 m

                Toast.makeText(getBaseContext(),"df"+dist,Toast.LENGTH_SHORT);
                mWalkingDistance.setText(dist+"km");
                Log.e("거리 ",""+distance); //반환 m

                test.setText(""+distance);
            }
            //내가 짠코드 .. 새로 재생되면 늘어나게 .
//            https://stackoverflow.com/questions/34551318/calculate-actual-distance-travelled-by-mobile/34575257#34575257
//            여기 참조
//            Location newLocation =locationResult.getLastLocation();
//https://stackoverflow.com/questions/38072228/android-getting-different-latitude-and-longitude-on-same-location
//            정확도는 어쩔 수가 없다.;;; 10m 오차 남;

        }
    };


// 넘어는 오는데 초기 시간 표시 , 처음 화면 바꿔야함


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
        test=findViewById(R.id.test);
        mWalkingTime = findViewById(R.id.walking_time_startwalking);
        mWalkingDistance = findViewById(R.id.walking_distance_startwalking);
        mDonutView = findViewById(R.id.donut);
        mStopButton = findViewById(R.id.stopbutton_startwalking);
        mStartCamera = findViewById(R.id.cameraApp_startWalking);
        if (getIntent().getStringExtra("timeTickin") != null) {
            mTimetickin = Double.parseDouble(getIntent().getStringExtra("timeTickin"));
        }
        mPercent = getIntent().getIntExtra("percent", 0);
        mStartTime = getIntent().getLongExtra("time", 0);
        mDonutView.setValue(mTimetickin, mPercent);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(6000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setPriority(LocationRequest.);



        mStartCamera.setOnClickListener(this);


        mWalkingTime.setBase(SystemClock.elapsedRealtime());
        mWalkingTime.setText(calculate(time));

        mStartWalking.setOnClickListener(this);
        mStopButton.setOnClickListener(this);

        //add(Location.api) play-services 라이브러리와 그 라이브러리에서 제공하는 GoogleApiClient 는 Fused Location API 만을 제공하는 라이브러리 및 클래스가
//        아닙니다. 다양한 구글의 서비스를 활용하기 위한.. 그래서 그중에서 LocationService를 사용하겠다는 구문 .


//        mLocationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
//        criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
//        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
////https://stackoverflow.com/questions/32635704/android-permission-doesnt-work-even-if-i-have-declared-it
////        Target api 23 이상인 경우 .. ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION 위험해서 이런 설정을 해야한다.
//
//
//        provider = mLocationManager.getBestProvider(criteria, true);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //Settings of device are satisfied and we can start location updates
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(startWalking.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
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
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }
//    private void checkPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                }, REQUEST_LOCATION_PERMISSIONS);
//            } else {
//                getLocation();
//            }
//        } else {
//            getLocation();
//        }
//    }
    private void stopLocationUpdates() {

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "askLocationPermission: you should show an alert dialog...");
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
//                getLastLocation();
                checkSettingsAndStartLocationUpdates();
            } else {
                //Permission not granted
            }
        }
    }

//    private void getLocation() {
//
//
//        if (mFusedLocationClient == null) {
//            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        }
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        //마지막으로 알려진 위치
////        https://developers.google.com/location-context/fused-location-provider
//        mFusedLocationClient.getLastLocation()
//                .addOnCompleteListener(this, task -> {
//                    if (!task.isSuccessful()) {
//                        return;
//                    }
//
//                    lastLocation = task.getResult();
//                    if (lastLocation == null) {
//                        return;
//                    }
////                    updateLocation();
//                });
//    }



    //실제로 onCreate() 너무 많은걸 넣으면 동작을 안하네? onClick 같은건 onResume에 넣자 .
    @Override
    protected void onResume() {
        super.onResume();


//        apiClient.connect();//connect ->onConnected() or OnConnectedFail()

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


//        출처: https://ghj1001020.tistory.com/14 [혁준 블로그]
        mWalkingTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {


                time = mStartTime + SystemClock.elapsedRealtime() - chronometer.getBase();

//                System.out.println("time : " + time);

                chronometer.setText(calculate(time));

                //1000

                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000) / 1000;


                mTimetickin = ((double) s / (18));
                mPercent = s / 18;
                mDonutView.setValue(mTimetickin, mPercent);
//                    System.out.println("time : " + time);
                System.out.println("mTimetickin" + mTimetickin);
                System.out.println("Time체크" + s);

//                    mDonutView.setValue(Double.parseDouble(getIntent().getStringExtra("time")), getIntent().getIntExtra("percent", 0));


            }
        });


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
        String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);

        return t;


    }

    private void startLocationUpdate(){

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

                    mWalkingTime.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    mWalkingTime.start();
                    mStartWalking.setImageResource(R.drawable.pause_pause);
                    mRunning = true;
                    startLocationUpdate();

                } else { //일시 중지

                    timeWhenStopped = mWalkingTime.getBase() - SystemClock.elapsedRealtime();
                    mWalkingTime.stop();
                    mStartWalking.setImageResource(R.drawable.start);
                    mRunning = false;
                    stopLocationUpdates();


                }

                break;
            case R.id.stopbutton_startwalking:


                if(mRunning)
                {

                    Toast.makeText(getApplicationContext(),"일시 중지를 눌러주세요 :)",Toast.LENGTH_SHORT);

                }else{

                    SharedPreferences prefs = getSharedPreferences("startwalking", MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("timetickin", String.valueOf(mTimetickin));
                    editor.putInt("percent", mPercent);
                    editor.putLong("time", time);

                    editor.commit();
                    finish();

                }

                break;
            case R.id.cameraApp_startWalking:

                dispatchTakePictureIntent();
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mRunning)
        {

            Toast.makeText(getApplicationContext(),"일시 중지를 눌러주세요 :)",Toast.LENGTH_SHORT);

        }else{
            finish();
        }
    }
}