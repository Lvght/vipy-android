package social.vipy.devmobile.repository;

import java.util.List;

public class PostReactionInfo {
    private int r1;
    private int r2;
    private int r3;
    private int r4;
    private List<Reaction> user_reaction;

    public PostReactionInfo(int r1, int r2, int r3, int r4, List<Reaction> userReaction) {
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
        this.user_reaction = userReaction;
    }

    public int getReactionsCounter() {
        return r1 + r2 + r3 + r4;
    }

    public boolean isUserReacted() {
        return user_reaction != null && user_reaction.size() > 0;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public int getR3() {
        return r3;
    }

    public void setR3(int r3) {
        this.r3 = r3;
    }

    public int getR4() {
        return r4;
    }

    public void setR4(int r4) {
        this.r4 = r4;
    }

    public List<Reaction> getUser_reaction() {
        return user_reaction;
    }

    public void setUser_reaction(List<Reaction> user_reaction) {
        this.user_reaction = user_reaction;
    }

    @Override
    public String toString() {
        return "PostReactionInfo{" +
                "r1=" + r1 +
                ", r2=" + r2 +
                ", r3=" + r3 +
                ", r4=" + r4 +
                ", userReaction=" + user_reaction +
                '}';
    }
}
