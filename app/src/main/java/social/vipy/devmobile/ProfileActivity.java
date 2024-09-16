package social.vipy.devmobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import social.vipy.devmobile.ViewModels.ProfileViewModel;
import social.vipy.devmobile.databinding.PerfilBinding;

public class ProfileActivity extends AppCompatActivity {

    private PerfilBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        binding = PerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // pegar o id do intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("userId");
        Log.d("haha", id);

        // fazer a requisição
        ProfileViewModel profileViewModel = new ViewModelProvider(ProfileActivity.this).get(ProfileViewModel.class);
        Log.d("haha", "descubea");

        profileViewModel.getProfile().observe(ProfileActivity.this, p -> {
            // substituir os textos dos campos
            binding.usernameView.setText(p.getUsername());
            binding.displayNamePerfil.setText(p.getDisplay_name());
            binding.bioUser.setText(p.getBiography());
         });
        profileViewModel.retrieveProfile(id);

        binding.backButton.setOnClickListener(view -> {
            finish();
        });
    }
}