package com.duphungcong.simpletodo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.duphungcong.simpletodo.R;
import com.duphungcong.simpletodo.adapters.TodoItemsAdapter;
import com.duphungcong.simpletodo.models.TodoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

public class TodoItemsListFragment extends Fragment {
    ArrayList<TodoItem> items;
    TodoItemsAdapter itemsAdapter;
    ListView lvItems;

    ItemEditListener itemEditListener;

    // Defines the listener interface with a method passing back data result to TodoItem Fragment
    public interface ItemEditListener {
        void onOpenEditForm(TodoItem selectedItem);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_items_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvItems = (ListView)view.findViewById(R.id.lvItems);

        // Read items from database
        readItems();

        // Setup ListView adapter
        Context context = getActivity().getApplicationContext();
        itemsAdapter = new TodoItemsAdapter(context, items);
        lvItems.setAdapter(itemsAdapter);

        // Listen events from ListView
        setupListViewListener();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ItemEditListener) {
            itemEditListener = (ItemEditListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement ItemEditListener");
        }
    }

    // Read list items from database
    private void readItems() {
        try {
            items = (ArrayList<TodoItem>) SQLite.select().from(TodoItem.class).queryList();
        } catch (Exception e) {
            items = new ArrayList<>();
        }
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
                // Get selected item in list item
                TodoItem selectedItem = itemsAdapter.getItem(position);
                // Send selected item to edit form
                itemEditListener.onOpenEditForm(selectedItem);

            }
        });

    }
}
