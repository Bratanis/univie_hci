package at.ac.univie.dailykind;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.description.setText(post.getDescription());
        holder.image.setImageResource(post.getImageResId());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        TextView description;

        public PostViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.postTitle);
            image = itemView.findViewById(R.id.postImage);
            description = itemView.findViewById(R.id.postDescription);
        }
    }
}

