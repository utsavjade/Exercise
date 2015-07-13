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

import java.util.ArrayList;

/**
 * Created by utsav on 13/7/15.
 */
public class ImgTextAdapter extends BaseAdapter {
    private ArrayList<ListItemHolder> mListItemsArrayList;
    private Context mContext;
    private ViewHolder mViewHolder;

    public ImgTextAdapter(Context context, ArrayList<ListItemHolder> listItemArrayList) {
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
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image);
            viewHolder.textView = (TextView) view.findViewById(R.id.text1);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.text2);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        ListItemHolder listItemHolder = mListItemsArrayList.get(position);
        viewHolder.imageView.setImageBitmap(listItemHolder.bitmap==null? BitmapFactory.decodeResource(mContext.getResources(),R.drawable.contact_def)
                                            :listItemHolder.bitmap);
        viewHolder.textView.setText(listItemHolder.text1==null?"":listItemHolder.text1);
        viewHolder.textView2.setText(listItemHolder.text2==null?"":listItemHolder.text2);
        return view;
    }
}
