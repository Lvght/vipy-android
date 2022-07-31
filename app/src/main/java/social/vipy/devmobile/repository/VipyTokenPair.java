package social.vipy.devmobile.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VipyTokenPair {
    @SerializedName("access")
    @Expose
    private String accessToken = null;

    @SerializedName("refresh")
    @Expose
    private String refreshToken = null;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "VipyTokenPair{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}