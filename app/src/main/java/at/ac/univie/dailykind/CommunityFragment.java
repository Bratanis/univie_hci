package at.ac.univie.dailykind;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<String> dataList;

    public CommunityFragment() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFrag.
     */
    // TODO: Rename and change types and number of parameters
   /* public static CommunityFrag newInstance(String param1, String param2) {
        CommunityFrag fragment = new CommunityFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        recyclerView = view.findViewById(R.id.postRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new PostAdapter(posts));


       // return inflater.inflate(R.layout.fragment_community, container, false);
        return view;
    }

    List<Post> posts = Arrays.asList(
            new Post("Post 1", R.drawable.image1, "Description of post 1."),
            new Post("Post 2", R.drawable.image2, "Description of post 2."),
            new Post("Post 3", R.drawable.image3, "Description of post 3.")
    );
}