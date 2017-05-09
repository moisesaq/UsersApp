package moises.com.usersapp.model;

import com.google.gson.Gson;

import java.io.Serializable;

public class User implements Serializable{
    private Login login;
    private Name name;
    private String email;
    private Picture picture;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
