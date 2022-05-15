package dev.sammi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dev.sammi.task.TaskDataStore;
import dev.sammi.task.TaskEntity;

public class AddTaskActivity extends AppCompatActivity {
    Button simpan;
    EditText taskInput;
    TaskDataStore taskDataStore = new TaskDataStore(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        simpan = findViewById(R.id.btn_simpan);
        taskInput = findViewById(R.id.task_input);
        taskDataStore = new TaskDataStore(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputTask = taskInput.getText().toString();
                TaskEntity taskEntity = new TaskEntity(inputTask);
                taskDataStore.insert(taskEntity);
                Toast.makeText(getApplicationContext(), "Task Baru Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                Intent toMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toMainActivity);
            }
        });
    }
}
