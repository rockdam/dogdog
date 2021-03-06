package com.makeus.dogdog.src.HomeDogDog.startWalking;
/*
 * 아직 해야되는 사항 .. 버튼 누를때 산책 되게 .. 그리고 view랑 산책 시간이랑 따로 논다.
 * */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
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
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.HomeActivity;
import com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces.StartWalkingView;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.Result;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StopWalkingBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StartWalking extends BaseActivity implements View.OnClickListener, StartWalkingView {

    DonutView mDonutView;
    Chronometer mWalkingTimeCronometer;
    int mWalkingTime;
    TextView mWalkingDistanceTextView;
    int mWalkingDistance;
    ImageView mStartWalking, mStopButton, mStartCamera, mCompltedWalking;
    private double mTimetickin;
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
    PermissionListener permissionlistener;
    boolean isPause = true;
    boolean checkGps = true;
    Intent Serviceintent;
    int sendTime;
    StopWalkingBody mStopWalkingBody;
    StartWalkingService mStartWalkingService, mStopWalkingService;
    int mDogIdx;

    ImageView mIconShare;
    TextView mFinishedText; // 목표 ->percent>=100 일 때  목표를 달성했어요!
    ImageView backButton;
    float Initialdistance = 0;
    Location oldLocation;

    Boolean isFirstCompletedMission; //한번만 나오게 목표를 달성했어요

    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }// 사진 어플 실행

//    스크린샷 찍기

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Do the file write
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();
            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();
            }


            SharedSns(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }


    }

    private void SharedSns(File imageFile) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.fromFile(imageFile);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setDataAndType(uri, "image/*");
        startActivity(Intent.createChooser(shareIntent, "목표 달성 공유하기"));


    }


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
        Serviceintent = new Intent(StartWalking.this, ForegroundWalkingService.class);
        mStopWalkingBody = new StopWalkingBody();
        isFirstCompletedMission = true;
        mIconShare = findViewById(R.id.share_startwalking);
        mCompltedWalking = findViewById(R.id.completed_startwalking);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

//        출처: https://docko.tistory.com/entry/androidosFileUriExposedException-filestorageemulated0-exposed-beyond-app-through-IntentgetData [장똘]
// uri 노출 해결 이게 쉽다.


//https://m.blog.naver.com/PostView.nhn?blogId=hg1286&logNo=220541645364&proxyReferer=https:%2F%2Fwww.google.com%2F 참조
        mIconShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();


            }
        });

        mFinishedText = findViewById(R.id.finishedText_startwalking);
        mDogIdx = getIntent().getIntExtra("dogIdx", 1);
//        if (getIntent().getStringExtra("timeTicking") != null) {
//            mTimetickin = Double.parseDouble(getIntent().getStringExtra("timeTicking"));
//        }
//        mPercent = getIntent().getIntExtra("percent", 0);
//        mStartTime = getIntent().getLongExtra("time", 0);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        backButton = findViewById(R.id.backButton_startwalking);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mRunning) {

                    Toast.makeText(getApplicationContext(), "일시 중지를 눌러주세요 :)", Toast.LENGTH_SHORT).show();

                } else {
                    stopService(Serviceintent);
                    sendStopWalkingTime();


                }

            }
        });


        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(6000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        onStartForegroundService();
        mStartCamera.setOnClickListener(this);
        mStartWalkingService = new StartWalkingService(this, mDogIdx);
        showDogDogLoadingDialog();
        mStartWalkingService.refreshStartWalkingView();

        mStartWalking.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        mWalkingTimeCronometer.setOnChronometerTickListener(chronometer -> {

            int percent;

            time = mWalkingTime + SystemClock.elapsedRealtime() - chronometer.getBase();

            chronometer.setText(calculate(time));

            //1000

            int h = (int) (time / 3600000);
//            int m = (int) (time - h * 3600000) / 60000;
            int s = (int) (time - h * 3600000) / 1000;


            mTimetickin = ((double) s / (18));
            percent = s / 18;
            mDonutView.setValue(mTimetickin, percent);
            System.out.println("mTimetickin" + mTimetickin);
            System.out.println("Time체크" + s);
            System.out.println("Time" + time);

            sendTime = s;

            //얘를 100으로 바꾸자
            if (percent >= 100 && isFirstCompletedMission) {

                mCompltedWalking.setVisibility(View.VISIBLE);
                isFirstCompletedMission = false;
                mFinishedText.setText("목표를 달성했어요!");

            }

            Log.e("sendTime", "" + sendTime);
        });
        mStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionlistener = new PermissionListener() {

                    @Override

                    public void onPermissionGranted() {

//                Toast.makeText(SelectedPicture.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        dispatchTakePictureIntent();

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

//                Toast.makeText(SelectedPicture.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }


                };

                TedPermission.with(getBaseContext())
                        .setPermissionListener(permissionlistener)
                        .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요.")
                        .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
                        .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
                        .check();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
//        sendStopWalkingTime();
//        stopLocationUpdates();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
                    apiException.startResolutionForResult(StartWalking.this, 1001);
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

        if (checkGps) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
            checkGps = false;
        } else {

            checkGps = true;
        }
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

    public void onStartForegroundService() {

        Serviceintent.setAction("startForeground");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //오레오 버전 이상에서는 ..
            startForegroundService(Serviceintent);
        } else {
            startService(Serviceintent);
        }
    }

    public void onStopForegroundService() {
        Serviceintent.setAction("stopForeground");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //오레오 버전 이상에서는 ..
            startForegroundService(Serviceintent);
        } else {
            startService(Serviceintent);
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


//                if (mRunning) {
//
//                    Toast.makeText(getApplicationContext(), "일시 중지를 눌러주세요 :)", Toast.LENGTH_SHORT).show();
//
//                } else {

                showDogDogLoadingDialog();
                stopService(Serviceintent);
                sendStopWalkingTime();


//                }

                break;
            case R.id.cameraApp_startWalking:

//                dispatchTakePictureIntent();
                break;


        }
    }

    void sendStopWalkingTime() {

        mStopWalkingBody.setDogIdx(mDogIdx);
        mStopWalkingBody.setWalkingDistance(mWalkingDistance);
        //이거 나중에 고쳐야됌
        mStopWalkingBody.setWalkingTime(sendTime);

        Log.e("sendTime", "보냅니다 시간" + sendTime);
        mStopWalkingService = new StartWalkingService(this, mStopWalkingBody);
        mStopWalkingService.terminatedStartWalking();
    }

    // 그렇니깐 pause일 때는 상관 x
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        위의 메서드가 호출되면 액티비티가 종료 됩니다.
//        if (mRunning) {
//
//            Toast.makeText(getApplicationContext(), "일시 중지를 눌러주세요 :)", Toast.LENGTH_SHORT).show();
//
//        } else {
        showDogDogLoadingDialog();
        sendStopWalkingTime();

