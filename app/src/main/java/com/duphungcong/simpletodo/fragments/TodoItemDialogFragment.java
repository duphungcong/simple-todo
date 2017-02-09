package com.duphungcong.simpletodo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.duphungcong.simpletodo.R;
import com.duphungcong.simpletodo.models.TodoItem;
import com.duphungcong.simpletodo.utils.DatePickerUtil;

import java.util.Calendar;

// Fragment for add/edit TodoItem
public class TodoItemDialogFragment extends DialogFragment {

    private TodoItem newItem;
    private String action;
    private EditText etTitle;
    private DatePicker dpDueDate;

    // Defines the listener interface with a method passing back data result.
    public interface ItemDialogListener {
        void onFinishItemDialog(TodoItem newItem, String action);
    }

    public TodoItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static TodoItemDialogFragment newInstance(TodoItem newItem, String action) {

        Bundle args = new Bundle();

        TodoItemDialogFragment fragment = new TodoItemDialogFragment();
        args.putSerializable("newItem", newItem);
        args.putString("action", action); // set action = "new" to add new item, set action = "edit" to edit exist item
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newItem = (TodoItem) getArguments().getSerializable("newItem");
        action = getArguments().getString("action");

        // Get fields from view
        // Title
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etTitle.setText(newItem.title);
        etTitle.setSelection(newItem.title.length());
        // Due-date
        dpDueDate = (DatePicker) view.findViewById(R.id.dpDueDate);
        DatePickerUtil.dateToDatePicker(newItem.dueDate, dpDueDate);
    }

    public void onSaveItem(View v) {
        ItemDialogListener listener = (ItemDialogListener) getActivity();

        Calendar c = Calendar.getInstance();
        newItem.setDueDate(c.getTime());

        newItem.setTitle(etTitle.getText().toString());
        newItem.setDueDate(DatePickerUtil.datePickerToDate(dpDueDate));

        listener.onFinishItemDialog(newItem, action);
        dismiss();
    }
}
