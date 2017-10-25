package com.example.gin.androiduploadretrievevolley.ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gin.androiduploadretrievevolley.R;

import java.util.ArrayList;

/**
 * Created by Gin on 10/26/2017.
 */

public class retrieveTextAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<String> name;
    private ArrayList<String> age;

    public retrieveTextAdapter(Context context, ArrayList<String> name, ArrayList<String> age){
        this.context = context;
        this.name = name;
        this.age = age;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View listView;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = inflater.inflate(R.layout.activity_retrieveitem_text, parent, false);

        TextView names = (TextView) listView.findViewById(R.id.txtName);
        TextView ages = (TextView) listView.findViewById(R.id.txtAge);

        names.setText(name.get(position));
        ages.setText(age.get(position));

        return listView;
    }
}
