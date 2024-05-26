package at.ac.univie.dailykind;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    private static final String ARG_HASHTAG = "hashtag";
    private String hashtag;
    private RecyclerView recyclerView;
    private PostAdapter adapter;

    public CommunityFragment() {
        // Required empty public constructor
    }

    public static CommunityFragment newInstance(String hashtag) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HASHTAG, hashtag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hashtag = getArguments().getString(ARG_HASHTAG);
        }
    }

    //Wird ausgeführt nach dem klicken auf den Community button
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        recyclerView = view.findViewById(R.id.postRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lade die Posts basierend auf dem Hashtag, wenn vorhanden, ansonsten alle Sample-Posts
        List<Post> posts = (hashtag != null) ? getPostsForHashtag(hashtag) : getAllSamplePosts();
        adapter = new PostAdapter(posts, getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Post> getPostsForHashtag(String hashtag) {
        // Hier kannst du verschiedene Arrays basierend auf dem Hashtag zurückgeben.
        switch (hashtag) {
            case "#love":
                return Arrays.asList(
                        new Post("studentScs", R.drawable.image1, "Love is all we need. #love", R.drawable.profilepic1),
                        new Post("beast123", R.drawable.image2, "Spread the love. #love", R.drawable.profilepic2)
                );
            case "Picking up Trash":
                return Arrays.asList(
                        new Post("futureDoc", R.drawable.image3, "Let's clean up! #pickingUpTrash", R.drawable.profilepic3),
                        new Post("beast123", R.drawable.image1, "Every bit helps. #pickingUpTrash", R.drawable.profilepic4)
                );
            case "Donau Cleanup":
                return Arrays.asList(
                        new Post("beast123", R.drawable.image1, "Join us for the Donau Cleanup event! #DonauCleanup", R.drawable.profilepic5),
                        new Post("starboy123", R.drawable.image2, "Let's keep the Donau clean. #DonauCleanup", R.drawable.profilepic1)
                );
            case "Mewing":
                return Arrays.asList(
                        new Post("studentScs", R.drawable.image3, "Learn about Mewing and its benefits. #Mewing", R.drawable.profilepic2),
                        new Post("starboy123", R.drawable.image1, "Start Mewing today! #Mewing", R.drawable.profilepic3)
                );
            case "Ernst Palicek":
                return Arrays.asList(
                        new Post("luckyLuke2008", R.drawable.image2, "Remembering Ernst Palicek. #ErnstPalicek", R.drawable.profilepic4),
                        new Post("studentScs", R.drawable.image3, "Legacy of Ernst Palicek. #ErnstPalicek", R.drawable.profilepic5)
                );
            case "#mindfulness":
                return Arrays.asList(
                        new Post("starboy123", R.drawable.image1, "Practicing mindfulness daily. #mindfulness", R.drawable.profilepic1),
                        new Post("futureDoc", R.drawable.image2, "Stay mindful and present. #mindfulness", R.drawable.profilepic2)
                );
            case "Sustainable Living":
                return Arrays.asList(
                        new Post("beast123", R.drawable.image3, "Tips for sustainable living. #SustainableLiving", R.drawable.profilepic3),
                        new Post("luckyLuke2008", R.drawable.image1, "Live sustainably for a better future. #SustainableLiving", R.drawable.profilepic4)
                );
            case "Green Energy":
                return Arrays.asList(
                        new Post("futureDoc", R.drawable.image2, "Green energy solutions for a cleaner planet. #GreenEnergy", R.drawable.profilepic5),
                        new Post("beast123", R.drawable.image3, "Embrace green energy! #GreenEnergy", R.drawable.profilepic1)
                );
            case "Volunteering":
                return Arrays.asList(
                        new Post("starboy123", R.drawable.image1, "Volunteer and make a difference. #Volunteering", R.drawable.profilepic2),
                        new Post("luckyLuke2008", R.drawable.image2, "Join our volunteering team. #Volunteering", R.drawable.profilepic3)
                );
            case "#AlpenFest":
                return Arrays.asList(
                        new Post("luckyLuke2008", R.drawable.image3, "Join us at AlpenFest! #AlpenFest", R.drawable.profilepic4),
                        new Post("starboy123", R.drawable.image1, "Celebrating AlpenFest. #AlpenFest", R.drawable.profilepic5)
                );
            default:
                return new ArrayList<>();
        }
    }

    private List<Post> getAllSamplePosts() {
        // Sample-Posts, die angezeigt werden, wenn kein spezifisches Hashtag vorhanden ist
        return Arrays.asList(
                new Post("starboy123", R.drawable.image1, "A good deed can make a big difference.\n#dailykind #betterworld #lifestyle", R.drawable.profilepic1),
                new Post("luckyLuke2008", R.drawable.image2, "Helping others fulfills me \n#elderlyCare\n",R.drawable.profilepic1),
                new Post("beast123", R.drawable.image3, "Sharing is the new having.\n#pickingUpTrash #newGen #kindnessOfTheDay",R.drawable.profilepic1),
                new Post("studentScs", R.drawable.pick_up_trash, "Keep the planet clean guys!! \n #environment #friends", R.drawable.profilepic4),
                new Post("FoodLover23", R.drawable.food_distribution, "Just do it! \n #food #helping", R.drawable.profilepic1),
                new Post("futureDoc", R.drawable.paramedic, "Always at your service!  \n #paramedic #ambulance", R.drawable.profilepic2)
        );
    }
}

