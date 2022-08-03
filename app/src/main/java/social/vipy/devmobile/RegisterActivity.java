package social.vipy.devmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;
import retrofit2.Retrofit;
import social.vipy.devmobile.ViewModels.LoginViewModel;
import social.vipy.devmobile.ViewModels.RegisterViewModel;
import social.vipy.devmobile.databinding.ActivityMainBinding;
import social.vipy.devmobile.databinding.ActivityRegisterBinding;
import social.vipy.devmobile.repository.retrofit.BaseAPIClient;
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
                registerViewModel.setBirthday(String.format("%d-%2d-%2d", year, month + 1, day));
            }, 2000, 0, 1);

            dialog.show();
        });

        binding.criarConta.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        registerViewModel.setPassword(binding.PasswordInputInternal.getText().toString());

                        registerViewModel.setEmail(binding.EmailInput.getText().toString());

                        registerViewModel.setName(binding.NameInput.getText().toString());

                        binding.PasswordInputInternal.setError("");
                        binding.EmailInput.setError("");
                        binding.NameInput.setError("");

                        HashMap<String, String> erros = new HashMap<String, String>();

                        // todo fazer a requisição
                        try {

                            VipyAPIClientInterface client = BaseAPIClient.getClient().create(VipyAPIClientInterface.class);

                            // TODO Terminar cadastro.

                            erros.put("name", "NOME INVÁLIDO");
                            erros.put("email", "EMAIL INVÁLIDO");
                            erros.put("password", "SENHA INVÁLIDA");

                        } catch (Exception e) {

                        }

                        if (erros.containsKey("name")) {
                            binding.NameInput.setError(erros.get("name"));
                        }
                        if (erros.containsKey("email")) {
                            binding.EmailInput.setError(erros.get("email"));
                        }
                        if (erros.containsKey("password")) {
                            binding.PasswordInputInternal.setError(erros.get("password"));
                        }


                    }
                }

        );


    }
}