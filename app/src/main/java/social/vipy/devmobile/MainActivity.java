package social.vipy.devmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import social.vipy.devmobile.ViewModels.LoginViewModel;
import social.vipy.devmobile.databinding.ActivityMainBinding;

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
                binding.UsernameInput.setError("");
                binding.PasswordInputInternal.setError("");

                loginViewModel.username = binding.UsernameInput.getText().toString();
                loginViewModel.password = binding.PasswordInputInternal.getText().toString();

                // TODO Fazer requisição

                boolean erro = true;
                if (erro) {
                    binding.UsernameInput.setError("Username ou senha Incorretos");
                    binding.PasswordInputInternal.setError("Username ou senha Incorretos");
                } else {
                    // TODO salvar tokens e navegar para a timeline
                }
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


