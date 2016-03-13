package com.parse.anyphone;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siddharth on 3/13/16.
 */
public class FeedsFragment extends Fragment {
    public static final String TAG = FeedsFragment.class.getSimpleName();
    GridView gridView;
    List<ParseObject> ob;
    FeedsGridAdapter Adapter;
    View rootView;
    private List<ParseFeeds> feedsarraylist = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.feeds_layout,
                container, false);
        new RemoteDataTask().execute();
        return rootView;
    }
    private class RemoteDataTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... Params) {
            feedsarraylist = new ArrayList<ParseFeeds>();
            try {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("PlaceFilters");
                query.orderByDescending("createdAt");
                //query.whereWithinKilometers()
                ob = query.find();
                for (ParseObject feeds : ob) {
                    ParseFile image = (ParseFile) feeds.get("Filters");
                    ParseFeeds map = new ParseFeeds();
                    Uri fileUri = Uri.parse(image.getUrl());
                    map.setImage(image.getUrl());
                    feedsarraylist.add(map);
                    Intent intent = new Intent(getActivity(), FeedsGridAdapter.class);
                    intent.setData(fileUri);
                }
            } catch (ParseException e) {
                Log.e("ParseException", "parse: " + e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            gridView = (GridView)rootView.findViewById(R.id.gridview);
            Adapter = new FeedsGridAdapter (FeedsFragment.this.getActivity(),feedsarraylist);
            gridView.setAdapter(Adapter);
        }
    }
}