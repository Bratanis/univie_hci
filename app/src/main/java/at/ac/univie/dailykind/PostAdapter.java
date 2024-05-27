package at.ac.univie.dailykind;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> posts;
    Context context;

    public PostAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_item, parent, false);
        return new PostViewHolder(view);
    }

    //Intitialisiert die Werte der RecyclerView mit vorgefertigten Datem
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.description.setText(post.getDescription());
        holder.image.setImageResource(post.getImageResId());
        holder.profilePicture.setImageResource(post.getProfileViewResId());
        holder.numberOfLikes.setText(String.valueOf(post.getNumberOfLikes()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        TextView description;
        CircleImageView profilePicture;
        Button commentButton;
        TextView numberOfLikes;

        ToggleButton likeButton;

        public PostViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.postTitle);
            image = itemView.findViewById(R.id.postImage);
            description = itemView.findViewById(R.id.postDescription);
            profilePicture = itemView.findViewById(R.id.profileView);
            commentButton = itemView.findViewById(R.id.commentButton);
            likeButton = itemView.findViewById(R.id.likeButton);
            numberOfLikes = itemView.findViewById(R.id.amountOfLikes);

            //Weiterleitung auf Commetsection vom jeweiligen Post
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                        CommentButtonClick(position);
                }
            });


            //Steuert anzahl an likes von einem Post
            likeButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    int num = Integer.parseInt(numberOfLikes.getText().toString());
                    ++num;
                    numberOfLikes.setText(String.valueOf(num));
                } else {
                    int num = Integer.parseInt(numberOfLikes.getText().toString());
                    --num;
                    numberOfLikes.setText(String.valueOf(num));
                }
            });
        }
    }

        //Implemetierung von der Weiterleitung auf neuen CommentScreen
    private void CommentButtonClick(int position) {
        //Toast.makeText(context, "pos: " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, CommentSection.class);
        intent.putExtra("user", posts.get(position).getTitle());
        context.startActivity(intent);

    }
}

