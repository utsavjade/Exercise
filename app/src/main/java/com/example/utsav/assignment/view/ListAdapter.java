package com.example.utsav.assignment.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.utsav.assignment.Beans.ListItemHolder;
import com.example.utsav.assignment.Logger;
import com.example.utsav.assignment.R;
import com.example.utsav.assignment.Utils;

import java.util.ArrayList;

/**
 * Created by utsav on 13/7/15.
 */
public class ListAdapter extends BaseAdapter {
    private ArrayList<ListItemHolder> mListItemsArrayList;
    private Context mContext;
    private ViewHolder mViewHolder;

    public ListAdapter(Context context, ArrayList<ListItemHolder> listItemArrayList) {
        mListItemsArrayList = listItemArrayList;
        mContext = context;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView2;
    }

    @Override
    public int getCount() {
        return mListItemsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mListItemsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_layout, null);
            viewHolder = new ViewHolder();
            if (Utils.isNotNullOrEmpty(convertView)) {
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.text1);
                viewHolder.textView2 = (TextView) convertView.findViewById(R.id.text2);
                convertView.setTag(viewHolder);
            }
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        ListItemHolder listItemHolder = mListItemsArrayList.get(position);
        if (Utils.isNotNullOrEmpty(viewHolder)) {
            viewHolder.imageView.setImageBitmap(listItemHolder.bitmap == null ? BitmapFactory.decodeResource(mContext.getResources(), R.drawable.contact_def)
                    : listItemHolder.bitmap);
            viewHolder.textView.setText(listItemHolder.labelMainString == null ? "" : listItemHolder.labelMainString);
            viewHolder.textView2.setText(listItemHolder.labelSubString == null ? "" : listItemHolder.labelSubString);
        }
        return convertView;
    }
}
