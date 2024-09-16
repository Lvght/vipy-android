package social.vipy.devmobile.repository;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import social.vipy.devmobile.User;

public class VipyLoginResponse {
    @SerializedName("profile")
    @Expose
    private User user;

    @SerializedName("tokens")
    @Expose
    private VipyTokenPair tokens;

    public VipyLoginResponse(User user, VipyTokenPair tokens) {
        this.user = user;
        this.tokens = tokens;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VipyTokenPair getTokens() {
        return tokens;
    }

    public void setTokens(VipyTokenPair tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "VipyLoginResponse{" +
                "user=" + user +
                ", tokens=" + tokens +
                '}';
    }
}
