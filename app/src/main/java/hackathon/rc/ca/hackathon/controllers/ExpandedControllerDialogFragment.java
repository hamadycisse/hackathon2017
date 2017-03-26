package hackathon.rc.ca.hackathon.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hackathon.rc.ca.hackathon.App;
import hackathon.rc.ca.hackathon.R;

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

    private Unbinder mUnbinder;

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
        (getApplication()).getPlaybackManager().playNext();
    }

    @OnClick(R.id.previous)
    public void onPrevious() {
        (getApplication()).getPlaybackManager().playPrevious();
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
