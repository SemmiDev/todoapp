package dev.sammi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import dev.sammi.task.TaskAdapter;
import dev.sammi.task.TaskDataStore;

public class MainActivity extends AppCompatActivity {
    ListView mlistView;
    TaskAdapter tasksAdapter;
    TaskDataStore taskDataStore;

    ImageView addTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlistView = findViewById(R.id.listview_list);
        taskDataStore = new TaskDataStore(this);

        addTaskBtn = findViewById(R.id.add_btn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent taskActivity = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(taskActivity);
            }
        });

        tasksAdapter = new TaskAdapter(this, taskDataStore.findAll(), taskDataStore);
        mlistView.setAdapter(tasksAdapter);
    }
}
