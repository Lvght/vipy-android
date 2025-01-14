package social.vipy.devmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;

import social.vipy.devmobile.ViewModels.TimelineViewModel;
import social.vipy.devmobile.databinding.TimelineBinding;
import social.vipy.devmobile.repository.VipyLoginResponse;
import social.vipy.devmobile.repository.retrofit.APIClient;


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
        postRecyclerViewAdapter = new PostAdapter(this::onReactionClick, this::onOptionsClick, this::onAvatarClick);

        binding.postListRecyclerView.setAdapter(postRecyclerViewAdapter);

        timelineViewModel.setAdapter(postRecyclerViewAdapter);

        timelineViewModel.getPosts().observe(TimelineActivity.this, postRecyclerViewAdapter::submitList);

        binding.sendPostButton.setOnClickListener(this::onSendClick);
    }

    private void onReactionClick(View view, int position) {
        if (timelineViewModel.getPost(position).getReactions().isUserReacted()){
            timelineViewModel.removeReaction(position);
        }else {
            timelineViewModel.reactToPost(position);

        }
        postRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void onOptionsClick(View view, int position) {


        Gson gson = new Gson();
        String userJson = APIClient.preferences.getString("login_info", null);
        VipyLoginResponse loginInfo = gson.fromJson(userJson, VipyLoginResponse.class);
        if (timelineViewModel.getPost(position).getAuthor().getId() !=
                loginInfo.getUser().getId()
        ) return;

        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.popup_post_actions, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.delete_option:


                    timelineViewModel.removePost(position);
                    postRecyclerViewAdapter.notifyDataSetChanged();

                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

    private void onAvatarClick(View view, int position) {
        Post post = timelineViewModel.getPost(position);
        User user = post.getAuthor();
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("userId", String.valueOf(user.getId()));
        startActivity(intent);
    }

    private void onSendClick(View view) {
        String content = binding.contentEditText.getText().toString();
        timelineViewModel.addPost(new User(0, "@lordlucas4", "lucas_mcid@hotmail.com", "Lucas"), content, false, 0);
        binding.contentEditText.setText("");
        postRecyclerViewAdapter.notifyDataSetChanged();
    }



}
