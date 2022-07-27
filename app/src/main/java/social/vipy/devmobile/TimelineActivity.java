package social.vipy.devmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import social.vipy.devmobile.databinding.TimelineBinding;


public class TimelineActivity extends AppCompatActivity {

    TimelineBinding binding;
    ArrayList<Post> posts = new ArrayList<>();
    PostRecyclerViewAdapter postRecyclerViewAdapter;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = TimelineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        posts.add(new Post(new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste"));
        posts.add(new Post(new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste"));
        posts.add(new Post(new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste"));
        posts.add(new Post(new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste"));
        posts.add(new Post(new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), "teste"));

//        if(savedInstanceState != null) {
//            posts = (ArrayList<Post>) savedInstanceState.getSerializable("posts");
//            if (posts == null) posts = new ArrayList<>();
//        }

        binding.postListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.postListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        postRecyclerViewAdapter = new PostRecyclerViewAdapter(TimelineActivity.this, posts);
        binding.postListRecyclerView.setAdapter(postRecyclerViewAdapter);

    }
}
