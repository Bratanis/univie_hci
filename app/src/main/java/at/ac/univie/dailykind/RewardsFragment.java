package at.ac.univie.dailykind;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;

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


        reward1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points > 100) {
                    rewardsPopUp("LiScoVouch", 100);
                }
                else
                    redeemReward(100);
            }
        });

        reward2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points > 250) {
                    rewardsPopUp("Cleanerz", 250);
                }
                else
                    redeemReward(250);
            }
        });

        reward3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points > 500)
                    rewardsPopUp("The Apples", 500);
                else
                    redeemReward(500);

            }
        });

        reward4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points > 1000)
                    rewardsPopUp("Tech Helps", 1000);
                else
                    redeemReward(1000);

            }
        });

        reward5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points > 2000)
                    rewardsPopUp("Tratravelo", 2000);
                else
                    redeemReward(2000);
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


    private void rewardsPopUp(String rewardInfo, int cost) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Claiming reward");
        builder.setMessage(Html.fromHtml("Would you like to redeem the following voucher: <b>" + rewardInfo +
                " worth " + cost + " points? </b>", Html.FROM_HTML_MODE_LEGACY));

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                redeemReward(cost);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
