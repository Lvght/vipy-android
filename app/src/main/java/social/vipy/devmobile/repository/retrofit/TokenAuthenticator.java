package social.vipy.devmobile.repository.retrofit;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import social.vipy.devmobile.MainActivity;
import social.vipy.devmobile.TimelineActivity;
import social.vipy.devmobile.repository.VipyLoginResponse;
import social.vipy.devmobile.repository.VipyTokenPair;

public class TokenAuthenticator implements Authenticator {

    private SharedPreferences preferences;

    public TokenAuthenticator(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    String accessToken;

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        VipyAPIClientInterface client =
                APIClient.getNonAuthenticatedClient()
                        .create(VipyAPIClientInterface.class);

        Log.d("tagger", "Interceptor de requisição não autorizada invocado.");
        Gson gson = new Gson();

        // Faz o parsing do usuário atual, caso exista.
        String userJson = preferences.getString("login_info", null);
        VipyLoginResponse loginInfo = gson.fromJson(userJson, VipyLoginResponse.class);

        String oldToken, refreshToken;

        if (loginInfo != null) {
            refreshToken = oldToken = loginInfo.getTokens().getRefreshToken();
            accessToken = loginInfo.getTokens().getAccessToken();
        } else {
            oldToken = refreshToken = accessToken = null;
        }

        if (refreshToken == null) {
            Log.d("tagger", "Não há token de refresh, não é possível fazer o refresh.");
            return null;
        }

        HashMap<String, String> payload = new HashMap<String, String>() {{
            put("refresh", refreshToken);
        }};

//        Call<VipyTokenPair> tokenPair = client.create(VipyAPIClientInterface.class)
//                .refresh(payload);

        retrofit2.Response<VipyTokenPair> refreshResponse = client.refresh(payload).execute();

        switch (refreshResponse.code()) {
            case 200:
                Log.d("tagger", String.valueOf(refreshResponse.body()));

                VipyTokenPair vipyTokenPair = refreshResponse.body();
                Log.d("tagger", String.valueOf(vipyTokenPair));

                accessToken = vipyTokenPair.getAccessToken();

                loginInfo.setTokens(vipyTokenPair);
                String loginInfoJson = (new Gson()).toJson(loginInfo);

                preferences.edit()
                        .putString("login_info", loginInfoJson)
                        .apply();

                break;


            default:
                preferences.edit().remove("login_info").apply();
                Log.d("tagger", "Não foi possível obter um novo token.");
                break;
        }

        Log.d("tagger", "Sending request with new token: " + accessToken);
//        Log.d("tagger", oldToken.equals(accessToken) ? "O token NÃO foi atualizado." : "O token foi atualizado.");

        String authHeader = "Bearer " + accessToken;

        return response.request()
                .newBuilder()
                .header("Authorization", authHeader)
                .build();
    }
}
