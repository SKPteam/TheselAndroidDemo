package com.skiplab.theselandroiddemo.Home;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skiplab.theselandroiddemo.Adapter.MainfeedListAdapter;
import com.skiplab.theselandroiddemo.Models.User;
import com.skiplab.theselandroiddemo.R;
import com.skiplab.theselandroiddemo.Models.Post;
import com.skiplab.theselandroiddemo.Models.UserAccountSettings;
import com.skiplab.theselandroiddemo.SharePost.PostDescription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private FirebaseAuth mAuth;
    private DatabaseReference usersDb, userAccountSettingsDb;

    //vars
    private ArrayList<Post> mPosts;
    private ArrayList<Post> mPaginatedPosts;
    private ListView mListView, categoryList;
    private MainfeedListAdapter mAdapter;
    private int mResults;
    private String[] categories;

    private Switch anonymitySwitch;
    private ImageView drawerIcon;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private EditText sharePostEt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        usersDb = FirebaseDatabase.getInstance().getReference("users");
        userAccountSettingsDb = FirebaseDatabase.getInstance().getReference("user_account_settings");

        mListView = (ListView) view.findViewById(R.id.list_posts);
        mListView.isStackFromBottom();
        anonymitySwitch = view.findViewById(R.id.hide_identity_switch);
        drawerIcon = view.findViewById(R.id.drawer_icon);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        sharePostEt = view.findViewById(R.id.share_post_et);

        sharePostEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startActivity(new Intent(getActivity(), PostDescription.class));
                return false;
            }
        });

        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        categoryList = view.findViewById(R.id.navList);

        categoryList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        categories = new String[]{"Relationship", "Addiction", "Depression", "Parenting", "Career", "Self-Esteem", "Disabilities", "Hopes",
                "Family", "Anxiety", "Pregnancy", "Religion", "Business", "Weight Loss", "Fitness", "Grief", "Bullying", "Education", "Music", "Helpful Tips"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), R.layout.category_list_item, R.id.categoryListItem, categories);
        categoryList.setAdapter(adapter);
        categoryList.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedCategory = (String) parent.getItemAtPosition(position);
            mDrawerLayout.closeDrawer(GravityCompat.START);

            Toast.makeText(getActivity(),"Category selected "+selectedCategory, Toast.LENGTH_SHORT).show();
        });

        anonymitySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (anonymitySwitch.isChecked())
                {
                    Log.d(TAG, "Anonymity: INVISIBLE");
                    userAccountSettingsDb.child(mAuth.getUid()).child("anonymous").setValue(true)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                            .setMessage("You have hidden your identity!")
                                            .create();
                                    alertDialog.show();
                                }
                            });
                }
                else
                {
                    Log.d(TAG, "Anonymity: VISIBLE");
                    userAccountSettingsDb.child(mAuth.getUid()).child("anonymous").setValue(false)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                            .setMessage("You are now visible!")
                                            .create();
                                    alertDialog.show();
                                }
                            });
                }
            }
        });

        getPosts();

        setupDrawer();

        return view;
    }

    private void getPosts() {
        mPosts = new ArrayList<>();

        Log.d(TAG, "getPosts: getting posts");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("posts");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    Post post = new Post();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                    post.setpDescription(objectMap.get("pDescription").toString());
                    post.setpCategory(objectMap.get("pCategory").toString());
                    post.setuMood(objectMap.get("uMood").toString());
                    post.setuName(objectMap.get("uName").toString());
                    //post.setTags(objectMap.get("tags").toString());
                    post.setpId(objectMap.get("pId").toString());
                    post.setUid(objectMap.get("uid").toString());
                    post.setpTime(objectMap.get("pTime").toString());
                    post.setuDp(objectMap.get("uDp").toString());

                    mPosts.add(post);

                    displayPosts();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void displayPosts(){
        mPaginatedPosts = new ArrayList<>();
        if(mPosts != null){
            try{
                Collections.sort(mPosts, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return o2.getpTime().compareTo(o1.getpTime());
                    }
                });

                int iterations = mPosts.size();

                if(iterations > 5){
                    iterations = 5;
                }

                mResults = 5;
                for(int i = 0; i < iterations; i++){
                    mPaginatedPosts.add(mPosts.get(i));
                }

                mAdapter = new MainfeedListAdapter(getActivity(), R.layout.layout_mainfeed_listitem, mPaginatedPosts);
                mListView.setAdapter(mAdapter);

            }catch (NullPointerException e){
                Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
            }catch (IndexOutOfBoundsException e){
                Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
            }
        }
    }

    public void displayMorePosts() {
        Log.d(TAG, "displayMorePhotos: displaying more posts");

        try{
            if(mPosts.size() > mResults && mPosts.size() > 0){

                int iterations;
                if(mPosts.size() > (mResults + 5)){
                    Log.d(TAG, "displayMorePhotos: there are greater than 10 more photos");
                    iterations = 5;
                }else{
                    Log.d(TAG, "displayMorePhotos: there is less than 10 more photos");
                    iterations = mPosts.size() - mResults;
                }

                //add the new posts to the paginated results
                for(int i = mResults; i < mResults + iterations; i++){
                    mPaginatedPosts.add(mPosts.get(i));
                }
                mResults = mResults + iterations;
                mAdapter.notifyDataSetChanged();
            }
        }catch (NullPointerException e){
            Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
        }catch (IndexOutOfBoundsException e){
            Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
        }
    }

}