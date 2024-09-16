package social.vipy.devmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import social.vipy.devmobile.ViewModels.LoginViewModel;
import social.vipy.devmobile.databinding.ActivityMainBinding;
import social.vipy.devmobile.repository.VipyLoginResponse;
import social.vipy.devmobile.repository.retrofit.APIClient;
import social.vipy.devmobile.repository.retrofit.VipyAPIClientInterface;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        Gson gson = new Gson();
        APIClient.preferences = preferences;

        // Faz o parsing do usuário atual, caso exista.
        String userJson = preferences.getString("login_info", null);

        if (userJson != null) {
            VipyLoginResponse loginInfo = gson.fromJson(userJson, VipyLoginResponse.class);

            if (loginInfo.getTokens() != null) {
                Intent intent = getIntent();
                finish();

                intent = new Intent(MainActivity.this, TimelineActivity.class);
                startActivity(intent);
            }


        }


        binding.Entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                loginViewModel.username = binding.usernameInput.getText().toString();
                loginViewModel.password = binding.passwordInputInternal.getText().toString();

                VipyAPIClientInterface client =
                        APIClient.getNonAuthenticatedClient().create(VipyAPIClientInterface.class);

                Call<VipyLoginResponse> call = client.login(
                        new HashMap<String, String>() {{
                            put("username", loginViewModel.username);
                            put("password", loginViewModel.password);
                            put("device_name", "Generic DSW3 Android Device");
                        }}
                );

                call.enqueue(
                        new Callback<VipyLoginResponse>() {
                            @Override
                            public void onResponse(Call<VipyLoginResponse> call, Response<VipyLoginResponse> response) {
                                try {

                                    switch (response.code()) {
                                        case 200:
                                            System.out.println(response.body().getUser().getDisplay_name());
                                            System.out.println(response.body().getTokens().getAccessToken());

                                            SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                                            String loginInfoJson = (new Gson()).toJson(response.body());

                                            preferences.edit().putString("login_info", loginInfoJson).apply();
                                            APIClient.preferences = preferences;
                                            // Substitui o intent atual por um novo.
                                            Intent intent = getIntent();
                                            finish();

                                            intent = new Intent(MainActivity.this, TimelineActivity.class);
                                            startActivity(intent);

                                            break;
                                        case 403:
                                            binding.passwordInputInternal.setError(getString(R.string.incorrectPassword));
                                            break;
                                        case 404:
                                            // TODO abrir toast.
                                            System.out.println(getString(R.string.userNotFound));
                                            binding.usernameInput.setError(getString(R.string.userNotFound));
                                            break;
                                        default:
                                            // 5XX
                                            binding.usernameInput.setError(getString(R.string.serverError));
                                            break;
                                    }


                                } catch (Exception e) {
                                    System.out.println("Erro desconhecido.");
                                    System.out.println(e.getMessage());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<VipyLoginResponse> call, Throwable t) {
                                System.out.println("Erro ao fazer requisição");
                                t.printStackTrace();
                            }
                        }
                );
            }

        });

        binding.CriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nav = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(nav);

            }
        });
    }
}