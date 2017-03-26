package hackathon.rc.ca.hackathon.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hackathon.rc.ca.hackathon.App;
import hackathon.rc.ca.hackathon.R;
import hackathon.rc.ca.hackathon.dtos.PlaylistItem;
import hackathon.rc.ca.hackathon.player.PlaybackManager;

/**
 * Created by Hamady Cissé on 2017-03-26.
 * Responsibilities:        ....
 * States:                   N/A
 * Dependencies:
 * Final dependencies:    N/A
 * Differed dependencies: N/A
 * Inner classes/interfaces: N/A
 */

public class ExpandedControllerDialogFragment extends DialogFragment {

    public static final String SUMMARY = "summary";
    public static final String TITLE = "title";
    public static final String IMAGE = "image";

    @BindView(R.id.element_title) TextView mTitle;
    @BindView(R.id.imageView) ImageView mImage;

    private Unbinder mUnbinder;

    public static ExpandedControllerDialogFragment newInstance(final String image,
                                                               final String title) {
        
        Bundle args = new Bundle();
        args.putString(IMAGE, image);
        args.putString(TITLE, title);
        
        ExpandedControllerDialogFragment fragment = new ExpandedControllerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ExpandedControllerDialogFragment() {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreen);
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.expanded_controller, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String title = getArguments().getString(TITLE);
        final String image = getArguments().getString(IMAGE);
        updateMeta(title, image);
    }

    private void updateMeta(final String title, final String image) {
        mTitle.setText(Html.fromHtml(title));
        Glide.with(getContext()).load(image).into(mImage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @OnClick(R.id.next)
    public void onNext() {
        final PlaybackManager playbackManager = getApplication().getPlaybackManager();
        playbackManager.playNext();
        updateFromCurrentTrack(playbackManager);
    }

    @OnClick(R.id.previous)
    public void onPrevious() {
        final PlaybackManager playbackManager = getApplication().getPlaybackManager();
        playbackManager.playPrevious();
        updateFromCurrentTrack(playbackManager);
    }

    private void updateFromCurrentTrack(final PlaybackManager playbackManager) {
        final PlaylistItem currentTrack = playbackManager.getCurrentTrack();
        if (currentTrack != null) {
            updateMeta(currentTrack.getSummaryMultimediaItem().getTitle(), currentTrack
                    .getSummaryMultimediaItem().getSummaryImage().getConcreteImages().get(0)
                    .getMediaLink().getHref());
        }
    }

    @OnClick(R.id.play)
    public void onPlay() {
        (getApplication()).getPlaybackManager().resume();
    }

    @OnClick(R.id.pause)
    public void onPlayerPause() {
        getApplication().getPlaybackManager().pause();
    }

    private App getApplication() {
        return (App)getActivity().getApplication();
    }
}
