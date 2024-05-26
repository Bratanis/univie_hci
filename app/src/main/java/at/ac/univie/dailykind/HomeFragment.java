package at.ac.univie.dailykind;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button buttonOpenCalendar;
    private RecyclerView recyclerViewTrending;
    private TrendingAdapter trendingAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        buttonOpenCalendar = view.findViewById(R.id.buttonOpenCalendar);
        recyclerViewTrending = view.findViewById(R.id.recyclerViewTrending);

        buttonOpenCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarFragment();
            }
        });

        // Setup RecyclerView
        List<TrendingItem> trendingList = new ArrayList<>();
        trendingList.add(new TrendingItem("Trending", "#love", "23 posts"));
        trendingList.add(new TrendingItem("Picked for you", "Picking up Trash", "8 posts"));
        trendingList.add(new TrendingItem("Trending in Austria", "Donau Cleanup", "29 posts"));
        trendingList.add(new TrendingItem("DailyKind suggests", "Mewing", "4 posts"));
        trendingList.add(new TrendingItem("Trending in Austria", "Ernst Palicek", "7 posts"));
        trendingList.add(new TrendingItem("Trending", "#mindfulness", "12 posts"));
        trendingList.add(new TrendingItem("Trending", "Sustainable Living", "6 posts"));
        trendingList.add(new TrendingItem("Trending in Austria", "Green Energy", "18 posts"));
        trendingList.add(new TrendingItem("DailyKind suggests", "Volunteering", "3 posts"));
        trendingList.add(new TrendingItem("Trending in Austria", "#AlpenFest", "10 posts"));
        //one empty, quick and dirty fix for layout
        trendingList.add(new TrendingItem("", "", ""));

        trendingAdapter = new TrendingAdapter(getContext(), trendingList);
        recyclerViewTrending.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTrending.setAdapter(trendingAdapter);

        return view;
    }

    private void openCalendarFragment() {
        CalendarFragment calendarFragment = new CalendarFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_mainActivity, calendarFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
