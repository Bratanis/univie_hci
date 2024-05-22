package at.ac.univie.dailykind;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentSection extends AppCompatActivity {

    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();
    private EditText commentEditText;
    private Button sendButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comment);

        commentList = new ArrayList<>();
        commentList.add(new Comment("DailyKindUser123", "Cooler Post", R.drawable.profilepic2));
        commentList.add(new Comment("CrazyBoyy", "Coole Tat", R.drawable.profilepic3));
        commentList.add(new Comment("SocialBoy", "Weiter so!!!", R.drawable.profilepic4));
        commentList.add(new Comment("DogLover1", "Crazyyy! Ihr seid die besten!!", R.drawable.profilepic2));
        commentList.add(new Comment("Stacy", "Danke für euren Einsatz", R.drawable.profilepic1));
        commentList.add(new Comment("Luna", "♡ ♡ ♡ ♡", R.drawable.profilepic3));
        commentList.add(new Comment("Lukas", "@Shawn Lass uns das auch machen nächstes Mal", R.drawable.profilepic4));
        commentList.add(new Comment("Tom", "\uD83D\uDC4F \uD83D\uDC4F \uD83D\uDC4F \uD83D\uDC4F", R.drawable.profilepic1));

        // Initialisiere die Views
        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        commentEditText = findViewById(R.id.commentEditText);
        sendButton = findViewById(R.id.sendButton);

        // Setze den Adapter und das Layout
        commentAdapter = new CommentAdapter(commentList);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentRecyclerView.setAdapter(commentAdapter);

        // Setze den OnClickListener für den Senden-Button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = commentEditText.getText().toString().trim();
                if (!commentText.isEmpty()) {
                    // Füge den neuen Kommentar hinzu und benachrichtige den Adapter
                    commentList.add(new Comment("MyUser", commentText, R.drawable.profilepic1));
                    commentAdapter.notifyItemInserted(commentList.size() - 1);
                    commentEditText.setText("");
                    commentRecyclerView.smoothScrollToPosition(commentList.size() - 1);
                }
            }
        });

       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }
}
