package hackathon.rc.ca.hackathon.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hackathon.rc.ca.hackathon.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link MiniControllerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiniControllerFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mini_controller, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
