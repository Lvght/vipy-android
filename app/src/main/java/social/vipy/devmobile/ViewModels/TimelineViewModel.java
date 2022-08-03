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
        Post post = new Post(getPost(index));
        PostReactionInfo reactionInfo = post.getReactions();

        // Usuário possui reação?
        boolean userHasReacted = !reactionInfo.getUser_reaction().isEmpty();

        if (userHasReacted) {
            // Todo Excluir reação do usuário

        } else {
            VipyAPIClientInterface client =
                    APIClient.getClient().create(VipyAPIClientInterface.class);

            HashMap<String, Integer> payload = new HashMap<String, Integer>() {{
                put("type", 1);
            }};

            Call<Reaction> call = client.reactToPost(payload, post.getId());

            call.enqueue(
                    new Callback<Reaction>() {
                        @Override
                        public void onResponse(Call<Reaction> call, Response<Reaction> response) {
                            if (response.code() == 201) {

                                PostReactionInfo newReactionInfo = new PostReactionInfo(post.getReactions());
                                List<Reaction> currentUserReactions = newReactionInfo.getUser_reaction();
                                currentUserReactions.add(response.body());

                                newReactionInfo.setUser_reaction(currentUserReactions);

                                newReactionInfo.addUserReaction(response.body());
                                newReactionInfo.setR1(2000);

                                Log.d("Are counters the same? ", newReactionInfo.getR1() + " " + post.getReactions().getR1());

                                post.setReactions(
                                        new PostReactionInfo(
                                                newReactionInfo.getR1(),
                                                newReactionInfo.getR2(),
                                                newReactionInfo.getR3(),
                                                newReactionInfo.getR4(),
                                                Collections.singletonList(response.body())
                                        )
                                );

                                List<Post> newList =
                                        new ArrayList<Post>(posts.getValue());

//                                newList = posts.getValue();
                                newList.set(index, post);

                                newList.set(index, post);

                                Log.d("tagger", "Reação no índice " + String.valueOf(index));
                                Log.d("tagger", newList.get(index).getReactions().getUser_reaction().isEmpty() + "");

//                                newList.set(index, post);

                                posts.setValue(newList);

                                Log.d("tagger", "Post: " + posts.getValue().get(0));
                                Log.d("tagger", "User reacted? " + posts.getValue().get(0).getReactions().isUserReacted());
                            }

                        }

                        @Override
                        public void onFailure(Call<Reaction> call, Throwable t) {
                            Log.d("tagger", "Erro ao criar reação.");
                            t.printStackTrace();
                        }
                    }
            );
        }


    }

    public void removePost(int index) {
        List<Post> newList = new ArrayList<Post>(posts.getValue());
        newList.remove(index);
        posts.setValue(newList);
    }
}

