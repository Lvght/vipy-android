package social.vipy.devmobile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private OptionsClickListener mOptionsClickListener;

    public PostAdapter(ReactionClickListener mReactionClickListener, OptionsClickListener mOptionsClickListener) {
        super(DIFF_CALLBACK);
        this.mReactionClickListener = mReactionClickListener;
        this.mOptionsClickListener = mOptionsClickListener;
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
        int counter = post.reactions.getReactionsCounter();

        holder.nameTextView.setText(name);
        holder.usernameTextView.setText(username);
        holder.contentTextView.setText(content);
        holder.counterTextView.setText(String.valueOf(counter));

        setReactionButtonLayout(holder, post.reactions.isUserReacted());

        holder.optionsButton.setOnClickListener(view -> {
            mOptionsClickListener.onOptionsClick(view, holder.getAdapterPosition());
        });

        holder.reactionButton.setOnClickListener(view -> {
            if (mReactionClickListener != null) {
                mReactionClickListener.onReactionClick(view, holder.getAdapterPosition());
            }
        });
    }


    private void setReactionButtonLayout(ViewHolder holder, Boolean isActive) {
        int color = isActive ? R.color.active_reaction_icon : R.color.inactive_reaction_icon;
        int drawable = isActive ? R.drawable.reaction_button_active_shape : R.drawable.reaction_button_inactive_shape;

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
        ImageButton optionsButton;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            reactionButton = itemView.findViewById(R.id.reactionButton);
            counterTextView = itemView.findViewById(R.id.counterTextView);
            optionsButton = itemView.findViewById(R.id.optionsButton);


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
                            oldItem.reactions.getReactionsCounter() == newItem.reactions.getReactionsCounter() &&
                            oldItem.reactions.isUserReacted() == newItem.reactions.isUserReacted());
                }
            };

    void setOptionsClickListener(OptionsClickListener optionsClickListener) {
        this.mOptionsClickListener = optionsClickListener;
    }

    void setReactionClickListener(ReactionClickListener reactionClickListener) {
        this.mReactionClickListener = reactionClickListener;
    }

    public interface ReactionClickListener {
        void onReactionClick(View view, int position);
    }

    public interface OptionsClickListener {
        void onOptionsClick(View view, int position);
    }
}