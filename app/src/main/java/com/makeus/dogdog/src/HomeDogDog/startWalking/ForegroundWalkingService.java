package com.makeus.dogdog.src.HomeDogDog.startWalking;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.makeus.dogdog.R;


public class ForegroundWalkingService extends Service {

    Thread mStartWalking;

    int mCount =0 ;
    private void startForegroundService() {                       // 포그라운드 서비스 실행
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        builder.setSmallIcon(R.drawable.default_profile_image);
        builder.setContentTitle("독독 ");
        builder.setContentText("강아지 산책 중입니다 :)");

        Intent notificationIntent = new Intent(this, StartWalking.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        https://stackoverflow.com/questions/17980245/service-and-chronometer-synchronization 싱크 맞추기
        builder.setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {     // 오레오 버전 이상 노티피케이션 알림 설정
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(new NotificationChannel("default", "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        startForeground(1, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        if(intent==null) //시스템에서 죽이고 살려줬을 떄는 null
        {

            startForegroundService();

        }
        if ("startForeground".equals(intent.getAction())) {
            startForegroundService();
        }else if("stopForeground".equals(intent.getAction()))
        {

            stopForegroundService();
        }

//         if (mStartWalking == null) {
//            mStartWalking = new Thread("ServiceThread") {
//                @Override//여기에 내가 원하는 서비스 로직을 넣으면 됌.
//                public void run() {
//                    for (int i = 0; i < 1000; i++) {
//                        try {
//                            mCount++;
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            break;
//                        }
//                        Log.d("My Service", "서비스 동작 중 " + mCount);
//                    }
//                }
//            };
//            mStartWalking.start();
//        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void stopForegroundService() {

        if(mStartWalking !=null)
        {
            mStartWalking.interrupt();
        }
        stopForeground(true);
        stopSelf();


    }



}
