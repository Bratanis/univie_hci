package at.ac.univie.dailykind;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
//import androidx.window.core.Logger;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This section has referenced many aspects of the following github project: https://github.com/Everyday-Programmer/Android-Camera-using-CameraX/
public class CameraFragment extends Fragment {

    private ImageButton capture;
    private ImageButton toggleFlash;
    private PreviewView previewView;
    private int cameraFacing = CameraSelector.LENS_FACING_BACK;
    private Executor executor = Executors.newSingleThreadExecutor();
    private static final int ACTIVITY_SELECT_IMAGE = 1234;

    private static final Logger logger = LoggerFactory.getLogger(CameraFragment.class);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        previewView = view.findViewById(R.id.cameraPreview);
        capture = view.findViewById(R.id.capture);
        toggleFlash = view.findViewById(R.id.toggleFlash);
        ImageButton flipCamera = view.findViewById(R.id.flipCamera);
        ImageButton photoGallery = view.findViewById(R.id.photoGallery);

        requestPermissions();

        flipCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cameraFacing == CameraSelector.LENS_FACING_BACK) {
                    cameraFacing = CameraSelector.LENS_FACING_FRONT;
                } else {
                    cameraFacing = CameraSelector.LENS_FACING_BACK;
                }
                startCamera(cameraFacing);
            }
        });

        photoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()), "image/*");

                startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
            }
        });

        return view;
    }



    private void requestPermissions() {
        // Check if the permissions are granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Request permissions if they are not granted
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
        } else {
            // Permissions are already granted, start the camera
            startCamera(cameraFacing);
        }
    }



    private void startCamera(int cameraFacing) {
        int aspectRatio = aspectRatio(previewView.getWidth(), previewView.getHeight());
        ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(requireContext());

        listenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = listenableFuture.get();

                Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();

                ImageCapture imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setTargetRotation(requireActivity().getWindowManager().getDefaultDisplay().getRotation())
                        .build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(cameraFacing).build();

                cameraProvider.unbindAll();

                Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

                capture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                        } else {
                            takePicture(imageCapture);
                        }
                    }
                });

                toggleFlash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setFlashIcon(camera);
                    }
                });

                preview.setSurfaceProvider(previewView.getSurfaceProvider());
            } catch (ExecutionException | InterruptedException e) {
                logger.error("CameraFragment.startCamera() failed to start camera!");
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }


    private void takePicture(ImageCapture imageCapture) {

        // Create a file to save the captured image
        File file = createImageFile();

        if (file == null) {
            logger.warn("CameraFragment.takePicture() received a NULL file from CameraFragment.createImageFile()!");
            Toast.makeText(requireContext(), "Failed to create file", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set up output options for the image capture
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();

        // Capture the image
        imageCapture.takePicture(outputFileOptions, executor, new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                // Image capture successful
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(requireContext(), "Image saved at: " + file.getPath(), Toast.LENGTH_SHORT).show();
                    }
                });

                MediaScannerConnection.scanFile(requireContext(), new String[]{file.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        logger.info("Scan completed!");
                        // Navigate to the PostFragment and pass the file path of the picture taken
                        navigateToPostFragment(file.getAbsolutePath());
                    }
                });

                // Restart the camera preview
                startCamera(cameraFacing);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                // Error occurred during image capture

                // Run on UI thread to show an error toast message
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(requireContext(), "Failed to save: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                // Restart the camera preview
                startCamera(cameraFacing);
            }
        });
    }

    private void navigateToPostFragment(String filePath) {
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", filePath);

        // Assuming you have an ImageFragment that will display the image
        SubmitPostFragment submitPostFragment = new SubmitPostFragment();
        submitPostFragment.setArguments(bundle);

        // Navigate to the ImageFragment
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_mainActivity, submitPostFragment) // (For Dev) Possibly the wrong R.id here!
                .addToBackStack(null) // Optional: if you want to add this transaction to the back stack
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    navigateToPostFragment(filePath);
                }
            }
        }
    }




    private File createImageFile() {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        if (!storageDir.exists() && !storageDir.mkdirs()) {
            logger.warn("CameraFragment.createImageFile() couldn't find/create a directory " + storageDir + " to create the file in!");
            return null;
        }

        String fileName = System.currentTimeMillis() + ".jpg";

        File imageFile = new File(storageDir, fileName);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image captured by DailyKind");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
        ContentResolver contentResolver = requireContext().getContentResolver();
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        logger.info("CameraFragment.createImageFile() will create file:" + fileName + " at " + storageDir);
        return imageFile;
    }



    private void setFlashIcon(Camera camera) {
        if (camera.getCameraInfo().hasFlashUnit()) {
            if (camera.getCameraInfo().getTorchState().getValue() == 0) {
                camera.getCameraControl().enableTorch(true);
                toggleFlash.setImageResource(R.drawable.baseline_flash_off_24);
            } else {
                camera.getCameraControl().enableTorch(false);
                toggleFlash.setImageResource(R.drawable.baseline_flash_on_24);
            }
        } else {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(requireContext(), "Flash is not available currently", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private int aspectRatio(int width, int height) {
        double previewRatio = (double) Math.max(width, height) / Math.min(width, height);
        if (Math.abs(previewRatio - 4.0 / 3.0) <= Math.abs(previewRatio - 16.0 / 9.0)) {
            return AspectRatio.RATIO_4_3;
        }
        return AspectRatio.RATIO_16_9;
    }
}
