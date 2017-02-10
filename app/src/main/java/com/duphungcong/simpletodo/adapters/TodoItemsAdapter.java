package com.duphungcong.simpletodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.duphungcong.simpletodo.R;
import com.duphungcong.simpletodo.models.TodoItem;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        // Get fields of view
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);

        // Set value for fields
        tvTitle.setText(currentItem.title);
        tvDueDate.setText(showDate(currentItem.dueDate));

        return convertView;
    }

    // Convert Date to String with string format MM dd, YYYY
    public String showDate(Date date) {
        String dateString;
        dateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
        return dateString;
    }
}
