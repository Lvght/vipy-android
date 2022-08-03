package social.vipy.devmobile.repository.retrofit;

import com.google.gson.JsonElement;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import social.vipy.devmobile.repository.VipyTokenPair;

public interface VipyAPIClientInterface {
    final String LOGIN = "/profiles/login/";
    final String REFRESH = "/profiles/refresh/";

    @POST(LOGIN)
    public Call<JsonElement> login(@Body HashMap<String, String> content);

    @POST(REFRESH)
    public Call<VipyTokenPair> refresh(@Body String refresh);
}
