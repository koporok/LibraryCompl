package com.example.Library;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class Adapter extends ArrayAdapter<List> {

    private final Context context;
    private  final List<Book> itemName;

    public Adapter(Context context, int resource, List<Book> itemName) {
        super(context, resource);
        this.context = context;
        this.itemName = itemName;
    }
    @Override
    public int getCount() {
        return itemName.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setTextSize(20);
        textView.setText(itemName.get(position).getTitle());
        return view;
    }

    @Override
    public List<String> getItem(int position) {
        return Collections.singletonList(itemName.get(position).getTitle());
    }

}