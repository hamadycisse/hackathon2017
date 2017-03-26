package hackathon.rc.ca.hackathon.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hackathon.rc.ca.hackathon.App;
import hackathon.rc.ca.hackathon.R;
import hackathon.rc.ca.hackathon.dtos.Playlist;
import hackathon.rc.ca.hackathon.dtos.PlaylistItem;
import hackathon.rc.ca.hackathon.dtos.SummaryMultimediaItem;
import retrofit2.Call;

import static android.view.View.GONE;

/**
 * A fragment representing a single Playlist detail screen.
 * This fragment is either contained in a {@link PlaylistListActivity}
 * in two-pane mode (on tablets) or a {@link PlaylistDetailActivity}
 * on handsets.
 */
public class PlaylistDetailFragment extends Fragment {


    public static final String TAG = "PlayListDetailFragment";
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    @BindView(R.id.playlist_list) RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
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
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PlaylistDetailActivity activity = (PlaylistDetailActivity)this.getActivity();
        final FloatingActionButton playlistStartButton = activity.getPlaylistPlayButton();

        final String itemId = "91549";
        playlistStartButton.setVisibility(GONE);
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
                    mRecyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mPlaylist.getItems()));
                }

                playlistStartButton.setVisibility(View.VISIBLE);
                playlistStartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        playlistStartButton.setVisibility(View.GONE);
                        getApp().getPlaybackManager().play(mPlaylist);
                        activity.showMiniController();
                    }
                });
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    private App getApp() {
        return (App) getActivity().getApplication();
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<PlaylistItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<PlaylistItem> items) {
            mValues = items;
        }

        @Override
        public SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.playlist_item_layout, parent, false);
            return new SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            final SummaryMultimediaItem summaryMultimediaItem = mValues.get(position).getSummaryMultimediaItem();
            holder.mContentView.setText(summaryMultimediaItem.getTitle());
            Glide.with(getContext()).load(summaryMultimediaItem.getSummaryImage()
                    .getConcreteImages().get(0).getMediaLink()
                    .getHref())
            .into(holder.mThumbnail);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
// TODO Start Media Playback Here
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mContentView;
            public final ImageView mThumbnail;

            public PlaylistItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.element_title);
                mThumbnail = (ImageView) view.findViewById(R.id.thumbnail_view);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