//            onStopForegroundService();
        stopService(Serviceintent);
//        }
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


                Initialdistance += oldLocation.distanceTo(locationResult.getLastLocation());
                //위의 코드가 원래는 지구 반지름 들어간 .. 뭐 공식있는거 그거였다고 한다..
                oldLocation = locationResult.getLastLocation();
                double calculateddistance = Math.floor(Initialdistance / 1000);
                String dist = String.format(Locale.getDefault(), "%.2f", (Initialdistance / 1000));
                String output = dist + "km";
                Log.e("산책 거리 ", "" + dist); //반환 m


//                Toast.makeText(getBaseContext(), "" + Initialdistance, Toast.LENGTH_SHORT).show();


                mWalkingDistanceTextView.setText(output);
                mWalkingDistance = (int) Initialdistance;
                Log.e("거리 ", "" + Initialdistance); //반환 m

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
        mPercent = result.getPercent();
        mWalkingDistance = result.getDistance();
        mWalkingTime = result.getWalkingTime();
        Initialdistance = mWalkingDistance;
        Log.e("mWalkingTime", "" + mWalkingTime);
        double calculateddistance = Math.floor(Initialdistance / 1000);
        String dist = String.format(Locale.getDefault(), "%.2f", (Initialdistance / 1000));
        String output = dist + "km";

        Log.e("mTimetickin", "" + mTimetickin);

        mWalkingTimeCronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        mWalkingTimeCronometer.setText(calculate(mWalkingTime * 1000));
        // mWalkingTimeCronometer 이 위에서 한번 호출 되는것으로 추정
        mTimetickin = ((double) mWalkingTime / (18));
        mDonutView.setValue(mTimetickin, mPercent);//시점이 위에 있으면 안되네
        if (mWalkingTime == 0) {
            mWalkingDistanceTextView.setText("0.00" + "km");
        } else {

            mWalkingDistanceTextView.setText(output);
        }
        Log.e("mTimetickin", "" + mTimetickin);

        mWalkingTime *= 1000;
        Log.e("mPercent", "" + mPercent);
        if (mPercent >= 100) {
            mCompltedWalking.setVisibility(View.VISIBLE);
            mFinishedText.setText("목표를 달성했어요!");

        }
        hideDogDogLoadingDialog();

    }

    @Override
    public void terminate() {
        Log.e("성공완료", "굿");


        Intent intent = new Intent(StartWalking.this, HomeActivity.class);
        startActivity(intent);
        hideDogDogLoadingDialog();
        finish();


    }


// 넘어는 오는데 초기 시간 표시 , 처음 화면 바꿔야함


}