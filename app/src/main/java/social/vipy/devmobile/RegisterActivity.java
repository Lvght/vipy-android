package social.vipy.devmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import social.vipy.devmobile.ViewModels.LoginViewModel;
import social.vipy.devmobile.ViewModels.RegisterViewModel;
import social.vipy.devmobile.databinding.ActivityMainBinding;
import social.vipy.devmobile.databinding.ActivityRegisterBinding;

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

        binding.criarConta.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        registerViewModel.password = binding.PasswordInputInternal.getText().toString();
                        registerViewModel.email = binding.EmailInput.getText().toString();
                        registerViewModel.name = binding.NameInput.getText().toString();

                        binding.PasswordInputInternal.setError("");
                        binding.EmailInput.setError("");
                        binding.NameInput.setError("");

                        HashMap<String, String> erros =  new HashMap<String, String>();

                        // todo fazer a requisição
                        try {
                            erros.put("name", "NOME INVÁLIDO");
                            erros.put("email", "EMAIL INVÁLIDO");
                            erros.put("password", "SENHA INVÁLIDA");

                        }catch (Exception e){

                        }

                        if (erros.containsKey("name")){
                            binding.NameInput.setError(erros.get("name"));
                        }
                        if (erros.containsKey("email")){
                            binding.EmailInput.setError(erros.get("email"));
                        }
                        if (erros.containsKey("password")){
                            binding.PasswordInputInternal.setError(erros.get("password"));
                        }



                    }
                }

        );


    }
}