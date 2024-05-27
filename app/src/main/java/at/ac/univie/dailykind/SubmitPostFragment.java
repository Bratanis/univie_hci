package at.ac.univie.dailykind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class SubmitPostFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

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
}
