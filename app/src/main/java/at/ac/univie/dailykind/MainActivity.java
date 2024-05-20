package at.ac.univie.dailykind;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import at.ac.univie.dailykind.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Zuerst das TasksFragment anzeigen
        changeScreen(new HomeFragment());

        // Listener für das bottomNavigationView setzen
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.community) {
                changeScreen(new CommunityFragment());
            } else if (itemId == R.id.home) {
                changeScreen(new HomeFragment());
            } else if (itemId == R.id.rewards) {
                changeScreen(new RewardsFragment());
            } else if (itemId == R.id.profile) {
                changeScreen(new ProfileFragment());
            } else if (itemId == R.id.camera) {
                changeScreen(new Fragment());
            }
            return true; // Return true to display the selected fragment
        });
    }

    public void changeScreen(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_mainActivity,fragment);
        transaction.commit();

    }

    /*hardcoded data, code of doom, entering the dangerzone:

    List<Post> posts = Arrays.asList(
            new Post("Post 1", R.drawable.image1, "Description of post 1."),
            new Post("Post 2", R.drawable.image2, "Description of post 2."),
            new Post("Post 3", R.drawable.image3, "Description of post 3.")
    );
    hardcoded data end*/

}

