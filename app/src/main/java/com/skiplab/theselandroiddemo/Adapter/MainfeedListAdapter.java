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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private String currentUsername = "";

    public MainfeedListAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    static class ViewHolder{
        CircleImageView uDp;
        String likesString, pId;
        TextView uName, pTime, pDesc, likes, comments, uMood, pCategory;
        SquareImageView pImage;
        ImageView heartRed, heartWhite, comment;

        User user = new User();
        boolean likeByCurrentUser;
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

        try{
            holder.pDesc.setText(getItem(position).getpDescription());
            holder.pCategory.setText(getItem(position).getpCategory());
            holder.uName.setText(getItem(position).getuName());
            holder.uMood.setText(getItem(position).getuMood());
            holder.pTime.setText(pTime);

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

            return true;
        }
    }
}
