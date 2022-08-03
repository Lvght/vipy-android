package social.vipy.devmobile.repository;

public class Reaction {
    int id;
    int author;
    int type;
    int post;

    public Reaction(int id, int author, int type, int post) {
        this.id = id;
        this.author = author;
        this.type = type;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }


    @Override
    public String toString() {
        return "Reaction{" +
                "id=" + id +
                ", author=" + author +
                ", type=" + type +
                ", post=" + post +
                '}';
    }
}
