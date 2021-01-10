package com.skiplab.theselandroiddemo.SharePost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skiplab.theselandroiddemo.Common.Common;
import com.skiplab.theselandroiddemo.Dashboard;
import com.skiplab.theselandroiddemo.R;
import com.skiplab.theselandroiddemo.Utils.Permissions;

public class PostDescription extends AppCompatActivity {

    private static final String TAG = "ShareActivity";

    private Context mContext = PostDescription.this;

    private static final int VERIFY_PERMISSIONS_REQUEST = 1;
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    //image picked will be saved in this uri
    Uri image_uri = null;
    int i=0;

    private EditText pDescEt;
    private ImageView imageIv, fowardArrow;
    private FloatingActionButton selectImageFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_description);

        Log.d(TAG, "onCreate: started.");

        fowardArrow = findViewById(R.id.fowardArrow);
        pDescEt = findViewById(R.id.pDescEt);
        imageIv = findViewById(R.id.pImageIv);
        selectImageFab = findViewById(R.id.fab);

        selectImageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermissionsArray(Permissions.PERMISSIONS)){
                    showImagePickDialog();
                }else{
                    verifyPermissions(Permissions.PERMISSIONS);
                }
            }
        });

        fowardArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });

    }

    private void nextActivity() {

        String pDescription = pDescEt.getText().toString();

        if (TextUtils.isEmpty(pDescription)){
            Toast.makeText(mContext, "Type what's on your mind...", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if (image_uri == null){
                if(Common.isConnectedToTheInternet(getBaseContext()))
                {
                    Intent postIntent = new Intent(mContext, SelectMood.class);
                    postIntent.putExtra("pDesc", pDescription);
                    postIntent.putExtra("imageUri", "noImage");
                    startActivity(postIntent);

                    Log.d(TAG, "postInfo: "+ pDescription +", "+ image_uri);
                }
                else
                {
                    i++;

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Please check your internet connection");
                    builder.show();

                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(mContext, Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);

                    return;
                }
            }
            else {
                if(Common.isConnectedToTheInternet(getBaseContext()))
                {
                    Intent postIntent = new Intent(mContext, SelectMood.class);
                    postIntent.putExtra("pDesc", pDescription);
                    postIntent.putExtra("imageUri", String.valueOf(image_uri));
                    startActivity(postIntent);
                }
                else
                {
                    i++;

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Please check your internet connection");
                    builder.show();

                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(mContext, Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);

                    return;
                }
            }
        }

    }

    private void showImagePickDialog() {

        //options(Camera, Gallery) to show in Dialog
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image from:");
        //set options to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //Camera clicked
                    //We need to check permissions first
                    pickFromCamera();
                }
                if (which == 1){
                    //gallery clicked
                    pickFromGallery();
                }
            }
        });
        builder.show();
    }

    /**
     * Open phone gallery
     */
    private void pickFromGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    /**
     * Open phone camera
     */
    private void pickFromCamera() {
        //intent to pick image from camera
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Temp Desc");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }


    /**
     * verifiy all the permissions passed to the array
     * @param permissions
     */
    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: verifying permissions.");

        ActivityCompat.requestPermissions(
                (Activity) mContext,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }

    /**
     * Check an array of permissions
     * @param permissions
     * @return
     */
    private boolean checkPermissionsArray(String[] permissions) {
        Log.d(TAG, "checkPermissionsArray: checking permissions array.");

        for(int i = 0; i< permissions.length; i++){
            String check = permissions[i];
            if(!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }

    /**
     * Check a single permission is it has been verified
     * @param permission
     * @return
     */
    public boolean checkPermissions(String permission){
        Log.d(TAG, "checkPermissions: checking permission: " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(mContext, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermissions: \n Permission was not granted for: " + permission);
            return false;
        }
        else{
            Log.d(TAG, "checkPermissions: \n Permission was granted for: " + permission);
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                //image is picked from gallery, get uri of the image
                image_uri = data.getData();

                //set to imageView
                imageIv.setImageURI(image_uri);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                //image is picked from camera, get uri of the image

                imageIv.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
