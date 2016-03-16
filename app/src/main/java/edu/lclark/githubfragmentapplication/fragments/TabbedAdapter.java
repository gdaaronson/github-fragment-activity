package edu.lclark.githubfragmentapplication.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by Greg on 3/13/2016.
 */
public class TabbedAdapter extends FragmentPagerAdapter {

    private ArrayList<GithubUser> followers;

    public TabbedAdapter(FragmentManager fm, ArrayList<GithubUser> followers) {
        super(fm);
        this.followers = followers;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(UserFragmentForTabs.class.getSimpleName(), "Fragment created for " + followers.get(position).getLogin() + " at " + position);
        return UserFragmentForTabs.newInstance(followers.get(position));

    }

    @Override
    public int getCount() {
        return followers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return followers.get(position).getLogin();
    }
}
