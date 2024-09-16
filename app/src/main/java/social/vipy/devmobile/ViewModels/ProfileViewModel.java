package social.vipy.devmobile.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import social.vipy.devmobile.Post;
import social.vipy.devmobile.PostAdapter;
import social.vipy.devmobile.Profile;
import social.vipy.devmobile.User;
import social.vipy.devmobile.repository.Reaction;
import social.vipy.devmobile.repository.retrofit.APIClient;
import social.vipy.devmobile.repository.retrofit.VipyAPIClientInterface;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<Profile> p;

    public void retrieveProfile(String userId) {

        VipyAPIClientInterface client =
                APIClient.getClient().create(VipyAPIClientInterface.class);

        if (p == null){
            p = new MutableLiveData<Profile>();
        }
        p.setValue(new Profile(1,"username","email",
                "name","description"));
        Call<Profile> call = client.getProfile(userId);
        Log.d("haha", "mano???" + userId);

        call.enqueue(
                new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        Log.d("haha", String.valueOf(response.code()));
                        if (response.code() == 200) {
                            Log.d("deubom", String.valueOf(response.body()));
                            p.setValue(response.body());

                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        System.out.println("Erro ao fazer requisição");
                        t.printStackTrace();
                    }
                }
        );

    }

    public LiveData<Profile> getProfile(){
        if (p == null){
            p = new MutableLiveData<Profile>();
            p.setValue(new Profile(1,"username","email",
                    "name","description"));
        }



        return p;
    }
}

