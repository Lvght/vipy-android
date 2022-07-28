package social.vipy.devmobile;

public class Post {
    User author;
    String content;
    Boolean reacted;
    int reactionCounter;

    public Post(User author, String content, Boolean reacted, int reactionCounter) {
        this.author = author;
        this.content = content;
        this.reacted = reacted;
        this.reactionCounter = reactionCounter;
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

    public Boolean getReacted() {return reacted;}

    public void setReacted(Boolean reacted) { this.reacted = reacted; }

    public int getReactionCounter() {
        return reactionCounter;
    }

    public void setReactionCounter(int reactionCounter) {
        this.reactionCounter = reactionCounter;
    }

}
