package social.vipy.devmobile.repository.retrofit;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import social.vipy.devmobile.repository.VipyLoginResponse;

public class APIClient {
    public static SharedPreferences preferences;

    final static String BASE_URL = "https://vipyv-api.herokuapp.com";

    static Retrofit nonAuthenticatedClient;
    static Retrofit client;

    public static Retrofit getNonAuthenticatedClient() {
        if (nonAuthenticatedClient == null) {
            nonAuthenticatedClient = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return nonAuthenticatedClient;
    }

    public static Retrofit getClient() {
        if (client == null) {
            Log.d("tagger", "oi rs");
            client = new Retrofit.Builder()
                    .client(new OkHttpClient.Builder()
                            .authenticator(new TokenAuthenticator(APIClient.preferences))
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    String token = null;

                                    // Carrega o token do disco.
                                    String storedData =
                                            preferences.getString("login_info", null);

                                    if (storedData != null) {
                                        VipyLoginResponse loginInfo =
                                                (new Gson()).fromJson(storedData, VipyLoginResponse.class);

                                        token = loginInfo.getTokens().getAccessToken();

                                    }

                                    Request req = chain.request();
                                    req = req.newBuilder()
                                            .header("Authorization", "Bearer " + token)
                                            .build();

                                    return chain.proceed(req);
                                }
                            })
                            .build())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return client;
    }
}
