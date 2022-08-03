package social.vipy.devmobile.repository.retrofit;

import android.content.SharedPreferences;
import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient extends BaseAPIClient {
    public static SharedPreferences preferences;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Log.d("tagger", "oi rs");
            retrofit = new Retrofit.Builder()
                    .client(new OkHttpClient.Builder()
                            .authenticator(new TokenAuthenticator(APIClient.preferences))
                            .build())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
