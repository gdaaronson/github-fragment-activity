package edu.lclark.githubfragmentapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by ntille on 2/25/16.
 */
public class UserFragmentForTabs extends Fragment {

    public static final String ARG_USER = "UserFragmentForTabs.User";

    ImageView mImageView;

    TextView mNameTextView;


    public static UserFragmentForTabs newInstance(GithubUser user) {
        UserFragmentForTabs fragment = new UserFragmentForTabs();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        mNameTextView = (TextView)rootView.findViewById(R.id.fragment_user_with_tab_username_textview);

        GithubUser mUser = getArguments().getParcelable(ARG_USER);

        mImageView = (ImageView) rootView.findViewById(R.id.fragment_user_with_tab_imageview);

        Picasso.with(getActivity()).load(mUser.getAvatar_url()).fit().centerInside().into(mImageView);

        mNameTextView.setText(mUser.getLogin());

        Log.d("UserFragmentForTabs", "Username: " + mUser.getLogin());

        return rootView;
    }
}
