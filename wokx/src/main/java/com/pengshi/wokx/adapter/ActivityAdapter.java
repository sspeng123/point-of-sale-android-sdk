package com.pengshi.wokx.adapter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.pengshi.wokx.R;
/**
 * @author shipeng
 * 1/11/24
 */
public class ActivityAdapter extends BaseAdapter {
    private final Context context;
    private final ActivityInfo[] activityInfos;

    public ActivityAdapter(Context context, ActivityInfo[] activityInfos) {
        this.context = context;
        this.activityInfos = activityInfos;
    }

    @Override
    public int getCount() {
        return activityInfos.length;
    }

    @Override
    public Object getItem(int i) {
        return activityInfos[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_activity, null);
        }

        ActivityInfo activityInfo = activityInfos[i];

        ImageView iconImage = (ImageView) view.findViewById(R.id.icon);
        int iconResource = activityInfo.getIconResource();
        if (iconResource == 0) {
            iconResource = activityInfo.applicationInfo.icon;
        }
        iconImage.setImageResource(iconResource);

        TextView descriptionText = (TextView) view.findViewById(R.id.description);
        if (activityInfo.labelRes != 0) {
            descriptionText.setText(activityInfo.labelRes);
        } else {
            descriptionText.setText(activityInfo.nonLocalizedLabel);
        }

        return view;
    }
}
