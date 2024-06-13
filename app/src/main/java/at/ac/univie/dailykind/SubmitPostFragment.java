package at.ac.univie.dailykind;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
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

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

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
            ImageView imageView = view.findViewById(R.id.imageView);
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
            String imagePath = getArguments().getString("imagePath");
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



// This part of the code attempts to fix the picture rotation issue


    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }
}


