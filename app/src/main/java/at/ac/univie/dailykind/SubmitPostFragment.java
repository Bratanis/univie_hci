package at.ac.univie.dailykind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class SubmitPostFragment extends Fragment {

    private Uri imageUri;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the image path from the arguments
        Bundle arguments = getArguments();
        if (arguments != null) {
            String imagePath = arguments.getString("imagePath");

            // Display the image
            ImageView imageView = view.findViewById(R.id.imageView); // Assuming you have an ImageView in your fragment layout
            if (imagePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);
                imageView.setContentDescription("Image captured at " + imagePath);
            } else {
                Toast.makeText(requireContext(), "Image path is null", Toast.LENGTH_SHORT).show();
            }
        }

    }



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
