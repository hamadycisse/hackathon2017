package hackathon.rc.ca.hackathon.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hackathon.rc.ca.hackathon.App;
import hackathon.rc.ca.hackathon.R;
import hackathon.rc.ca.hackathon.dtos.Playlist;
import retrofit2.Call;

/**
 * A fragment representing a single Playlist detail screen.
 * This fragment is either contained in a {@link PlaylistListActivity}
 * in two-pane mode (on tablets) or a {@link PlaylistDetailActivity}
 * on handsets.
 */
public class PlaylistDetailFragment extends Fragment {


    private Unbinder mUnbinder;

    @BindView(R.id.playlist_detail) TextView mSummaryText;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Playlist mPlaylist;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaylistDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.playlist_detail, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Activity activity = this.getActivity();
        if (getArguments().containsKey(ARG_ITEM_ID)) {

            final String itemId = getArguments().getString(PlaylistDetailFragment.ARG_ITEM_ID);
            Task.callInBackground(new Callable<Playlist>() {
                @Override
                public Playlist call() throws Exception {
                    final Call<Playlist> playlistCall = getApp()
                            .getNeuroApiService()
                            .getPlaylist(itemId);
                    return playlistCall.execute().body();
                }
            }).continueWith(new Continuation<Playlist, Void>() {
                @Override
                public Void then(final Task<Playlist> task) throws Exception {

                    if (task.isFaulted()) {
                        Snackbar.make(getView(), "Une erreur s'est produite: " + task.getError()
                                .getMessage(), Snackbar.LENGTH_LONG).show();
                        return null;
                    }
                    mPlaylist = task.getResult();

                    CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                    if (appBarLayout != null) {
                        appBarLayout.setTitle(mPlaylist.getTitle());
                    }

                    // Show the dummy content as text in a TextView.
                    if (mPlaylist != null) {
                        mSummaryText.setText(Html.fromHtml(mPlaylist.getSummary()));
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        }
    }

    private App getApp() {
        return (App) getActivity().getApplication();
    }

    @OnClick(R.id.playlist_detail)
    public void onPlay() {

        getApp().getPlaybackManager().play(mPlaylist);
    }
}
