package social.vipy.devmobile;


public class Profile extends User{

    private String biography;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String description) {
        this.biography = description;
    }

    public Profile(int id, String username, String email, String display_name, String description) {
        super(id, username, email, display_name);
        this.biography = description;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + this.getId() +
                ", username='" + this.getUsername() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", display_name='" + this.getDisplay_name() + '\'' +
                "biography='" + biography + '\'' +
                '}';
    }
}
