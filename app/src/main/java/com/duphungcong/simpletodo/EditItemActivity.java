package com.duphungcong.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    String itemText;
    int itemPosition;
    EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemText = getIntent().getStringExtra("itemText");
        itemPosition = getIntent().getIntExtra("itemPosition", -1);

        etEditItem = (EditText)findViewById(R.id.etEditItem);
        etEditItem.setText(itemText);
        etEditItem.setSelection(itemText.length());
    }

    public void onSaveItem(View v) {
        Intent data = new Intent();
        data.putExtra("itemText", etEditItem.getText().toString());
        data.putExtra("itemPosition", itemPosition);
        setResult(RESULT_OK, data);
        finish();
    }
}
