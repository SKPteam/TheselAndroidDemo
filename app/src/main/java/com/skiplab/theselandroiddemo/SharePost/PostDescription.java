package com.skiplab.theselandroiddemo.SharePost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skiplab.theselandroiddemo.R;
import com.skiplab.theselandroiddemo.Utils.Permissions;

public class PostDescription extends AppCompatActivity {

    private static final String TAG = "ShareActivity";

    private Context mContext = PostDescription.this;

    private FloatingActionButton selectImageFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_description);

        Log.d(TAG, "onCreate: started.");

        selectImageFab = findViewById(R.id.fab);

        selectImageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ShareImageActivity.class));
            }
        });

    }
}
