package net.juude.droidrest.retrofit;

/**
 * Created by juude on 15-6-18.
 */
public class Repo {
    private int id;
    private String name;
    private String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id : " + id + ", name : " + name + ", full_name : " + full_name + "\n";
    }
}
