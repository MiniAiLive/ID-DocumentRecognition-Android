package com.miniai.idreader.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miniai.idreader.R;
import com.miniai.idsdk.IdParser;

import java.util.ArrayList;

public class AdapterImage extends BaseAdapter {
    Context context;
    String countryList[];
    int flags[];
    LayoutInflater inflter;

    ArrayList<IdParser.ImageNode> m_list;

    public AdapterImage(Context ctx) {
        this.context = ctx;
        inflter = (LayoutInflater.from(ctx));
        m_list = IdParser.GetInstance().get_image_all();
    }

    @Override
    public int getCount() {
        return m_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflter.inflate(R.layout.list_item_graphic, null);
        TextView textView = (TextView)view.findViewById(R.id.textView2);
        textView.setText(m_list.get(position).m_strFieldName);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        icon.setImageBitmap(m_list.get(position).m_bitmap);
        return view;
    }
}
