package social.vipy.devmobile;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        String name = mData.get(position).getAuthor().getDisplay_name();
        String username = mData.get(position).getAuthor().getUsername();
        String content = mData.get(position).getContent();

        holder.nameTextView.setText(name);
        holder.usernameTextView.setText(username);
        holder.contentTextView.setText(content);

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


        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
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