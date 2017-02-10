package com.duphungcong.simpletodo;

import com.duphungcong.simpletodo.models.TodoItem;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

@Database(name = SimpleTodoDatabase.NAME, version = SimpleTodoDatabase.VERSION)
public class SimpleTodoDatabase {
    public static final String NAME = "SimpleTodoDatabase";
    public static final int VERSION = 3;

    @Migration(version = 3, database = SimpleTodoDatabase.class)
    public static class AddDueDateToTodoItemMigration extends AlterTableMigration<TodoItem> {
        public AddDueDateToTodoItemMigration(Class<TodoItem> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "dueDate");
        }
    }
}
