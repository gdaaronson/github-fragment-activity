package edu.lclark.githubfragmentapplication.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.activities.MainActivity;
import edu.lclark.githubfragmentapplication.models.GithubUser;
import edu.lclark.githubfragmentapplication.models.UserAsyncTask;

/**
 * Created by Greg on 3/8/2016.
 */
public class BeginningFragment extends Fragment implements View.OnClickListener, UserAsyncTask.OnUserLoginListener {

    private EditText editText;

    private UserAsyncTask mAsyncTask;

    private Button button;

    private ProgressBar progressBar;

    private UserListener mListener;

    @Override
    public void onUserNameRetrived(GithubUser user) {
        if (user == null){
            Toast.makeText(this.getContext(), R.string.user_not_found, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            button.setVisibility(View.VISIBLE);
            mAsyncTask = null;
        } else {
            mListener.onUserNameRetrived(user);
        }
    }

    public interface UserListener {
        void onUserNameRetrived(GithubUser user);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beginning, container, false);

        editText = (EditText) rootView.findViewById(R.id.fragment_beginning_edittext);

        button = (Button) rootView.findViewById(R.id.fragment_beginning_button);

        button.setOnClickListener(this);

        progressBar = (ProgressBar)rootView.findViewById(R.id.fragment_beginning_progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.fragment_beginning_imageview);

        Picasso.with(rootView.getContext()).load("https://assets-cdn.github.com/images/modules/logos_page/Octocat.png").fit().centerInside().into(imageView);

        mListener = (MainActivity) getActivity();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fragment_beginning_button ){
            Log.d(this.getClass().getSimpleName(), "The button was pressed");
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                Toast.makeText(this.getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
            } else if(mAsyncTask == null){
                progressBar.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);
                Log.d(this.getClass().getSimpleName(), "Async is starting");
                mAsyncTask = new UserAsyncTask(this);
                mAsyncTask.execute(editText.getText().toString());
            }
        }
    }
}
