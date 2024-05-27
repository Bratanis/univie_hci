package at.ac.univie.dailykind;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SubmitPostFragment extends Fragment {

    private Uri imageUri;

    public SubmitPostFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_post, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextInputEditText editTextComment = view.findViewById(R.id.editTextComment);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);

        if (getArguments() != null) {
            imageUri = getArguments().getParcelable("imageUri");
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
            }
        }

        buttonSubmit.setOnClickListener(v -> {
            // Handle the submit action (e.g., send the data to the server or database)
            String comment = editTextComment.getText().toString();
            // TODO: Implement the submit logic here

            // Show a toast message
            Toast.makeText(getContext(), "Post submitted", Toast.LENGTH_SHORT).show();

            // Close the current fragment
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
        });

        return view;
    }
}
