package at.ac.univie.dailykind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewardsFragment extends Fragment {

    private TextView pointsTextView;
    private int points = 2020; // Example initial points

    public RewardsFragment() {
        // Required empty public constructor
    }

    public static RewardsFragment newInstance() {
        return new RewardsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        pointsTextView = view.findViewById(R.id.points);
        updatePointsDisplay();

        LinearLayout reward1 = view.findViewById(R.id.reward1);
        LinearLayout reward2 = view.findViewById(R.id.reward2);
        LinearLayout reward3 = view.findViewById(R.id.reward3);
        LinearLayout reward4 = view.findViewById(R.id.reward4);
        LinearLayout reward5 = view.findViewById(R.id.reward5);

        // Add more reward views as needed

        reward1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redeemReward(100); // Example cost of 10 points
            }
        });

        reward2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redeemReward(250); // Example cost of 20 points
            }
        });

        reward3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redeemReward(500); // Example cost of 20 points
            }
        });

        reward4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redeemReward(1000); // Example cost of 20 points
            }
        });

        reward5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redeemReward(2000); // Example cost of 20 points
            }
        });

        return view;
    }

    private void updatePointsDisplay() {
        pointsTextView.setText(String.valueOf(points));
    }

    private void redeemReward(int cost) {
        if (points >= cost) {
            points -= cost;
            updatePointsDisplay();
            Toast.makeText(getActivity(), "Reward redeemed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Not enough points", Toast.LENGTH_SHORT).show();
        }
    }
}
