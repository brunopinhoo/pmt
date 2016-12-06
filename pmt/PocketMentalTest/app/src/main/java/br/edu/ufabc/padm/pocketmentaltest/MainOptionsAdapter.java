package br.edu.ufabc.padm.pocketmentaltest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bpinh on 11/11/2016.
 */

public class MainOptionsAdapter extends BaseAdapter {

    private Context context;
    private String[] items;
    private final Integer[] icons = {
            R.drawable.pacientes,
            R.drawable.teste,
            R.drawable.exames
    };

    public MainOptionsAdapter(Context c) {
        context = c;
        items = c.getResources().getStringArray(R.array.main_options_items);

    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView icon;
        TextView  item;
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.main_options_item, null);

        item = (TextView)  convertView.findViewById(R.id.main_options_item_label);
        icon = (ImageView) convertView.findViewById(R.id.main_options_item_icon);
        ViewGroup.LayoutParams iconParams = icon.getLayoutParams();

        item.setText(items[position]);
        icon.setImageResource(icons[position]);
        icon.setPadding(0, 0, 20, 0);
        iconParams.width = 70;
        iconParams.height = 70;

        return convertView;
    }
}
