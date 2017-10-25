package com.example.gin.androiduploadretrievevolley.ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.gin.androiduploadretrievevolley.IMAGEVOLLEYREQUEST.imageVolleyRequest;
import com.example.gin.androiduploadretrievevolley.R;

import java.util.ArrayList;

/**
 * Created by Gin on 10/26/2017.
 */

public class retrieveImageAdapter extends BaseAdapter {

    private ImageLoader imageLoader;

    private Context context;

    private ArrayList<String> image;

    public retrieveImageAdapter(Context context, ArrayList<String> image){
        this.context = context;
        this.image = image;
    }

    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public Object getItem(int position) {
        return image.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        grid = inflater.inflate(R.layout.activity_retrieveitem_image, null);

        NetworkImageView thumbNail = (NetworkImageView) grid.findViewById(R.id.imageViewHero);
        imageLoader = imageVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(image.get(position), ImageLoader.getImageListener(thumbNail, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        thumbNail.setImageUrl(image.get(position), imageLoader);

        return grid;
    }
}
