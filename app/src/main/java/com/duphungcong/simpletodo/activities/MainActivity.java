package com.duphungcong.simpletodo.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.duphungcong.simpletodo.R;
import com.duphungcong.simpletodo.adapters.TodoItemsAdapter;
import com.duphungcong.simpletodo.fragments.TodoItemDialogFragment;
import com.duphungcong.simpletodo.models.TodoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TodoItemDialogFragment.ItemDialogListener {
    ArrayList<TodoItem> items;
    TodoItemsAdapter itemsAdapter;
    ListView lvItems;
    TodoItemDialogFragment todoItemDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);

        // Read items from database
        readItems();

        // Setup ListView adapter
        itemsAdapter = new TodoItemsAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

        // Listen events from ListView
        setupListViewListener();
    }

    // Add new item when click on ADD ITEM button
    public void onAddItem(View v) {
        TodoItem newItem = new TodoItem();

        FragmentManager fm = getSupportFragmentManager();
        todoItemDialogFragment = TodoItemDialogFragment.newInstance(newItem, "new");
        todoItemDialogFragment.show(fm, "fragment_todo_item");
    }

    private void setupListViewListener() {
        // Press and hold to delete item
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TodoItem selectedItem = itemsAdapter.getItem(position);

                // Update ListView
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();

                // Delete selected item from database
                selectedItem.delete();

                return true;
            }
        });

        // Click to edit item
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TodoItem selectedItem = itemsAdapter.getItem(position);

                FragmentManager fm = getSupportFragmentManager();
                todoItemDialogFragment = TodoItemDialogFragment.newInstance(selectedItem, "edit");
                todoItemDialogFragment.show(fm, "fragment_todo_item");
            }
        });

    }

    // Read items from database
    private void readItems() {
        try {
            items = (ArrayList<TodoItem>) SQLite.select().from(TodoItem.class).queryList();
        } catch (Exception e) {
            items = new ArrayList<>();
        }
    }

    @Override
    public void onFinishItemDialog(TodoItem newItem, String action) {
        newItem.save();
        if(action == "new") {
            itemsAdapter.add(newItem);
        } else { // action = "edit"
            itemsAdapter.notifyDataSetChanged();
        }
    }

    public void onSaveItem(View v) {
        todoItemDialogFragment.onSaveItem(v);
    }
}
