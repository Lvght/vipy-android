package social.vipy.devmobile.ViewModels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import social.vipy.devmobile.MainActivity;
import social.vipy.devmobile.Post;
import social.vipy.devmobile.TimelineActivity;
import social.vipy.devmobile.User;
import social.vipy.devmobile.repository.VipyLoginResponse;
import social.vipy.devmobile.repository.retrofit.APIClient;
import social.vipy.devmobile.repository.retrofit.VipyAPIClientInterface;

public class TimelineViewModel extends ViewModel {
    private MutableLiveData<List<Post>> posts;
    int currentId;
    public LiveData<List<Post>> getPosts() {
        if (posts == null) {
            posts = new MutableLiveData<List<Post>>();

            VipyAPIClientInterface client =
                    APIClient.getClient().create(VipyAPIClientInterface.class);
            Log.d("tagger", "casasd?");

            Call<List<Post>> call = client.getTimeline();

            Log.d("tagger", "casas?");
            call.enqueue(
                    new Callback<List<Post>>() {
                        @Override
                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                            try {
                            Log.d("tagger", "Código de status do getPosts " + String.valueOf(response.code()));
                                switch (response.code()) {
                                    case 200:
                                        Log.d("tagger", String.valueOf(response.body()));
                                        posts.setValue(response.body());
                                        break;
                                    default:
                                        // 5XX
                                        break;
                                }


                            } catch (Exception e) {
                                System.out.println("Erro desconhecido.");
                                System.out.println(e.getMessage());
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Post>> call, Throwable t) {
                            System.out.println("Erro ao fazer requisição");
                            t.printStackTrace();
                        }
                    }
            );

//            posts.setValue(Arrays.asList(
//                    new Post(1, new User(0,"@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 10),
//                    new Post(2, new User(0,"@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 122),
//                    new Post(3, new User(0,"@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 50),
//                    new Post(4, new User(0,"@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 27),
//                    new Post(5, new User(0,"@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 1)
//            ));
//            currentId = 6;
        }
        return posts;
    }

    public void addPost(User author, String content, Boolean reacted, int reactionCounter) {
//        if(content != ""){
//            List<Post> newList = new ArrayList<Post>(posts.getValue());
//            Collections.reverse(newList);
//            newList.add(new Post(currentId, author, content, reacted, reactionCounter));
//            Collections.reverse(newList);
//            currentId++;
//            posts.setValue(newList);
//        }
    }

    public Post getPost(int index) {
        return posts.getValue().get(index);
    }

    public void reactToPost(int index) {
//        Post post = getPost(index);
//        post.setReacted(!post.getReacted());
//
//        if(post.getReacted())
//            post.setReactionCounter(post.getReactionCounter()+1);
//        else
//            post.setReactionCounter(post.getReactionCounter()-1);
//
//        List<Post> newList = new ArrayList<Post>(posts.getValue());
//        newList.set(index, post);
//        posts.setValue(newList);
    }

    public void removePost(int index) {
        List<Post> newList = new ArrayList<Post>(posts.getValue());
        newList.remove(index);
        posts.setValue(newList);
    }
}

