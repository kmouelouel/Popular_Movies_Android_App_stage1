package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviesstage1.adapters.GridViewAdapter;
import com.example.android.popularmoviesstage1.models.Movie;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import com.example.android.popularmoviesstage1.utilities.OpenMoviesJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String SORT_BY_VALUE = "vote_average.desc";
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private GridView mGridView;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_Loading_indicator);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        //set the GridView :
        gridViewAdapter = new GridViewAdapter(MainActivity.this, new ArrayList<Movie>());

        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setAdapter(gridViewAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Movie MovieSelectedData = gridViewAdapter.getItem(position);
                Context context = getApplicationContext();
                Class destination = MovieDetail.class;
                Intent intentToDisplayMovieDetails = new Intent(context, destination);
                intentToDisplayMovieDetails.putExtra("movieDetails", MovieSelectedData);
                startActivity(intentToDisplayMovieDetails);
            }
        });

        loadMovieData(SORT_BY_VALUE);
    }

    private void loadMovieData(String sortByValue) {
        showMovieDataView();
        new FetchMoviesTask().execute(sortByValue);
    }

    private void invalidateData() {
        mGridView.setAdapter(null);
        gridViewAdapter.clear();
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mGridView.setVisibility(View.VISIBLE);

    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mGridView.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.settings, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        switch (item.getItemId()) {
            case R.id.action_most_popular:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                SORT_BY_VALUE = "popularity.desc";
                gridViewAdapter.clear();
                loadMovieData(SORT_BY_VALUE);
                return true;
            case R.id.action_highest_rated:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                SORT_BY_VALUE = "vote_average.desc";
                gridViewAdapter.clear();
                loadMovieData(SORT_BY_VALUE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String sortByValue = params[0];
            URL moviesRequestURL = NetworkUtils.buildUrl(sortByValue);
            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestURL);
                List<Movie> MoviesJsonData = OpenMoviesJsonUtils.getMoviesFromJson(MainActivity.this, jsonResponse);
                return MoviesJsonData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMovieDataView();
                gridViewAdapter.setData(movies);
            } else {
                showErrorMessage();
            }
        }


    }

}
