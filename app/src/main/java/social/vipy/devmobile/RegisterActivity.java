package social.vipy.devmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;
import social.vipy.devmobile.ViewModels.LoginViewModel;
import social.vipy.devmobile.ViewModels.RegisterViewModel;
import social.vipy.devmobile.databinding.ActivityMainBinding;
import social.vipy.devmobile.databinding.ActivityRegisterBinding;
import social.vipy.devmobile.repository.VipyLoginResponse;
import social.vipy.devmobile.repository.retrofit.APIClient;
import social.vipy.devmobile.repository.retrofit.VipyAPIClientInterface;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.birthdayInput.setOnClickListener(view -> {
            final DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {

                // TODO Não aceitamos menores de idade.

                binding.birthdayInput.setText(String.format("%02d/%02d/%d", day, month + 1, year));
                registerViewModel.setBirthday(String.format("%d-%02d-%02d", year, month + 1, day));
            }, 2000, 0, 1);

            dialog.show();
        });

        binding.criarConta.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        registerViewModel.setPassword(binding.passwordInputInternal.getText().toString());
                        registerViewModel.setEmail(binding.emailInput.getText().toString());
                        registerViewModel.setName(binding.displayNameInput.getText().toString());
                        registerViewModel.setUsername(binding.usernameInput.getText().toString());

                        // TODO Validações iniciais
//                        binding.passwordInputInternal.setError("");
//                        binding.emailInput.setError("");
//                        binding.displayNameInput.setError("");

//                        HashMap<String, String> erros = new HashMap<String, String>();

                        // todo fazer a requisição
                        try {

                            VipyAPIClientInterface client =
                                    APIClient.getNonAuthenticatedClient()
                                            .create(VipyAPIClientInterface.class);

                            HashMap<String, String> payload = new HashMap<String, String>() {{
                                put("email", registerViewModel.getEmail());
                                put("password", registerViewModel.getPassword());
                                put("display_name", registerViewModel.getName());
                                put("birthday", registerViewModel.getBirthday());
                                put("device_name", "Generic DevMobile Android Device");
                            }};

                            // creates a multipart request

//                            MultipartBody.Builder builder = new MultipartBody.Builder();
//                            builder.setType(MultipartBody.FORM);
//                            for (Map.Entry<String, String> entry : payload.entrySet()) {
//                                builder.addFormDataPart(entry.getKey(), entry.getValue());
//                            }

                            System.out.println("HSKjashdkjds");

                            MultipartBody.Builder builder =
                                    new MultipartBody.Builder().setType(MultipartBody.FORM);

                            System.out.println(registerViewModel.getBirthday());

                            builder.addFormDataPart("email", registerViewModel.getEmail())
                                    .addFormDataPart("password", registerViewModel.getPassword())
                                    .addFormDataPart("username", registerViewModel.getUsername())
                                    .addFormDataPart("display_name", registerViewModel.getName())
                                    .addFormDataPart("birthday", registerViewModel.getBirthday())
                                    .addFormDataPart("device_name", "Generic DevMobile Android Device");

                            System.out.println("Vai buildarrrrrr");
                            RequestBody finalizedBody = builder.build();

                            System.out.println("ahdkdhdkshdahdasjkdhkj");
                            Call<User> call = client.register(finalizedBody);


                            call.enqueue(
                                    new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            Log.d("tagger", "Código de resposta do registro: " + response.code());
                                            Log.d("tagger", "Resposta do registro: " + response.body());

                                            if (response.code() == 201) {
                                                Log.d("tagger", "Novo usuário: " + response.body().toString());

                                                User user = response.body();
                                                VipyLoginResponse loginInfo =
                                                        new VipyLoginResponse(user, null);

                                                Gson gson = new Gson();
                                                String json = gson.toJson(loginInfo);

                                                SharedPreferences preferences =
                                                        getSharedPreferences("user_data", MODE_PRIVATE);

                                                preferences.edit().putString("login_info", json).apply();

                                                // Envia o usuário para a tela de verificação

                                                Intent intent = getIntent();
                                                finish();

                                                intent = new Intent(
                                                        RegisterActivity.this,
                                                        ValidateRegistrationActivity.class
                                                );
                                                startActivity(intent);

                                            } else {
                                                try {
                                                    Log.d("tagger", "Resposta do erro: " + response.errorBody().string());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            // TODO Mudar de tela.
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            Log.d("vipyinfo", t.getMessage());
                                        }
                                    }
                            );

                        } catch (Exception e) {

                        }

//                        if (erros.containsKey("name")) {
//                            binding.displayNameInput.setError(erros.get("name"));
//                        }
//                        if (erros.containsKey("email")) {
//                            binding.emailInput.setError(erros.get("email"));
//                        }
//                        if (erros.containsKey("password")) {
//                            binding.passwordInputInternal.setError(erros.get("password"));
//                        }


                    }
                }

        );


    }
}