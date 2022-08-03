package social.vipy.devmobile.repository.retrofit;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import social.vipy.devmobile.Post;
import social.vipy.devmobile.User;
import social.vipy.devmobile.repository.VipyLoginResponse;
import social.vipy.devmobile.repository.VipyTokenPair;

public interface VipyAPIClientInterface {
    final String LOGIN = "/profiles/login/";
    final String REFRESH = "/profiles/refresh/";
    final String REGISTER = "/profiles/register/";

    @POST(LOGIN)
    public Call<VipyLoginResponse> login(@Body HashMap<String, String> content);

    @POST(REGISTER)
    public Call<User> register(@Body HashMap<String, String> content);

    @POST(REFRESH)
    public Call<VipyTokenPair> refresh(@Body String refresh);

    @GET
    public Call<List<Post>> getTimeline();
}
