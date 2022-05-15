package dev.sammi.task;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TaskDataStore extends SQLiteOpenHelper {
    static final private String DB_NAME = "todoAppDB";
    static final private String DB_TABLE = "tasks";
    static final private int DB_VER = 1;

    Context _ctx;
    SQLiteDatabase _db;

    public TaskDataStore(Context ctx) {
        super(ctx, DB_NAME,null, DB_VER);
        _ctx = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSQL = "CREATE TABLE " + DB_TABLE + "(id integer primary key autoincrement, task text, completed integer);";
        sqLiteDatabase.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropSql = "DROP TABLE if exists " + DB_TABLE + ";";
        sqLiteDatabase.execSQL(dropSql);
        onCreate(sqLiteDatabase);
    }

    public void insert(TaskEntity taskEntity) {
        String insertSql = "INSERT INTO " + DB_TABLE + "(task, completed) VALUES (" + "'" + taskEntity.getTask() + "', '0');";
        _db = getWritableDatabase();
        _db.execSQL(insertSql);
    }

    public ArrayList<TaskEntity> findAll() {
        String selectSql = "SELECT * FROM " + DB_TABLE + " ORDER BY id DESC;";
        _db = getReadableDatabase();
        Cursor cursor = _db.rawQuery(selectSql, null);

        ArrayList<TaskEntity> taskEntities = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String task = cursor.getString(1);
            int completed = cursor.getInt(2);

            TaskEntity taskEntity = new TaskEntity(id, task);
            if (completed == 0) {
                taskEntity.setStatus("Unfinished");
                taskEntity.setCompleted(false);
            }else {
                taskEntity.setStatus("Finished");
                taskEntity.setCompleted(true);
            }
            taskEntities.add(taskEntity);
        }
        return taskEntities;
    }

    public void updateStatusById(int id, int completed) {
        String updateSql = "UPDATE " + DB_TABLE + " SET completed = " + "'" + completed + "'" + " WHERE id = " + "'" + id + "';";
        _db = getWritableDatabase();
        _db.execSQL(updateSql);
    }

    public void deleteById(int id) {
        String deleteSql = "DELETE FROM " + DB_TABLE + " WHERE id = " + "'" + id + "';";
        _db = getWritableDatabase();
        _db.execSQL(deleteSql);
    }
}
