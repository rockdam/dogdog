package com.makeus.dogdog.src.HomeDogDog.startWalking;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

public class startWalking extends BaseActivity implements View.OnClickListener {

    SeekBar seekBar;
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
    static final int REQUEST_IMAGE_CAPTURE = 1;

    LocationManager locationManager;
    Criteria criteria;
    String provider;
    Location location;
    double old_latitude; // latitude
    double old_longitude; // longitude
    double new_latitude; // latitude
    double new_longitude; // longitude
    float total_distance = 0.0f;
    String details;
// 넘어는 오는데 초기 시간 표시 , 처음 화면 바꿔야함


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
//https://stackoverflow.com/questions/42619863/how-to-calculate-distance-every-15-sec-with-using-gps-heavy-accuracy
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_walking);
        mStartWalking = findViewById(R.id.start_walking);
        mWalkingTime = findViewById(R.id.walking_time_startwalking);
        mWalkingDistance = findViewById(R.id.walking_distance_startwalking);
        mDonutView = findViewById(R.id.donut);
        mStopButton = findViewById(R.id.stopbutton_startwalking);
        mStartCamera = findViewById(R.id.cameraApp_startWalking);
        if(getIntent().getStringExtra("timeTickin")!=null) {
            mTimetickin = Double.parseDouble(getIntent().getStringExtra("timeTickin"));
        }
        mPercent = getIntent().getIntExtra("percent", 0);
        mStartTime = getIntent().getLongExtra("time", 0);
        mDonutView.setValue(mTimetickin, mPercent);




        mStartCamera.setOnClickListener(this);



        mWalkingTime.setBase(SystemClock.elapsedRealtime());
        mWalkingTime.setText(calculate(time));

        mStartWalking.setOnClickListener(this);
        mStopButton.setOnClickListener(this);


    }


    //실제로 onCreate() 너무 많은걸 넣으면 동작을 안하네? onClick 같은건 onResume에 넣자 .
    @Override
    protected void onResume() {
        super.onResume();

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
                mPercent=s/18;
                mDonutView.setValue(mTimetickin, mPercent);
//                    System.out.println("time : " + time);
                System.out.println("mTimetickin" + mTimetickin);
                System.out.println("Time체크" + s);

//                    mDonutView.setValue(Double.parseDouble(getIntent().getStringExtra("time")), getIntent().getIntExtra("percent", 0));


            }
        });



        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        provider = locationManager.getBestProvider(criteria, true);
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
        location = locationManager.getLastKnownLocation(provider);

        try {
            old_latitude = location.getLatitude();
            old_longitude = location.getLongitude();
        } catch (Exception e) {

        }







        locationManager.requestLocationUpdates(provider, 15000, 1, new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {
                if (ActivityCompat.checkSelfPermission(startWalking.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(startWalking.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                location = locationManager.getLastKnownLocation(provider);
                new_latitude = location.getLatitude();
                new_longitude = location.getLongitude();

                total_distance = meterDistanceBetweenPoints(old_latitude, old_longitude, new_latitude, new_longitude) + total_distance;

                if (details == null) {
                    details = "current location:" + new_latitude + ", " + new_longitude;
                    details = details + "\n" + "old location:" + old_latitude + ", " + old_longitude;
                    details = details + "\n" + "\n" + "total distance:" + total_distance + " m";
                    details = details + "\n" + "----------------------------------------------" + "\n";
                } else {

                    details = details + "\n" + "current location:" + new_latitude + ", " + new_longitude;
                    details = details + "\n" + "old location:" + old_latitude + ", " + old_longitude;
                    details = details + "\n" + "\n" + "total distance:" + total_distance + " m";
                    details = details + "\n" + "----------------------------------------------" + "\n";
                }

                mWalkingDistance.setText(details);

                old_latitude = new_latitude;
                old_longitude = new_longitude;

                System.out.println("**********this is LocationChanged**********" + 15000);
                Toast.makeText(startWalking.this, "current location" + new_latitude + new_longitude, Toast.LENGTH_LONG).show();
                Toast.makeText(startWalking.this, "old location" + old_latitude + old_longitude, Toast.LENGTH_LONG).show();
                Toast.makeText(startWalking.this, "LocationChanged" + Math.abs((float) old_latitude - (float) new_latitude) + "," + Math.abs((float) old_longitude - (float) new_longitude), Toast.LENGTH_LONG).show();

            }
            private float meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
                double earthRadius = 6371000; //meters
                double dLat = Math.toRadians(lat_b - lat_a);
                double dLng = Math.toRadians(lng_b - lng_a);
                double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                                Math.sin(dLng / 2) * Math.sin(dLng / 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                float dist = (float) (earthRadius * c);
                Toast.makeText(startWalking.this, "calculated distance" + dist + "," + Math.abs((float) old_longitude - (float) new_longitude), Toast.LENGTH_LONG).show();
                System.out.println("**********this is distance calculation**********" + dist);
                return dist;
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                System.out.println("**********this is StatusChanged**********" + 15000);
                Toast.makeText(startWalking.this, "StatusChanged" + Math.abs((float) old_latitude - (float) new_latitude) + "," + Math.abs((float) old_longitude - (float) new_longitude), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onProviderEnabled(String s) {
                System.out.println("**********this is ProviderEnabled**********" + 15000);
                Toast.makeText(startWalking.this, "ProviderDisabled" + Math.abs((float) old_latitude - (float) new_latitude) + "," + Math.abs((float) old_longitude - (float) new_longitude), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onProviderDisabled(String s) {
                System.out.println("**********this is ProviderDisabled**********" + 15000);
                Toast.makeText(startWalking.this, "ProviderDisabled" + Math.abs((float) old_latitude - (float) new_latitude) + "," + Math.abs((float) old_longitude - (float) new_longitude), Toast.LENGTH_LONG).show();

            }
        });

    }

    public String calculate(long time) {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);

        return t;


    }

    @Override
    protected void onStart() {
        super.onStart();
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
                } else { //일시 중지

                    timeWhenStopped = mWalkingTime.getBase() - SystemClock.elapsedRealtime();
                    mWalkingTime.stop();
                    mStartWalking.setImageResource(R.drawable.start);
                    mRunning = false;


                }

                break;
            case R.id.stopbutton_startwalking:


                SharedPreferences prefs = getSharedPreferences("startwalking", MODE_PRIVATE);

                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("timetickin", String.valueOf(mTimetickin));
                editor.putInt("percent", mPercent);
                editor.putLong("time", time);

                editor.commit();
                finish();
                break;
            case R.id.cameraApp_startWalking:

                dispatchTakePictureIntent();
                break;


        }
    }

}