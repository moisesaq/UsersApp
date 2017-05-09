package moises.com.usersapp.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


public class Login {
    @SerializedName("username")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
