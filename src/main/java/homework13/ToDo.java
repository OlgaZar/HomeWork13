package homework13;

import java.util.Objects;

public class ToDo {
    private int userId;
    private int id;
    private String title;
    private boolean completed;


    public ToDo(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public ToDo() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return userId == toDo.userId && id == toDo.id && completed == toDo.completed && Objects.equals(title, toDo.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, id, title, completed);
    }


    @Override
    public String toString() {
        return "ToDo{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}




