package social.vipy.devmobile.repository.retrofit;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import social.vipy.devmobile.Post;
import social.vipy.devmobile.Profile;
import social.vipy.devmobile.User;
import social.vipy.devmobile.repository.Reaction;
import social.vipy.devmobile.repository.VipyLoginResponse;
import social.vipy.devmobile.repository.VipyTokenPair;

public interface VipyAPIClientInterface {
    final String LOGIN = "/profiles/login/";
    final String REFRESH = "/profiles/refresh/";
    final String REGISTER = "/profiles/register/";
    final String POSTS = "/posts/";
    final String DELETE_POST = "/posts/{id}/";
    final String REACT_TO_POST = "/posts/{id}/reactions/";
    final String REMOVE_REACT = "/posts/{id}/reactions/{rid}/";
    final String PROFILES = "/profiles/{id}/";


    @POST(LOGIN)
    public Call<VipyLoginResponse> login(@Body HashMap<String, String> content);

    @POST(REGISTER)
    public Call<User> register(@Body HashMap<String, String> content);

    @POST(REFRESH)
    public Call<VipyTokenPair> refresh(@Body HashMap<String, String> content);

    @GET(POSTS)
    public Call<List<Post>> getTimeline();

    @GET(PROFILES)
    public Call<Profile> getProfile(@Path("id") String id);

    @DELETE(DELETE_POST)
    public Call<Void> deletePost(@Path("id") String id);

    @POST(POSTS)
    public Call<Post> createPost(@Body HashMap<String, String> content);

    @POST(REACT_TO_POST)
    public Call<Reaction> reactToPost(@Body HashMap<String, Integer> payload, @Path("id") int id);

    @DELETE(REMOVE_REACT)
    public Call<Void> removeReact(@Path("id") int id, @Path("rid") int rid);
}
