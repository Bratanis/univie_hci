package at.ac.univie.dailykind;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;




public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PostAdapter(posts));
    }

    //hardcoded data, code of doom, entering the dangerzone:

    List<Post> posts = Arrays.asList(
            new Post("Post 1", R.drawable.image1, "Description of post 1."),
            new Post("Post 2", R.drawable.image2, "Description of post 2."),
            new Post("Post 3", R.drawable.image3, "Description of post 3.")
    );


    //hardcoded data end
}

