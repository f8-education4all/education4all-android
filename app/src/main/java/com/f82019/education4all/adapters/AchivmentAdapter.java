package com.f82019.education4all.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.f82019.education4all.R;
import com.f82019.education4all.models.Archivment;

import java.util.List;
import java.util.Locale;

public class AchivmentAdapter extends ArrayAdapter<Archivment> {

    private Context context;
    private List<Archivment> archivments;
    LayoutInflater inflater ;

    public AchivmentAdapter( Context context, int resource,  List<Archivment> objects) {
        super(context, resource, objects);

        this.archivments = objects;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    
    @Override
    public Archivment getItem(int position) {
        return super.getItem(position);
    }

    
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_archivment, parent, false);

            holder.img = convertView.findViewById(R.id.adapter_archivment_img);
            holder.tv_name = convertView.findViewById(R.id.adapter_archivment_name);
            holder.tv_desc = convertView.findViewById(R.id.adapter_archivment_desc);
            holder.tv_status = convertView.findViewById(R.id.adapter_archivment_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Archivment archivment = archivments.get(position);

        //holder.img.setImageResource(archivment.getLogo());
        holder.tv_name.setText(archivment.getArchivment());
        holder.tv_desc.setText(archivment.getDescription());
        holder.tv_status.setText(String.format(Locale.ENGLISH, "LIVE RUN"));


        return convertView;
    }


    private class ViewHolder {
        public ImageView img;
        public TextView tv_name, tv_desc, tv_status;
    }
}
