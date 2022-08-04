package social.vipy.devmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import social.vipy.devmobile.databinding.ActivityRegisterBinding;
import social.vipy.devmobile.databinding.ValidateRegistrationBinding;
import social.vipy.devmobile.repository.VipyLoginResponse;
import social.vipy.devmobile.repository.VipyTokenPair;
import social.vipy.devmobile.repository.retrofit.APIClient;
import social.vipy.devmobile.repository.retrofit.VipyAPIClientInterface;

public class ValidateRegistrationActivity extends AppCompatActivity {
    private ValidateRegistrationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validate_registration);
        binding = ValidateRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.verifyButton.setOnClickListener(view -> {
            System.out.println("Botão pressionado");

            HashMap<String, String> payload = new HashMap<String, String>() {{
                put("code", binding.verificationInput.getText().toString());
            }};

            VipyAPIClientInterface client =
                    APIClient.getNonAuthenticatedClient().create(VipyAPIClientInterface.class);

            Call<JsonElement> call = client.validateRegistration(payload);

            call.enqueue(
                    new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                            System.out.println("ashddasjkdahsjkasjk laasi9092019");

                            System.out.println(response.body());
                            System.out.println(response.code());

                            JsonObject object =
                                    response.body().getAsJsonObject().get("tokens").getAsJsonObject();

                            VipyTokenPair tokens = new VipyTokenPair(
                                    object.get("access").getAsString(),
                                    object.get("refresh").getAsString()
                            );

                            System.out.println(tokens.toString());


                            SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                            String userJson = preferences.getString("login_info", null);

                            Gson gson = new Gson();
                            VipyLoginResponse loginInfo
                                    = gson.fromJson(userJson, VipyLoginResponse.class);

                            loginInfo.setTokens(tokens);

                            userJson = gson.toJson(loginInfo);
                            preferences.edit().putString("login_info", userJson).apply();

                            Intent i = getIntent();
                            finish();

                            i = new Intent(ValidateRegistrationActivity.this, TimelineActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            t.printStackTrace();
                        }
                    }
            );


        });
    }
}
