package com.duphungcong.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<TodoItem> items;
    TodoItemsAdapter itemsAdapter;
    ListView lvItems;
    static final int EDIT_REQUEST_CODE = 20;

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
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String newText = etNewItem.getText().toString();

        TodoItem newItem = new TodoItem();
        newItem.setTitle(newText);
        newItem.save();

        itemsAdapter.add(newItem);
        etNewItem.setText("");
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
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);

                TodoItem selectedItem = itemsAdapter.getItem(position);

                i.putExtra("editedItem", selectedItem);
                i.putExtra("itemPosition", position);

                startActivityForResult(i, EDIT_REQUEST_CODE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                // Data from edit activity
                TodoItem editedItem = (TodoItem)data.getSerializableExtra("editedItem");
                int itemPosition = data.getExtras().getInt("itemPosition");

                // Update ListView
                items.set(itemPosition, editedItem);
                itemsAdapter.notifyDataSetChanged();

                // Write-down edited item in database
                editedItem.save();
            }
        }
    }
}
