package com.example.utsav.assignment;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.utsav.assignment.Beans.ListItemHolder;
import com.example.utsav.assignment.view.ListAdapter;

import java.util.ArrayList;


public class ActivityTwo extends ActionBarActivity {
    private final String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,};
    private final String[] mEmailProjection = {ContactsContract.CommonDataKinds.Email.ADDRESS};
    private ArrayList<ListItemHolder> mContactsListItemHolders;
    private ListAdapter mListAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_two);
        mContactsListItemHolders = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.contactsList);
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projection, null, null, null);
        if (Utils.isNotNullOrEmpty(cursor) && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ListItemHolder holder = new ListItemHolder();
                holder.labelMainString = cursor.getString(1);
                Cursor emailCur = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI
                        , mEmailProjection, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{cursor.getString(0)}, null);
                if (Utils.isNotNullOrEmpty(emailCur) && emailCur.getCount() > 0) {
                    emailCur.moveToNext();
                    holder.labelSubString = emailCur.getString(0);
                }
                Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, cursor.getInt(0));
                Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                Cursor imgcursor = getContentResolver().query(photoUri, new String[]{ContactsContract.Contacts.Photo.PHOTO}
                        , null, null, null);
                if (Utils.isNotNullOrEmpty(imgcursor)&&imgcursor.getCount() > 0) {
                    imgcursor.moveToNext();
                    holder.bitmap = BitmapFactory.decodeByteArray(imgcursor.getBlob(0), 0, imgcursor.getBlob(0).length);
                }
                mContactsListItemHolders.add(holder);
            }
        }
        mListAdapter = new ListAdapter(this, mContactsListItemHolders);
        mListView.setAdapter(mListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_two, menu);
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
