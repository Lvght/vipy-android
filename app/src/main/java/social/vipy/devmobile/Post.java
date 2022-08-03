package social.vipy.devmobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import social.vipy.devmobile.repository.PostReactionInfo;

public class Post {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("author")
    @Expose
    User author;

    @SerializedName("content")
    @Expose
    String content;

    @SerializedName("reactions")
    @Expose
    PostReactionInfo reactions;

    public Post(int id, User author, String content, PostReactionInfo reactions) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.reactions = reactions;
    }

    public Post(Post post) {
        this.id = post.id;
        this.author = post.author;
        this.content = post.content;
        this.reactions = post.reactions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostReactionInfo getReactions() {
        return reactions;
    }

    public void setReactions(PostReactionInfo reactions) {
        this.reactions = reactions;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", author=" + author +
                ", content='" + content + '\'' +
                ", reactions=" + reactions +
                '}';
    }
}
