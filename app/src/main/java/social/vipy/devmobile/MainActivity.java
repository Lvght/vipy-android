package social.vipy.devmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import social.vipy.devmobile.ViewModels.LoginViewModel;
import social.vipy.devmobile.databinding.ActivityMainBinding;
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
        binding.Entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                loginViewModel.username = binding.UsernameInput.getText().toString();
                loginViewModel.password = binding.PasswordInputInternal.getText().toString();

                // TODO Fazer requisição

                boolean erro = false;

                VipyAPIClientInterface client =
                        BaseAPIClient.getClient().create(VipyAPIClientInterface.class);

                Call<JsonElement> call = client.login(
                        new HashMap<String, String>() {{
                            put("username", loginViewModel.username);
                            put("password", loginViewModel.password);
                            put("device_name", "Generic DSW3 Android Device");
                        }}
                );

                call.enqueue(
                        new Callback<JsonElement>() {
                            @Override
                            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                try {

                                    switch (response.code()) {
                                        case 200:
                                            JSONObject jobj = new JSONObject(response.body().toString());
                                            System.out.println(jobj.toString());
                                            break;
                                        case 404:
                                            // TODO abrir toast.
                                            System.out.println("Usuário não encontrado");
                                            binding.UsernameInput.setError("Usuário não encontrado");
                                            break;
                                        default:
                                            // TODO abrir toast.
                                            break;
                                    }


                                } catch (JSONException e) {
                                    System.out.println("Erro ao converter JSON");
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonElement> call, Throwable t) {
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


