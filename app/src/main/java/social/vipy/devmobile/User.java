package social.vipy.devmobile;

import androidx.room.Entity;

import java.util.HashMap;


public class User {
    private int id;
    private String username;
    private String email;
    private String display_name;

    public User(int id, String username, String email, String display_name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.display_name = display_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", display_name='" + display_name + '\'' +
                '}';
    }
}
