package com.duphungcong.simpletodo.models;

import com.duphungcong.simpletodo.SimpleTodoDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Table(database = SimpleTodoDatabase.class)
public class TodoItem extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String title;

    @Column
    public Date dueDate;

    public TodoItem() {
        this.title = "";

        Calendar c = Calendar.getInstance();
        this.dueDate = c.getTime();
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
}
