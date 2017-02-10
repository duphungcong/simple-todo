package com.duphungcong.simpletodo.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.duphungcong.simpletodo.R;
import com.duphungcong.simpletodo.fragments.TodoItemFragment;
import com.duphungcong.simpletodo.fragments.TodoItemsListFragment;
import com.duphungcong.simpletodo.models.TodoItem;

public class MainActivity
        extends AppCompatActivity
        implements TodoItemsListFragment.ItemEditListener, TodoItemFragment.ItemCommitListener {
    
    Toolbar mainToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the toolbar view inside the activity layout
        mainToolBar = (Toolbar) findViewById(R.id.mainToolBar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(mainToolBar);

        // Sets the fragment to list todo items
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        TodoItemsListFragment todoItemsListFragment = new TodoItemsListFragment();
        ft.add(R.id.fragment_container, todoItemsListFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.btnSaveItem).setVisible(false);
        menu.findItem(R.id.btnCancelItem).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actions when click on menu items
        switch (item.getItemId()) {
            case R.id.btnAddItem:
                onAddItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Open new form with TodoItemFragment
    public void onAddItem() {
        TodoItem newItem = new TodoItem();
        TodoItemFragment todoItemFragment = TodoItemFragment.newInstance(newItem);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, todoItemFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    // Open edit form with selected item sent from TodoItemsListFragment
    @Override
    public void onOpenEditForm(TodoItem selectedItem) {
        TodoItemFragment todoItemFragment = TodoItemFragment.newInstance(selectedItem);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, todoItemFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    // Open list item with TodoItemsListFragment
    @Override
    public void onCommitItem() {
        TodoItemsListFragment todoItemsListFragment = new TodoItemsListFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, todoItemsListFragment);
        ft.commit();
    }
}
