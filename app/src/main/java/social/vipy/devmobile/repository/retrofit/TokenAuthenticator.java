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
        Retrofit client = BaseAPIClient.getClient();
        Log.d("tagger", "entrei sim");
        Gson gson = new Gson();

        // Faz o parsing do usuário atual, caso exista.
        String userJson = preferences.getString("login_info", null);
        VipyLoginResponse loginInfo = gson.fromJson(userJson, VipyLoginResponse.class);
        final String refreshToken = loginInfo.getTokens().getRefreshToken();
        HashMap<String, String> payload = new HashMap<String, String>() {{
            put("refresh", refreshToken);
        }};
        Call<VipyTokenPair> tokenPair = client.create(VipyAPIClientInterface.class)
                .refresh(payload);
        accessToken = loginInfo.getTokens().getAccessToken();

        tokenPair.enqueue(
                new Callback<VipyTokenPair>() {
                    @Override
                    public void onResponse(Call<VipyTokenPair> call, retrofit2.Response<VipyTokenPair> refreshResponse) {
                        Log.d("tagger", "status " + String.valueOf(refreshResponse.code()));

                        try {

                            switch (refreshResponse.code()) {
                                case 200:
                                    Log.d("tagger", String.valueOf(refreshResponse.body()));

                                    String json = refreshResponse.body().toString();

                                    VipyTokenPair VTokenPain = gson.fromJson(json, VipyTokenPair.class);
                                    Log.d("tagger", String.valueOf(VTokenPain));

                                    accessToken = VTokenPain.getAccessToken();

                                    loginInfo.setTokens(VTokenPain);
                                    String loginInfoJson = (new Gson()).toJson(loginInfo);
                                    preferences.edit().putString("login_info", loginInfoJson).apply();


                                default:
                                    preferences.edit().remove("login_info").apply();

                                    break;
                            }


                        } catch (Exception e) {
                            System.out.println("Erro desconhecido.");
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                            preferences.edit().remove("login_info").apply();

                        }

                    }

                    @Override
                    public void onFailure(Call<VipyTokenPair> call, Throwable t) {
                        System.out.println("Erro ao fazer requisição");
                        preferences.edit().remove("login_info").apply();
                        t.printStackTrace();
                    }
                }
        );


        return response.request()
                .newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();


    }
}
