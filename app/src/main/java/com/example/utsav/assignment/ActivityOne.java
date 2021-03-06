package com.example.utsav.assignment;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.IllegalFormatException;


public class ActivityOne extends ActionBarActivity {
    private Intent intent;
    public static MyHandler sHandler;
    private boolean flag;
    private Button mServiceButton;
    class MyHandler extends Handler {

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            if(Utils.isNotNullOrEmpty(msg))
            switch (msg.what) {
                case Constants.MESSAGE_WHAT_START_SERVICE:
                    if(Utils.isNotNullOrEmpty(mServiceButton))
                        mServiceButton.setText(getResources().getString(R.string.stop_service_button));
                    flag = true;
                    break;
                case Constants.MESSAGE_WHAT_STOP_SERVICE:
                    if(Utils.isNotNullOrEmpty(mServiceButton))
                        mServiceButton.setText(getResources().getString(R.string.start_service_button));
                    flag = false;
                    break;
                default:
                    throw new RuntimeException("MESSAGE.what value not supported");
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_one);
        if (savedInstanceState != null) {
            flag = savedInstanceState.getBoolean(Constants.KEY_FLAG, false);
        }
        mServiceButton=(Button) findViewById(R.id.buttonStartService);
        sHandler = new MyHandler(getMainLooper());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_one, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!flag)
            mServiceButton.setText(getResources().getString(R.string.start_service_button));
        else
            mServiceButton.setText(getResources().getString(R.string.stop_service_button));
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.buttonGetContacts:
                intent = new Intent(this, ActivityTwo.class);
                startActivity(intent);
                break;
            case R.id.buttonGetApps:
                intent = new Intent(this, ActivityThree.class);
                startActivity(intent);
                break;
            case R.id.buttonStartService:
                intent = new Intent(this, LoggerService.class);
                if (!flag)
                    intent.setAction(Constants.INTENT_ACTION_START_SERVICE);
                else
                    intent.setAction(Constants.INTENT_ACTION_STOP_SERVICE);
                if(!Utils.isNotNullOrEmpty(startService(intent)))
                    Toast.makeText(this,"service does not exist",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.KEY_FLAG, flag);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putBoolean(Constants.KEY_FLAG, flag);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
