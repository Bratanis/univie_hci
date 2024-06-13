package at.ac.univie.dailykind;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText streetEditText;
    private EditText zipCodeEditText;
    private EditText cityEditText;
    private Spinner countryPicker;
    private Button editButton;
    private boolean isEditable = false;
    private boolean isPasswordVisible = false;

    private ImageButton togglePasswordVisibilityButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);
        streetEditText = view.findViewById(R.id.street);
        zipCodeEditText = view.findViewById(R.id.zip_code);
        cityEditText = view.findViewById(R.id.city);
        countryPicker = view.findViewById(R.id.country_picker);
        editButton = view.findViewById(R.id.edit_button);
        togglePasswordVisibilityButton = view.findViewById(R.id.toggle_password_visibility);

        // Set initial data (Replace with real data)
        emailEditText.setText("whatever@mail.com");
        passwordEditText.setText("password123");
        streetEditText.setText("Street name");
        zipCodeEditText.setText("12345");
        cityEditText.setText("Cityname");

        // Setup country picker with example data
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.country_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryPicker.setAdapter(adapter);
        countryPicker.setSelection(0); // Set initial selection (Replace with real data)

        // Set up the edit button click listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEdit();
            }
        });

        togglePasswordVisibilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        //load calendar
        //openCalendarFragmentBelowRecyclerView();

        FloatingActionButton buttonCalendar = view.findViewById(R.id.calenderIcon);
        buttonCalendar.setOnClickListener(v -> {
            Fragment calendarFrag = new CalendarFragment();
            Bundle args = new Bundle();
            calendarFrag.setArguments(args);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frame_mainActivity, calendarFrag)
                    .addToBackStack(null)
                    .commit();
        });


        return view;
    }

    private void toggleEdit() {
        isEditable = !isEditable;

        emailEditText.setEnabled(isEditable);
        passwordEditText.setEnabled(isEditable);
        streetEditText.setEnabled(isEditable);
        zipCodeEditText.setEnabled(isEditable);
        cityEditText.setEnabled(isEditable);
        countryPicker.setEnabled(isEditable);

        if (!isEditable) {
            // Save the profile information when edit mode is disabled
            saveProfile();
        }

        editButton.setText(isEditable ? "Save" : "Change Data");
    }

    private void togglePasswordVisibility()
    {
        if(isPasswordVisible)
        {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            togglePasswordVisibilityButton.setImageResource(R.drawable.invisible_eye);
        }
        else
        {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            togglePasswordVisibilityButton.setImageResource(R.drawable.visible_eye);
        }

        isPasswordVisible = !isPasswordVisible;
    }
    private void saveProfile() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String street = streetEditText.getText().toString();
        String zipCode = zipCodeEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String country = countryPicker.getSelectedItem().toString();

        // TODO: Add logic to save the profile information (e.g., save to database, shared preferences, etc.)

        // For demonstration, show a toast message
        Toast.makeText(getActivity(), "Profile saved!", Toast.LENGTH_SHORT).show();
    }


    //calendar section
  /*  private void openCalendarFragment() {
        CalendarFragment calendarFragment = new CalendarFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_mainActivity, calendarFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openCalendarFragmentBelowRecyclerView() {
        CalendarFragment calendarFragment = new CalendarFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.calendar_container, calendarFragment);
        transaction.commit();
    }*/
}