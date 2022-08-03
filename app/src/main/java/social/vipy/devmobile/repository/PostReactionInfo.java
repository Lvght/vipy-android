package social.vipy.devmobile.repository;

import java.util.List;

public class PostReactionInfo {
    private int r1;
    private int r2;
    private int r3;
    private int r4;
    private List<Integer> userReaction;

    public PostReactionInfo(int r1, int r2, int r3, int r4, List<Integer> userReaction) {
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
        this.userReaction = userReaction;
    }

    public int getReactionsCounter() {
        return r1 + r2 + r3 + r4;
    }

    public boolean isUserReacted() {
        return userReaction != null && userReaction.size() > 0;
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

    public List<Integer> getUserReaction() {
        return userReaction;
    }

    public void setUserReaction(List<Integer> userReaction) {
        this.userReaction = userReaction;
    }

    @Override
    public String toString() {
        return "PostReactionInfo{" +
                "r1=" + r1 +
                ", r2=" + r2 +
                ", r3=" + r3 +
                ", r4=" + r4 +
                ", userReaction=" + userReaction +
                '}';
    }
}
