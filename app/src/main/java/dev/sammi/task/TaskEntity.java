package dev.sammi.task;

public class TaskEntity {
    private int id;
    private String task;
    private int isRemoved;
    private boolean isChecked;
    private boolean completed;
    private String status;

    public TaskEntity(int id, String task) {
        this.id = id;
        this.task = task;
    }

    public TaskEntity(String task) {
        this.task = task;
    }

    public boolean isChecked() {
        if (status.equals("Finished")) {
            return true;
        }
        return false;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(int isRemoved) {
        this.isRemoved = isRemoved;
    }
}
