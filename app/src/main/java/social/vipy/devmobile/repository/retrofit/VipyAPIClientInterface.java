package social.vipy.devmobile.repository.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import social.vipy.devmobile.repository.VipyTokenPair;

public interface VipyAPIClientInterface {
    final String LOGIN = "/profiles/login/";

    @POST(LOGIN)
    public Call<VipyTokenPair> login(@Body String username, @Body String password, @Body String deviceName);
}
