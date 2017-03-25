package hackathon.rc.ca.hackathon.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hackathon.rc.ca.hackathon.App;
import hackathon.rc.ca.hackathon.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link MiniControllerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiniControllerFragment extends Fragment {

    private Unbinder mUnbinder;


    @BindView(R.id.Play) ImageButton mPlayButton;
    @BindView(R.id.Pause) ImageButton mPauseButton;

    public MiniControllerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MiniControllerFragment newInstance() {
        MiniControllerFragment fragment = new MiniControllerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.mini_controller, container, false);
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
    public void onDetach() {
        super.onDetach();
    }

    private App getApp() {
        return (App) getActivity().getApplication();
    }

    @OnClick(R.id.Play)
    public void onPlay() {
        getApp().getPlaybackManager().resume();
        mPlayButton.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.Pause)
    public void onPaused() {
        getApp().getPlaybackManager().pause();
        mPlayButton.setVisibility(View.VISIBLE);
    }
}
