package dev.sammi.task;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.text.HtmlCompat;

import java.util.ArrayList;
import dev.sammi.R;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TaskEntity> tasksData;
    private LayoutInflater layoutInflater;
    private TaskEntity taskEntity;
    private TaskDataStore taskDataStore;

    public TaskAdapter(
            Context context,
            final ArrayList<TaskEntity> tasksData,
            final TaskDataStore taskDataStore) {
        this.context = context;
        this.tasksData = tasksData;
        this.taskDataStore = taskDataStore;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasksData.size();
    }

    @Override
    public Object getItem(int position) {
        return tasksData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasksData.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;
        if (rowView == null) {
            rowView = layoutInflater.inflate(R.layout.tasks_row, parent, false);
        }

        TextView task = rowView.findViewById(R.id.task);
        TextView status = rowView.findViewById(R.id.status);
        CheckBox checkBox = rowView.findViewById(R.id.checkbox);
        ImageView sampah = rowView.findViewById(R.id.sampah);

        taskEntity = tasksData.get(position);
        task.setText(taskEntity.getTask());
        status.setText(taskEntity.getStatus());
        if (taskEntity.getStatus().equals("Finished")) {
            status.setTextColor(Color.parseColor("#00574B"));
            String formatStrike = "<strike>" + taskEntity.getTask() + "</strike>";
            task.setText(HtmlCompat.fromHtml(formatStrike, HtmlCompat.FROM_HTML_MODE_LEGACY));
        }else {
            status.setTextColor(Color.RED);
        }

        checkBox.setChecked(taskEntity.isChecked());
        checkBox.setTag(position);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos = (int) v.getTag();
                boolean isChecked = false;
                String status = "Unfinished";

                if (!tasksData.get(currentPos).isChecked()) {
                    isChecked = true;
                    status = "Finished";
                }

                if (isChecked) {
                    taskDataStore.updateStatusById(tasksData.get(currentPos).getId(), 1);
                    Toast.makeText(context, "Task Ditandai Selesai", Toast.LENGTH_SHORT).show();

                }else {
                    taskDataStore.updateStatusById(tasksData.get(currentPos).getId(), 0);
                    Toast.makeText(context, "Task Ditandai Belum Selesai", Toast.LENGTH_SHORT).show();
                }

                tasksData.get(currentPos).setChecked(isChecked);
                tasksData.get(currentPos).setStatus(status);
                notifyDataSetChanged();
            }
        });

        sampah.setTag(position);
        sampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos = (int) v.getTag();
                TaskEntity task = tasksData.get(currentPos);
                taskDataStore.deleteById(task.getId());
                tasksData.remove(task);
                Toast.makeText(context, "Task Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        return rowView;
    }
}