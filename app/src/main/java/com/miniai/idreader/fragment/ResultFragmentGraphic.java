package com.miniai.idreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.miniai.idreader.R;

/* Fragment used as page 1 */
public class ResultFragmentGraphic extends Fragment {

    AdapterImage adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result_graphic, container, false);
        ListView simpleList = (ListView) rootView.findViewById(R.id.listView);
        adapter = new AdapterImage(getContext());
        simpleList.setAdapter(adapter);
        return rootView;
    }
}