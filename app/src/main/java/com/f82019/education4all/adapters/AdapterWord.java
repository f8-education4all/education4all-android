package com.f82019.education4all.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.f82019.education4all.R;
import com.f82019.education4all.models.Dictionary;

import java.util.List;

public class AdapterWord extends ArrayAdapter<Dictionary> {

    private Context context;
    private List<Dictionary> words;
    LayoutInflater inflater ;

    public AdapterWord(Context context, int resource, List<Dictionary> objects) {
        super(context, resource, objects);

        this.context = context;

        inflater = LayoutInflater.from(context);

        words = objects;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Dictionary getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.adapter_word, parent, false);

            holder.tv_word = convertView.findViewById(R.id.adapter_word);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Dictionary word = words.get(position);
        holder.tv_word.setText(word.getWord());

        return convertView;
    }

    private class ViewHolder {
        public TextView tv_word;
    }
}
