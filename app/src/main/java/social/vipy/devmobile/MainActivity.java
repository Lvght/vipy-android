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
import social.vipy.devmobile.repository.retrofit.BaseAPIClient;
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

        // Faz o parsing do usuário atual, caso exista.
        String userJson = preferences.getString("login_info", null);

        if (userJson != null) {
            VipyLoginResponse loginInfo = gson.fromJson(userJson, VipyLoginResponse.class);
            Log.d("vipyinfo", loginInfo.toString());
        }


        binding.Entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                loginViewModel.username = binding.UsernameInput.getText().toString();
                loginViewModel.password = binding.PasswordInputInternal.getText().toString();

                VipyAPIClientInterface client =
                        BaseAPIClient.getClient().create(VipyAPIClientInterface.class);

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

                                            // Substitui o intent atual por um novo.
                                            Intent intent = getIntent();
                                            finish();

                                            intent = new Intent(MainActivity.this, TimelineActivity.class);
                                            startActivity(intent);

                                            break;
                                        case 403:
                                            binding.PasswordInputInternal.setError("Senha incorreta");
                                            break;
                                        case 404:
                                            // TODO abrir toast.
                                            System.out.println("Usuário não encontrado");
                                            binding.UsernameInput.setError("Usuário não encontrado");
                                            break;
                                        default:
                                            // 5XX
                                            binding.UsernameInput.setError("Erro no servidor.");
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