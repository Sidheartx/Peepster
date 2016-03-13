package com.parse.anyphone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siddharth on 3/13/16.
 */

    public class FeedsGridAdapter extends BaseAdapter {
        // Declare Variables
        Context context;
        LayoutInflater inflater;
        ImageLoader imageLoader;
        private List<ParseFeeds> feedsarraylist = null;
        private ArrayList<ParseFeeds> arraylist;
        public FeedsGridAdapter(Context context, List<ParseFeeds> feedsarraylist) {
            this.context = context;
            this.feedsarraylist = feedsarraylist;
            inflater = LayoutInflater.from(context);
            this.arraylist = new ArrayList<ParseFeeds>();
            this.arraylist.addAll(feedsarraylist);
            imageLoader = new ImageLoader(context);
        }

        @Override
        public int getCount() {
            return feedsarraylist.size();
        }

        @Override
        public Object getItem(int position) {
            return feedsarraylist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.feeds_image, null);
                // Locate the ImageView in gridview_item.xml
                holder.feedimages = (ImageView) view.findViewById(R.id.feedImages);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            } view.setVisibility(View.GONE);
            // Load image into GridView
            imageLoader.DisplayImage(feedsarraylist.get(position).getImage(),
                    holder.feedimages);
            // Capture GridView item click
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // Send single item click data to SingleItemView Class
                    Intent intent = new Intent(context, SingleItemView.class);
                    // Pass all data phone
                    intent.putExtra("filters", feedsarraylist.get(position)
                            .getImage());
                    context.startActivity(intent);
                }
            });
            return view;
        }

        public class ViewHolder {
            ImageView feedimages;
            VideoView feedvideos;
        }
    }


