package edu.lclark.githubfragmentapplication.models;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Greg on 3/8/2016.
 */
public class UserAsyncTask extends AsyncTask<String, Integer, GithubUser> {

    public static final String TAG = UserAsyncTask.class.getSimpleName();

    private OnUserLoginListener mListener;

    public UserAsyncTask(OnUserLoginListener loginListener) {
        mListener = loginListener;
    }


    public interface OnUserLoginListener{
        void onUserNameRetrived(GithubUser user);
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "Async was started");
    }

    @Override
    protected GithubUser doInBackground(String... params) {
        StringBuilder responseBuilder = new StringBuilder();
        GithubUser user = null;
        if (params.length == 0) {
            return null;
        }

        String userId = params[0];

        try {
            URL url = new URL("https://api.github.com/users/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            if (isCancelled()) {
                return null;
            }
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);

                if (isCancelled()) {
                    return null;
                }
            }

            user = new Gson().fromJson(responseBuilder.toString(), GithubUser.class);

            if (isCancelled()) {
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return user;
    }

    @Override
    protected void onPostExecute(GithubUser githubUser) {
        super.onPostExecute(githubUser);

        Log.d(TAG, "Async ended");
        mListener.onUserNameRetrived(githubUser);
    }
}
