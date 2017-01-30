package com.example.rinko.myepidroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rinko on 31/01/2015.
 */
public class TrombiAdapter extends BaseAdapter{
    LayoutInflater layoutInflater;
    private  List listTrombi = null;

    public TrombiAdapter(Context context, List listTrombi) {
        this.listTrombi = listTrombi;
        layoutInflater = LayoutInflater.from(context);
        this.listTrombi = listTrombi;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listTrombi.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listTrombi.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    static class ViewHolder {
        TextView nomView;
        ImageView pictureView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_trombi_view, null);
            holder = new ViewHolder();
            // initialisation des vues
            holder.nomView = (TextView) convertView.findViewById(R.id.Trombi_Names);
            holder.pictureView = (ImageView) convertView
                    .findViewById(R.id.Trombi_Photos);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // affchier les données convenablement dans leurs positions
        Trombi temp;
        temp = (Trombi) listTrombi.get(position);
        holder.nomView.setText("                " + temp.getName());

        holder.pictureView.setImageBitmap(temp.getPhoto());
        return convertView;

    }

}
