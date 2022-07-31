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

        // TODO Carregar do disco.
        String refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTY1OTEwODEwMCwiaWF0IjoxNjU5MDIxNzAwLCJqdGkiOiJhNzZiNjc2YWE1YzM0N2YyYmRmMDkyZTVjNWRjMGM4NSIsInVzZXJfaWQiOjE4fQ.ffzueA2JtqZY0N2__i5lrUBq9ga-E3E9d01-PEjXgnA";

        retrofit2.Response<VipyTokenPair> tokenPair = client.create(VipyAPIClientInterface.class)
                .refresh(refreshToken).execute();

        String json = tokenPair.body().toString();

        String newToken = "";

        return response.request()
                .newBuilder()
                .header("Authorization", "Bearer " + newToken)
                .build();
    }
}
