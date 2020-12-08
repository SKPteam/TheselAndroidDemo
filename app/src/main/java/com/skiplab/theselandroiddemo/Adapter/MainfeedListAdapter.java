package com.skiplab.theselandroiddemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.skiplab.theselandroiddemo.Utils.SquareImageView;
import com.skiplab.theselandroiddemo.models.Post;
import com.skiplab.theselandroiddemo.models.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainfeedListAdapter extends ArrayAdapter<Post> {

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
    }

    static class ViewHolder{
        CircleImageView mprofileImage;
        String likesString;
        TextView username, pTime, postDesc, likes, comments;
        SquareImageView image;
        ImageView heartRed, heartWhite, comment;

        User user = new User();
    }
}
