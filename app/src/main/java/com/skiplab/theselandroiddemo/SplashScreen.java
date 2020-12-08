package com.skiplab.theselandroiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "SplashScreen";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    Context mContext = SplashScreen.this;

    Animation skip2big;

    private ImageView appNameIv;
    private TextView loginTv, backTv;
    private ImageButton switchBtn;
    private Button userBtn, counsellorBtn;

    int i = 0;

    public static boolean isActivityRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Now the listener will be actively listening for changes in the authentication state
        setupFirebaseAuth();
    }

    private void setupFirebaseAuth()
    {
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null)
            {
                Log.d( TAG, "onAuthStateChanged: signed_in: " + user.getUid());

                Intent intent = new Intent( mContext, Dashboard.class );
                startActivity( intent );
                finish();

            } else {
                Log.d( TAG, "onAuthStateChanged: signed_out");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreen.this, AuthenticationScreen.class));
                    }
                }, 2000);
            }
        };
    }

    //Everything you need to use the authStateListener Object
    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
        isActivityRunning = false;
    }
}
