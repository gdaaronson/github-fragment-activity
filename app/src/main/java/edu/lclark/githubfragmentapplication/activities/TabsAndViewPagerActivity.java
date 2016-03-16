package edu.lclark.githubfragmentapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.TabbedAsyncTask;
import edu.lclark.githubfragmentapplication.fragments.TabbedAdapter;
import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by Greg on 3/13/2016.
 */
public class TabsAndViewPagerActivity extends AppCompatActivity implements TabbedAsyncTask.GithubListener{

    ViewPager viewPager;

    TabLayout tabLayout;

    TabbedAsyncTask mAsyncTask;

    ArrayList<GithubUser> mFollowers;

    GithubUser user;

    TabbedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_with_tab);

        user = getIntent().getParcelableExtra(MainActivity.ARG_USER);

        setTitle(user.getLogin() + getString(R.string.users_followers));

        viewPager = (ViewPager)findViewById(R.id.fragment_user_with_tab_pager);

        tabLayout = (TabLayout) findViewById(R.id.fragment_user_with_tab_layout);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAsyncTask == null && mFollowers == null) {
            mAsyncTask = new TabbedAsyncTask(this);
            mAsyncTask.execute(user.getLogin());
        }
    }

    @Override
    public void onGithubTabbedFollowersRetrieved(@Nullable ArrayList<GithubUser> followers) {
        Log.d(TabsAndViewPagerActivity.class.getSimpleName(), "The followers were retrieved");

        adapter = new TabbedAdapter(getSupportFragmentManager(), followers);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

}
