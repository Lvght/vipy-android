package social.vipy.devmobile.repository.retrofit;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import social.vipy.devmobile.repository.VipyTokenPair;

public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        Retrofit client = BaseAPIClient.getClient();

//        VipyTokenPair tokenPair = client.create(VipyAPIClientInterface.class)
//                .login("v", "vipyadmin7106", "deviceName");

        String newToken = "";

        return response.request()
                .newBuilder()
                .header("Authorization", "Bearer " + newToken)
                .build();
    }
}
