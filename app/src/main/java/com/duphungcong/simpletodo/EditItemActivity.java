package com.duphungcong.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    TodoItem editedItem;
    int itemPosition;
    EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editedItem = (TodoItem)getIntent().getSerializableExtra("editedItem");
        itemPosition = getIntent().getIntExtra("itemPosition", -1);

        etTitle = (EditText)findViewById(R.id.etTitle);

        String itemTitle = editedItem.title;

        etTitle.setText(itemTitle);
        etTitle.setSelection(itemTitle.length());
    }

    public void onSaveItem(View v) {
        Intent data = new Intent();
        editedItem.setTitle(etTitle.getText().toString());
        data.putExtra("editedItem", editedItem);
        data.putExtra("itemPosition", itemPosition);
        setResult(RESULT_OK, data);
        finish();
    }
}
