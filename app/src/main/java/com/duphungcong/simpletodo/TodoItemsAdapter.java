package com.duphungcong.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {
    public TodoItemsAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem currentItem = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }

        TextView tvId = (TextView)convertView.findViewById(R.id.tvId);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);

        tvId.setText("" + currentItem.id);
        tvTitle.setText(currentItem.title);

        return convertView;
    }
}
