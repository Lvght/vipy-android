package social.vipy.devmobile;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

    private List<Post> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;

    // 'data' is passed into the constructor
    // it refers to the list of objects showing in the recycler view
    PostRecyclerViewAdapter(Context context, List<Post> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    // each row () is a viewHolder
    // this method creates and initialize, but no data is added
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_post, parent, false);
        return new ViewHolder(view);
    }

    // The view for the row is created (holder); now you may manipulate the view
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mData.get(position);
        String name = post.getAuthor().getDisplay_name();
        String username =post.getAuthor().getUsername();
        String content = post.getContent();
        int counter = post.getReactionCounter();

        holder.nameTextView.setText(name);
        holder.usernameTextView.setText(username);
        holder.contentTextView.setText(content);
        holder.counterTextView.setText(String.valueOf(counter));

        setReactionButtonLayout(holder, post.getReacted());


        holder.reactionButton.setOnClickListener(view -> {
            post.setReacted(!post.getReacted());
            if(post.getReacted()){
                addReaction(holder);
            } else {
                removeReaction(holder);
            }
        });

    }

    private void addReaction(ViewHolder holder) {
        int currentCounter = Integer.parseInt(holder.counterTextView.getText().toString());
        int newReactionCounter = currentCounter + 1;

        holder.counterTextView.setTextColor(
                ContextCompat.getColor(holder.reactionButton.getContext(),
                R.color.active_reaction_counter));

        holder.counterTextView.setText(String.valueOf(newReactionCounter));
        setReactionButtonLayout(holder, true);
    }

    private void removeReaction(ViewHolder holder) {
        int currentCounter = Integer.parseInt(holder.counterTextView.getText().toString());
        int newReactionCounter = currentCounter - 1;

        holder.counterTextView.setTextColor(
                ContextCompat.getColor(holder.reactionButton.getContext(),
                        R.color.inactive_reaction_counter));

        holder.counterTextView.setText(String.valueOf(newReactionCounter));
        setReactionButtonLayout(holder, false);
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

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // Provide a reference to the type of views that you are using
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
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

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) {
                mLongClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
            return false;
        }
    }

        // convenience method for getting data at click position
    Post getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // allows long clicks events
    void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mLongClickListener = itemLongClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}