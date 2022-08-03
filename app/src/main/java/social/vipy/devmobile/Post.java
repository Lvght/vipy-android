package social.vipy.devmobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    // todo
    Boolean reacted;
    int reactionCounter;

    public Post(int id, User author, String content, Boolean reacted, int reactionCounter) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.reacted = false;
        this.reactionCounter = 0;

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

    public Boolean getReacted() {return false;}

    public void setReacted(Boolean reacted) { this.reacted = reacted; }

    public int getReactionCounter() {
        return reactionCounter;
    }

    public void setReactionCounter(int reactionCounter) {
        this.reactionCounter = reactionCounter;
    }

}
