package com.duphungcong.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

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
        //readItems();
        items = new ArrayList<>();
        itemsAdapter = new TodoItemsAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        TodoItem newItem = new TodoItem(1, "one");
        items.add(newItem);
        //setupListViewListener();
    }


    public void onAddItem(View v) {
        EditText etNewitem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewitem.getText().toString();
        itemsAdapter.add(new TodoItem(10, itemText));
        etNewitem.setText("");
        //writeItems();
    }

    /*private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                String itemText = ((TextView)view).getText().toString();
                i.putExtra("itemText", itemText );
                i.putExtra("itemPosition", position);
                startActivityForResult(i, EDIT_REQUEST_CODE);
            }
        });
    }

    private void readItems() {
        File filesDir = getFilesDir().getAbsoluteFile();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                String itemText = data.getExtras().getString("itemText");
                int itemPosition = data.getExtras().getInt("itemPosition");
                items.set(itemPosition, itemText);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }*/
}
