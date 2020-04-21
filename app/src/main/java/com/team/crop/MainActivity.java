package com.team.crop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_GALLERY = 123;
    private static final int CAMERA_REQUEST = 1888;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView mGallery = (CardView) findViewById(R.id.gallery);
        CardView mCamera = (CardView) findViewById(R.id.camera);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(MainActivity.this)
                        .withPermissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);

                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();


            }
        });

        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(MainActivity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                        showCamera();


                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();




            }
        });
    }

    private void showCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(MainActivity.this.getPackageManager()) != null) {
            File file = null;
            try {
                file = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoUri = null;
            if (file != null) {
                photoUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = MainActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(timeStamp, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
            if (requestCode == REQUEST_CODE_GALLERY) {
                Uri selectedImage = data.getData();

                Intent intent = new Intent(MainActivity.this, OutpuActivity.class);
                intent.putExtra("imagePath", selectedImage.toString());
                startActivity(intent);
            }else if (requestCode == CAMERA_REQUEST){

                if (photoUri != null) {
                    Intent intent = new Intent(MainActivity.this, OutpuActivity.class);
                    intent.putExtra("imagePath", photoUri.toString());
                    startActivity(intent);

                }

            }


    }
}
