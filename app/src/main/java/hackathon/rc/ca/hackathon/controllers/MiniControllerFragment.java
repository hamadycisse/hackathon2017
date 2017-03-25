package hackathon.rc.ca.hackathon.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hackathon.rc.ca.hackathon.App;
import hackathon.rc.ca.hackathon.R;
import hackathon.rc.ca.hackathon.dtos.Playlist;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link MiniControllerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiniControllerFragment extends Fragment {

    private SeekBar seekBar;
    private TextView titleTV;

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
        /*
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mini_controller, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private App getApp() {
        return (App) getActivity().getApplication();
    }

    @OnClick(R.id.Play)
    public void onPlayButton() {
        getApp().getPlaybackManager().resume();

    }

    @OnClick(R.id.Pause)
    public void onPauseButton() {
        getApp().getPlaybackManager().pause();
    }
}
