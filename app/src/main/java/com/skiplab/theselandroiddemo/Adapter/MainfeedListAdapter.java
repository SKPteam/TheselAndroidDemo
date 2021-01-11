package com.skiplab.theselandroiddemo.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skiplab.theselandroiddemo.Models.Like;
import com.skiplab.theselandroiddemo.R;
import com.skiplab.theselandroiddemo.Utils.Heart;
import com.skiplab.theselandroiddemo.Utils.SquareImageView;
import com.skiplab.theselandroiddemo.Models.Post;
import com.skiplab.theselandroiddemo.Models.User;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainfeedListAdapter extends ArrayAdapter<Post> {

    public interface OnLoadMoreItemsListener{
        void onLoadMoreItems();
    }
    OnLoadMoreItemsListener mOnLoadMoreItemsListener;

    private static final String TAG = "MainfeedListAdapter";

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private String currentUsername = "";

    public MainfeedListAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        mReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    static class ViewHolder{
        CircleImageView uDp;
        String likesString, pId;
        TextView uName, pTime, pDesc, likes, comments, uMood, pCategory;
        SquareImageView pImage;
        ImageView heartRed, heartWhite, comment;

        User user = new User();
        //boolean likeByCurrentUser;
        Heart heart;
        GestureDetector detector;
        Post post;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.uName = (TextView) convertView.findViewById(R.id.uNameTv);
            holder.uMood = convertView.findViewById(R.id.uMoodTv);
            holder.pImage = (SquareImageView) convertView.findViewById(R.id.pImageIv);
            holder.heartRed = (ImageView) convertView.findViewById(R.id.image_heart_red);
            holder.heartWhite = (ImageView) convertView.findViewById(R.id.image_heart);
            holder.comment = (ImageView) convertView.findViewById(R.id.commentBtn);
            holder.likes = (TextView) convertView.findViewById(R.id.pLikesTV);
            holder.comments = (TextView) convertView.findViewById(R.id.pCommentsTV);
            holder.pDesc = (TextView) convertView.findViewById(R.id.pDescTv);
            holder.pTime = (TextView) convertView.findViewById(R.id.timestampTv);
            holder.uDp = (CircleImageView) convertView.findViewById(R.id.uPictureIv);
            holder.pCategory = convertView.findViewById(R.id.pCategoryTv);
            holder.heart = new Heart(holder.heartWhite, holder.heartRed);
            holder.post = getItem(position);
            holder.detector = new GestureDetector(mContext, new GestureListener(holder));
            //holder.users = new StringBuilder();

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //convert timestamp to dd/mm/yyyy hh:mm am/pm
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(getItem(position).getpTime()));
        String pTime = DateFormat.format("dd/MM/yyyy   hh:mm aa", calendar).toString();

        setLikes(holder, holder.post.getpId());

//        private void setLikes(final PostViewHolder holder, final String postKey)
//        {
//            likesRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.child(postKey).hasChild(myUid)){
//                        //user has like this post
//                        /*To indicate this post is liked by this (signed in)  user
//                         * Chang drawable left icon of like button
//                         * Change text of like button from "Like" to "Liked"*/
//                        holder.mHeartWhite.setImageResource(R.drawable.ic_liked);
//                    }
//                    else {
//                        holder.mHeartWhite.setImageResource(R.drawable.ic_like);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    //..
//                }
//            });
//        }

//        if (holder.likeByCurrentUser){
//            holder.heartWhite.setVisibility(View.GONE);
//            holder.heartRed.setVisibility(View.VISIBLE);
//        }


        try{
            holder.pDesc.setText(getItem(position).getpDescription());
            holder.pCategory.setText(getItem(position).getpCategory());
            holder.uName.setText(getItem(position).getuName());
            holder.uMood.setText(getItem(position).getuMood());
            holder.pTime.setText(pTime);


            holder.heartWhite.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Log.d(TAG, "onTouch: red detected" );
                    return holder.detector.onTouchEvent(motionEvent);
                }
            });

            //set the profile image
            try {
                Glide
                        .with(mContext)
                        .load(getItem(position).getuDp())
                        .placeholder(R.drawable.default_image)
                        .into(holder.uDp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //get the profile image and username
        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("users")
                .orderByChild("uid")
                .equalTo(getItem(position).getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    // currentUsername = singleSnapshot.getValue(UserAccountSettings.class).getUsername();
                    Log.d(TAG, "onDataChange: found user: "
                            + singleSnapshot.getValue(User.class).getUsername());
                    holder.uName.setText(singleSnapshot.getValue(User.class).getUsername());
                    imageLoader.displayImage(singleSnapshot.getValue(User.class).getProfile_photo(),
                            holder.uDp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //..
            }
        });*/
        }catch (Exception e) {
            //..
        }

        if(reachedEndOfList(position)){
            loadMoreData();
        }

        return convertView;
    }

    private void setLikes(ViewHolder holder, String postKey) {
        Query query = mReference
                .child("likes");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                holder.likes.setText(String.valueOf(dataSnapshot.child(postKey).getChildrenCount()));

                    if (dataSnapshot.child(postKey).hasChild(mAuth.getUid()))
                    {
                        //Toast.makeText(mContext,"YES!!!", Toast.LENGTH_SHORT).show();
                        holder.heartWhite.setVisibility(View.GONE);
                        holder.heartRed.setVisibility(View.VISIBLE);
                    }

//                    Query query1 =  dataSnapshot.getRef()
//                            .orderByChild("user_id")
//                            .equalTo(mAuth.getCurrentUser().getUid());
//
//                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()){
//                                holder.heartWhite.setVisibility(View.GONE);
//                                holder.heartRed.setVisibility(View.VISIBLE);
//                            }
//                            else
//                            {
//                                holder.heartWhite.setVisibility(View.VISIBLE);
//                                holder.heartRed.setVisibility(View.GONE);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });


//                if (dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){
//                    holder.heartWhite.setVisibility(View.GONE);
//                    holder.heartRed.setVisibility(View.VISIBLE);
//                }
//                else {
//                    holder.heartWhite.setVisibility(View.GONE);
//                    holder.heartRed.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private boolean reachedEndOfList(int position){
        return position == getCount() - 1;
    }

    private void loadMoreData(){

        try{
            mOnLoadMoreItemsListener = (OnLoadMoreItemsListener) getContext();
        }catch (ClassCastException e){
            Log.e(TAG, "loadMoreData: ClassCastException: " +e.getMessage() );
        }

        try{
            mOnLoadMoreItemsListener.onLoadMoreItems();
        }catch (NullPointerException e){
            Log.e(TAG, "loadMoreData: ClassCastException: " +e.getMessage() );
        }
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {

        ViewHolder mHolder;

        public GestureListener(ViewHolder holder) {
            mHolder = holder;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap: double tap detected.");

            Query query = mReference
                    .child("likes")
                    .child(mHolder.post.getpId());

            query .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot singleSnapshot: dataSnapshot.getChildren())
                    {
                        String keyID = singleSnapshot.child(mAuth.getCurrentUser().getUid()).getKey();

                        if (singleSnapshot.getValue(Like.class).getUser_id()
                                .equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        {
                            Toast.makeText(mContext, "YES!!!", Toast.LENGTH_SHORT).show();
                            mHolder.heart.toggleLike();
                            //mHolder.heartWhite.setVisibility(View.VISIBLE);
                            //mHolder.heartRed.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(mContext, "NOOOO!!!", Toast.LENGTH_SHORT).show();
                            //mHolder.heartWhite.setVisibility(View.VISIBLE);
                            //mHolder.heartRed.setVisibility(View.GONE);
                        }

                    }

                    if(!dataSnapshot.exists()){
                        //add new like
                        addNewLike(mHolder);
                        //mHolder.likeByCurrentUser = true;
                    } else if (!dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists())
                    {
                        addNewLike(mHolder);
                        //mHolder.likeByCurrentUser = true;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return true;
        }
    }

    private void addNewLike(ViewHolder mHolder) {

        Like like = new Like();
        like.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mHolder.heart.toggleLike();

        mReference
                .child("likes")
                .child(mHolder.post.getpId())
                .child(mAuth.getCurrentUser().getUid())
                .setValue(like);


        //mHolder.likeByCurrentUser = true;

    }
}