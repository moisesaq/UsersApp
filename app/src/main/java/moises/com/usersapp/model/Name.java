package moises.com.usersapp.model;

import com.google.gson.Gson;

import java.io.Serializable;

public class Name implements Serializable{
    private String title;
    private String first;
    private String last;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
