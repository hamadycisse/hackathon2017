package hackathon.rc.ca.hackathon.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import hackathon.rc.ca.hackathon.R;

/**
 * An activity representing a single Playlist detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PlaylistListActivity}.
 */
public class PlaylistDetailActivity extends AppCompatActivity {

    private FloatingActionButton mPlaylistPlayButton;
    public FloatingActionButton getPlaylistPlayButton() {
        return mPlaylistPlayButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        mPlaylistPlayButton = (FloatingActionButton) findViewById(R.id.start_playlist);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(PlaylistDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(PlaylistDetailFragment.ARG_ITEM_ID));
            PlaylistDetailFragment fragment = new PlaylistDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.playlist_detail_container, fragment)
                    .commit();
        }
    }

    public void showMiniController() {
        MiniControllerFragment miniControllerFragment = new MiniControllerFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mini_controller_container, miniControllerFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.settings:
                Intent launchNewIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(launchNewIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
