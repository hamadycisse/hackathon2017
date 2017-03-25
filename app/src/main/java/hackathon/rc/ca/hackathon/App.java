package hackathon.rc.ca.hackathon;

import android.app.Application;

import com.fasterxml.jackson.core.JsonFactory;

import hackathon.rc.ca.hackathon.client.NeuroApiServiceInterface;
import hackathon.rc.ca.hackathon.player.PlaybackManager;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Hamady Cissé on 2017-03-25.
 * Responsibilities:       The root class of the Application
 * States:                   N/A
 * Dependencies:
 * Final dependencies:    N/A
 * Differed dependencies: N/A
 * Inner classes/interfaces: N/A
 */

public class App extends Application {

    private NeuroApiServiceInterface mNeuroApiService;
    public NeuroApiServiceInterface getNeuroApiService() {
        return mNeuroApiService;
    }

    private PlaybackManager mPlaybackManager;
    public PlaybackManager getPlaybackManager() {
        return mPlaybackManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://services.radio-canada.ca/hackathon2017/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        mNeuroApiService = retrofit.create(NeuroApiServiceInterface.class);

        mPlaybackManager = new PlaybackManager(getApplicationContext());
    }


}
