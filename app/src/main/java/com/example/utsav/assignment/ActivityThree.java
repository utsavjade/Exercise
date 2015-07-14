package com.example.utsav.assignment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.utsav.assignment.Beans.ListItemHolder;
import com.example.utsav.assignment.view.ListAdapter;

import java.util.ArrayList;
import java.util.List;


public class ActivityThree extends ActionBarActivity {
    private ArrayList<ListItemHolder> mListItemHoldersArrayList;
    private List<ApplicationInfo> mApplicationInfoList;
    private PackageManager mPackageManager;
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_three);
        mListItemHoldersArrayList = new ArrayList<>();
        mPackageManager = getPackageManager();
        if (Utils.isNotNullOrEmpty(mPackageManager))
            mApplicationInfoList = mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        mListAdapter = new ListAdapter(this, mListItemHoldersArrayList);
        if (Utils.isNotNullOrEmpty(mApplicationInfoList))
            for (ApplicationInfo info : mApplicationInfoList) {
                ListItemHolder holder = new ListItemHolder();
                holder.bitmap = ((BitmapDrawable) info.loadIcon(mPackageManager)).getBitmap();
                holder.labelMainString = (String) info.loadLabel(mPackageManager);
                holder.labelSubString = (String) info.packageName;
                mListItemHoldersArrayList.add(holder);
            }
        ((ListView) findViewById(R.id.listViewApps)).setAdapter(mListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_three, menu);
        return true;
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
