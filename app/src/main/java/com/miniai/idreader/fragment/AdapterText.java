package com.miniai.idreader.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miniai.idreader.R;
import com.miniai.idsdk.IdParser;

import java.util.ArrayList;

public class AdapterText extends BaseAdapter {
    Context context;
    String countryList[];
    int flags[];
    LayoutInflater inflter;

    ArrayList<IdParser.TextNode> m_list;

    public AdapterText(Context ctx) {
        this.context = ctx;
        inflter = (LayoutInflater.from(ctx));
        m_list = IdParser.GetInstance().get_text_all();
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
        View view = inflter.inflate(R.layout.list_item_text, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        TextView textView1 = (TextView) view.findViewById(R.id.textView1);
        IdParser.TextNode attr = m_list.get(position);
//        textView1.setText(attr.source + " : " + attr.pageIndex);
        textView1.setText(attr.m_strFieldName);
        textView.setText(attr.m_strFieldValue);
        return view;
    }
}
