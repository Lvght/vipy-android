package social.vipy.devmobile.repository.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class APIClient extends BaseAPIClient {
    public static Retrofit getClient() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .client(new OkHttpClient.Builder()
                            .authenticator(new TokenAuthenticator())
                            .build())
                    .baseUrl(BASE_URL)
                    .build();
        }

        return retrofit;
    }
}
