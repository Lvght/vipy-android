package social.vipy.devmobile;

public class User {
    String username;
    String email;
    String display_name;

    public User(String username, String email, String display_name) {
        this.username = username;
        this.email = email;
        this.display_name = display_name;
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
}
