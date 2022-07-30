package social.vipy.devmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import social.vipy.devmobile.databinding.TimelineBinding;


public class TimelineActivity extends AppCompatActivity {

    TimelineBinding binding;
    TimelineViewModel timelineViewModel;
    PostAdapter postRecyclerViewAdapter;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = TimelineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        timelineViewModel = new ViewModelProvider(TimelineActivity.this).get(TimelineViewModel.class);

        binding.postListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.postListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        postRecyclerViewAdapter = new PostAdapter();

        postRecyclerViewAdapter.setReactionClickListener((view, position) -> {
            timelineViewModel.reactToPost(position);
            postRecyclerViewAdapter.notifyDataSetChanged();
        });

        binding.postListRecyclerView.setAdapter(postRecyclerViewAdapter);

        timelineViewModel.getPosts().observe(TimelineActivity.this, postRecyclerViewAdapter::submitList);

        binding.sendPostButton.setOnClickListener(view -> {
            String content = binding.contentEditText.getText().toString();
            timelineViewModel.addPost(new User("@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), content, false, 0);
            binding.contentEditText.setText("");
            postRecyclerViewAdapter.notifyDataSetChanged();
        });

    }
}
