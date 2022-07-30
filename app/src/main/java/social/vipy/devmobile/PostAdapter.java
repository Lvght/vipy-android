package social.vipy.devmobile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends ListAdapter<Post, PostAdapter.ViewHolder> {
    private ReactionClickListener mReactionClickListener;

    public PostAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.recyclerview_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = getItem(position);
        String name = post.getAuthor().getDisplay_name();
        String username = post.getAuthor().getUsername();
        String content = post.getContent();
        int counter = post.getReactionCounter();

        holder.nameTextView.setText(name);
        holder.usernameTextView.setText(username);
        holder.contentTextView.setText(content);
        holder.counterTextView.setText(String.valueOf(counter));

        setReactionButtonLayout(holder, post.getReacted());

        holder.reactionButton.setOnClickListener(view -> {
            if(mReactionClickListener != null){
                mReactionClickListener.onReactionClick(view, position);
            }
        });
    }

    private void setReactionButtonLayout(ViewHolder holder, Boolean isActive){
        int color = isActive ? R.color.active_reaction_icon : R.color.inactive_reaction_icon;
        int drawable =  isActive ? R.drawable.reaction_button_active_shape : R.drawable.reaction_button_inactive_shape;

        holder.reactionButton.setColorFilter(
                ContextCompat.getColor(holder.reactionButton.getContext(), color),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        holder.reactionButton.setBackground(
                ContextCompat.getDrawable(
                        holder.reactionButton.getContext(),
                        drawable)
        );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView usernameTextView;
        TextView contentTextView;
        TextView counterTextView;
        ImageButton reactionButton;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            reactionButton = itemView.findViewById(R.id.reactionButton);
            counterTextView = itemView.findViewById(R.id.counterTextView);
        }
    }

    public static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Post>() {
                // it decides if two objects are equal
                @Override
                public boolean areItemsTheSame(Post oldItem, Post newItem) {
                    return oldItem.getId() == newItem.getId();
                }
                // it decides if the object needs to redraw
                @Override
                public boolean areContentsTheSame(Post oldItem, Post newItem) {
                    return (oldItem.getContent().equals(newItem.getContent()) &&
                            oldItem.getReactionCounter() == newItem.getReactionCounter() &&
                            oldItem.getReacted().equals(newItem.getReacted()));
                }
            };

    void setReactionClickListener(ReactionClickListener reactionClickListener) {
        this.mReactionClickListener = reactionClickListener;
    }

    public interface ReactionClickListener {
        void onReactionClick(View view, int position);
    }
}