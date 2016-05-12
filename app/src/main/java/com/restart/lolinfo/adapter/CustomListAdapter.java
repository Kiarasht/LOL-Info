package com.restart.lolinfo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.restart.lolinfo.R;
import com.restart.lolinfo.app.AppController;
import com.restart.lolinfo.model.Convert;
import com.restart.lolinfo.model.Match;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Match> matchItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Match> movieItems) {
        this.activity = activity;
        this.matchItems = movieItems;
    }

    @Override
    public int getCount() {
        return matchItems.size();
    }

    @Override
    public Object getItem(int location) {
        return matchItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView queue = (TextView) convertView.findViewById(R.id.queue);
        TextView champion = (TextView) convertView.findViewById(R.id.champion);
        TextView lane_role = (TextView) convertView.findViewById(R.id.lane_role);
        TextView time = (TextView) convertView.findViewById(R.id.time);

        Match m = matchItems.get(position);

        if (m.getWin()) {
            convertView.setBackgroundResource(R.drawable.list_row_selector);
        } else {
            convertView.setBackgroundResource(R.drawable.list_row_selector_lose);
        }

        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        queue.setText(m.getQueue());
        champion.setText(m.getChampion());
        lane_role.setText(m.getLane_role());
        time.setText(Convert.getDate(m.getTime()));

        return convertView;
    }

}
