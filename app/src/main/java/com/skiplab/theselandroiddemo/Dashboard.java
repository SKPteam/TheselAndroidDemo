package com.skiplab.theselandroiddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skiplab.theselandroiddemo.Activity.NotificationsFragment;
import com.skiplab.theselandroiddemo.Adapter.MainfeedListAdapter;
import com.skiplab.theselandroiddemo.Home.HomeFragment;
import com.skiplab.theselandroiddemo.Utils.SectionsPagerAdapter;
import com.skiplab.theselandroiddemo.Settings.AccountSettingsActivity;
import com.skiplab.theselandroiddemo.SharePost.PostDescription;

public class Dashboard extends AppCompatActivity implements
        MainfeedListAdapter.OnLoadMoreItemsListener {

    @Override
    public void onLoadMoreItems() {
        Log.d(TAG, "onLoadMoreItems: displaying more photos");
        HomeFragment fragment = (HomeFragment)getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
        if(fragment != null){
            fragment.displayMorePosts();
        }
    }

    private static final String TAG = "Dashboard";
    private static final int ACTIVITY_NUM = 0;
    private static final int HOME_FRAGMENT = 0;
    private String mUID;
    private BottomAppBar bottomAppBar;


    private Context mContext = Dashboard.this;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //widgets
    private ViewPager mViewPager;
    private FrameLayout mFrameLayout;

    public static boolean isActivityRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Log.d(TAG, "onCreate: starting.");

        mAuth = FirebaseAuth.getInstance();

        checkAuthenticationState();
        setupFirebaseAuth();

        mViewPager = (ViewPager) findViewById(R.id.container);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_container);
        bottomAppBar = findViewById(R.id.bottomAppBar);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //Home button
                        break;
//                    case R.id.nav_share:
//                        startActivity(new Intent(mContext, PostDescription.class));
//                        break;
                    case R.id.nav_settings:
                        startActivity(new Intent(mContext, AccountSettingsActivity.class));
                        break;
                }
                return false;
            }
        });

        setupViewPager();

    }


    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment()); //index 0
        adapter.addFragment(new NotificationsFragment()); //index 1
        mViewPager.setAdapter(adapter);
    }

    private void checkAuthenticationState()
    {
        Log.d( TAG, "checkAuthenticationState: check authentication state." );

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
        {
            Intent intent = new Intent( Dashboard.this, SplashScreen.class );
            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( intent );
            finish();
        } else {
            mUID = user.getUid();

            Log.d( TAG, "checkAuthenticationState: user is authenticated." );
            //save uid of curently signed in user in shared preferences
            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID", mUID);
            editor.apply();
        }
    }


    private void setupFirebaseAuth() {
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null)
            {
                Log.d( TAG, "onAuthStateChanged: signed_in: " + user.getUid());

            } else {
                Log.d( TAG, "onAuthStateChanged: signed_out");
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        isActivityRunning = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        isActivityRunning = false;
    }
}
