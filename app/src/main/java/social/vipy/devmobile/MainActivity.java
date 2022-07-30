package social.vipy.devmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import social.vipy.devmobile.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.Entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.UsernameInput.setError("Username ou senha Incorretos");
                binding.PasswordInputInternal.setError("Username ou senha Incorretos");

            }
        });

        binding.CriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Navigate","Ir para a outra página");
            }
        });
    }
}


