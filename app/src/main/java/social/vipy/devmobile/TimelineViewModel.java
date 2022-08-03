package social.vipy.devmobile;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TimelineViewModel extends ViewModel {
    private MutableLiveData<List<Post>> posts;
    int currentId;
    public LiveData<List<Post>> getPosts() {
        if (posts == null) {
            posts = new MutableLiveData<List<Post>>();
            posts.setValue(Arrays.asList(
                    new Post(1, new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 10),
                    new Post(2, new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 122),
                    new Post(3, new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 50),
                    new Post(4, new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 27),
                    new Post(5, new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste", false, 1)
            ));
            currentId = 6;
        }
        return posts;
    }

    public void addPost(User author, String content, Boolean reacted, int reactionCounter) {
        if(content != ""){
            List<Post> newList = new ArrayList<Post>(posts.getValue());
            Collections.reverse(newList);
            newList.add(new Post(currentId, author, content, reacted, reactionCounter));
            Collections.reverse(newList);
            currentId++;
            posts.setValue(newList);
        }
    }

    public Post getPost(int index) {
        return posts.getValue().get(index);
    }

    public void reactToPost(int index) {
        Post post = getPost(index);
        post.setReacted(!post.getReacted());

        if(post.getReacted())
            post.setReactionCounter(post.getReactionCounter()+1);
        else
            post.setReactionCounter(post.getReactionCounter()-1);

        List<Post> newList = new ArrayList<Post>(posts.getValue());
        newList.set(index, post);
        posts.setValue(newList);
    }

    public void removePost(int index) {
        List<Post> newList = new ArrayList<Post>(posts.getValue());
        newList.remove(index);
        posts.setValue(newList);
    }
}

