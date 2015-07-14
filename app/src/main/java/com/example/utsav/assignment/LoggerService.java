package com.example.utsav.assignment;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.widget.Button;

public class LoggerService extends Service {
    ActivityManager mActivityManager;
    Boolean isRunning=false;
    Button button;
    Context mContext;

    public LoggerService() {
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mActivityManager= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    }
    private void sendMessageToActivityOne(int what){
        Message message=new Message();
        message.what=what;
        ActivityOne.sHandler.sendMessage(message);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendMessageToActivityOne(Constants.MESSAGE_WHAT_START_SERVICE);
        switch (intent.getAction()){
            case Constants.INTENT_ACTION_START_SERVICE:

                if(!isRunning) {
                    isRunning=true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isRunning) {
                                ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
                                Logger.d("pkg name:" + topActivity.getPackageName());
                                try {
                                    Thread.sleep(10000l);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            //sendMessageToActivityOne(Constants.MESSAGE_WHAT_STOP_SERVICE);
                            stopSelf();
                        }
                    }).start();

                }
                break;
            case Constants.INTENT_ACTION_STOP_SERVICE:

                synchronized (isRunning) {
                    isRunning = false;
                }
                stopSelf();

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //if(isRunning)
        sendMessageToActivityOne(Constants.MESSAGE_WHAT_STOP_SERVICE);
        if(isRunning)
            startService(new Intent(Constants.INTENT_ACTION_START_SERVICE));
        super.onDestroy();
    }
}
