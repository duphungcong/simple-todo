package com.duphungcong.simpletodo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.duphungcong.simpletodo.R;
import com.duphungcong.simpletodo.models.TodoItem;
import com.duphungcong.simpletodo.utils.DatePickerUtil;

import java.util.Calendar;

// Fragment for add/edit TodoItem
public class TodoItemFragment extends Fragment {

    private TodoItem todoItem;
    private String action;
    private EditText etTitle;
    private DatePicker dpDueDate;

    ItemCommitListener itemCommitListener;

    // Defines the listener interface with a method passing back data result.
    public interface ItemCommitListener {
        void onCommitItem();
    }

    public TodoItemFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static TodoItemFragment newInstance(TodoItem todoItem) {

        Bundle args = new Bundle();

        TodoItemFragment fragment = new TodoItemFragment();
        args.putSerializable("todoItem", todoItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_item, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.btnAddItem).setVisible(false);
        menu.findItem(R.id.btnSaveItem).setVisible(true);
        menu.findItem(R.id.btnCancelItem).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actions when click on menu items
        switch (item.getItemId()) {
            case R.id.btnSaveItem:
                onSaveItem();
                return true;
            case R.id.btnCancelItem:
                onCancelItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        todoItem = (TodoItem) getArguments().getSerializable("todoItem");
        action = getArguments().getString("action");

        // Get fields from view
        // Title
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etTitle.setText(todoItem.title);
        etTitle.setSelection(todoItem.title.length());
        // Due-date
        dpDueDate = (DatePicker) view.findViewById(R.id.dpDueDate);
        DatePickerUtil.dateToDatePicker(todoItem.dueDate, dpDueDate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ItemCommitListener) {
            itemCommitListener = (ItemCommitListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement ItemCommitListener");
        }
    }

    public void onSaveItem() {
        Calendar c = Calendar.getInstance();
        todoItem.setDueDate(c.getTime());

        todoItem.setTitle(etTitle.getText().toString());
        todoItem.setDueDate(DatePickerUtil.datePickerToDate(dpDueDate));

        todoItem.save();

        itemCommitListener.onCommitItem();
    }

    public void onCancelItem() {
        itemCommitListener.onCommitItem();
    }
}
