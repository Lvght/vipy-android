package social.vipy.devmobile.ViewModels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import social.vipy.devmobile.MainActivity;
import social.vipy.devmobile.Post;
import social.vipy.devmobile.PostAdapter;
import social.vipy.devmobile.TimelineActivity;
import social.vipy.devmobile.User;
import social.vipy.devmobile.repository.PostReactionInfo;
import social.vipy.devmobile.repository.Reaction;
import social.vipy.devmobile.repository.VipyLoginResponse;
import social.vipy.devmobile.repository.retrofit.APIClient;
import social.vipy.devmobile.repository.retrofit.VipyAPIClientInterface;

public class TimelineViewModel extends ViewModel {
    private MutableLiveData<List<Post>> posts;
    int currentId;
    PostAdapter adapter;

    public PostAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PostAdapter adapter) {
        this.adapter = adapter;
    }

    public LiveData<List<Post>> getPosts() {
        if (posts == null) {
            posts = new MutableLiveData<List<Post>>();

            VipyAPIClientInterface client =
                    APIClient.getClient().create(VipyAPIClientInterface.class);

            Call<List<Post>> call = client.getTimeline();

            call.enqueue(
                    new Callback<List<Post>>() {
                        @Override
                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                            try {
                                Log.d("tagger", "Código de status do getPosts " + String.valueOf(response.code()));
                                switch (response.code()) {
                                    case 200:
                                        Log.d("tagger", String.valueOf(response.body()));
                                        Collections.reverse(response.body());
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
        try {

            VipyAPIClientInterface client =
                    APIClient.getClient().create(VipyAPIClientInterface.class);

            HashMap<String, String> payload = new HashMap<String, String>() {{
                put("content", content);
            }};

            Call<Post> call = client.createPost(payload);

            call.enqueue(
                    new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {

                            if (response.code() == 201) {
                                Post post = response.body();


                                ArrayList<Post> newPostList = (ArrayList<Post>) posts.getValue();
                                newPostList.add(0, post);

                                posts.postValue(newPostList);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            System.out.println("Erro ao fazer requisição");
                            t.printStackTrace();
                        }
                    }
            );
        } catch (Exception e){

        }
    }

    public Post getPost(int index) {
        return posts.getValue().get(index);
    }

    public void reactToPost(int index) {

        VipyAPIClientInterface client =
                APIClient.getClient().create(VipyAPIClientInterface.class);

        HashMap<String, Integer> payload = new HashMap<String, Integer>() {{
            put("type", 1);
        }};

        Call<Reaction> call = client.reactToPost(payload, posts.getValue().get(index).getId());

        call.enqueue(
                new Callback<Reaction>() {
                    @Override
                    public void onResponse(Call<Reaction> call, Response<Reaction> response) {

                        if (response.code() == 201) {
                            Reaction newReaction = response.body();

                            Post p = posts.getValue().get(index);
                            p.setNewUserReaction(newReaction);

                            ArrayList<Post> newPostList = (ArrayList<Post>) posts.getValue();
                            newPostList.set(index, p);

                            posts.postValue(newPostList);
                            adapter.notifyItemChanged(index);
                        }
                    }

                    @Override
                    public void onFailure(Call<Reaction> call, Throwable t) {
                        System.out.println("Erro ao fazer requisição");
                        t.printStackTrace();
                    }
                }
        );
    }

    public void removeReaction(int index) {

        VipyAPIClientInterface client =
                APIClient.getClient().create(VipyAPIClientInterface.class);

        Post p = posts.getValue().get(index);
        int postId = p.getId();

        Call<Void> call = client.removeReact(postId, 1);

        call.enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 204) {

                            p.removeUserReaction();

                            ArrayList<Post> newPostList = (ArrayList<Post>) posts.getValue();
                            newPostList.set(index, p);

                            posts.postValue(newPostList);
                            adapter.notifyItemChanged(index);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Erro ao fazer requisição");
                        t.printStackTrace();
                    }
                }
        );
    }


    public void removePost(int index) {
        List<Post> newList = new ArrayList<Post>(posts.getValue());

        VipyAPIClientInterface client =
                APIClient.getClient().create(VipyAPIClientInterface.class);

        Post p = posts.getValue().get(index);
        int postId = p.getId();

        Call<Void> call = client.deletePost(String.valueOf(postId));

        call.enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 204) {
                            newList.remove(index);
                            posts.setValue(newList);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Erro ao fazer requisição");
                        t.printStackTrace();
                    }
                }
        );

    }
}

