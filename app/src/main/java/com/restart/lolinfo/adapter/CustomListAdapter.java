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
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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

        NetworkImageView item_1 = (NetworkImageView) convertView
                .findViewById(R.id.item_1);
        NetworkImageView item_2 = (NetworkImageView) convertView
                .findViewById(R.id.item_2);
        NetworkImageView item_3 = (NetworkImageView) convertView
                .findViewById(R.id.item_3);
        NetworkImageView item_4 = (NetworkImageView) convertView
                .findViewById(R.id.item_4);
        NetworkImageView item_5 = (NetworkImageView) convertView
                .findViewById(R.id.item_5);
        NetworkImageView item_6 = (NetworkImageView) convertView
                .findViewById(R.id.item_6);
        NetworkImageView sum_1 = (NetworkImageView) convertView
                .findViewById(R.id.sum_1);
        NetworkImageView sum_2 = (NetworkImageView) convertView
                .findViewById(R.id.sum_2);

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

        final String url = "http://ddragon.leagueoflegends.com/cdn/6.18.1/img/item/";
        final String sum = "http://ddragon.leagueoflegends.com/cdn/6.18.1/img/spell/";
        int[] items = m.getItems();

        item_1.setImageUrl(url + items[0] + ".png", imageLoader);
        item_2.setImageUrl(url + items[1] + ".png", imageLoader);
        item_3.setImageUrl(url + items[2] + ".png", imageLoader);
        item_4.setImageUrl(url + items[3] + ".png", imageLoader);
        item_5.setImageUrl(url + items[4] + ".png", imageLoader);
        item_6.setImageUrl(url + items[5] + ".png", imageLoader);

        if (m.getSpell1() != null && !m.getSpell1().equals("3637")) {
            sum_1.setImageUrl(sum + m.getSpell1() + ".png", imageLoader);
        } else {
            sum_1.setImageUrl(url + "3637" + ".png", imageLoader);
        }
        if (m.getSpell2() != null && !m.getSpell2().equals("3637")) {
            sum_2.setImageUrl(sum + m.getSpell2() + ".png", imageLoader);
        } else {
            sum_2.setImageUrl(url + "3637" + ".png", imageLoader);
        }

        queue.setText(m.getQueue());
        champion.setText(m.getChampion());
        lane_role.setText(m.getStats());
        time.setText(Convert.getDate(m.getTime()));

        return convertView;
    }

}
