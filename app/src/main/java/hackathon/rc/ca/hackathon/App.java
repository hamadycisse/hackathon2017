package hackathon.rc.ca.hackathon;

import android.app.Application;

import com.fasterxml.jackson.core.JsonFactory;

import hackathon.rc.ca.hackathon.client.BingSpeechApiServiceInterface;
import hackathon.rc.ca.hackathon.client.NeuroApiServiceInterface;
import hackathon.rc.ca.hackathon.client.ValidationMediaApiServiceInterface;
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

    private ValidationMediaApiServiceInterface mValidationMediaApiService;
    public ValidationMediaApiServiceInterface getValidationMediaApiService() {
        return mValidationMediaApiService;
    }

    private BingSpeechApiServiceInterface mBingSpeechApiService;

    public BingSpeechApiServiceInterface getBingSpeechApiService() {
        return mBingSpeechApiService;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final JacksonConverterFactory factory = JacksonConverterFactory.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://services.radio-canada.ca/hackathon2017/")
                .addConverterFactory(factory)
                .build();

        mNeuroApiService = retrofit.create(NeuroApiServiceInterface.class);

        Retrofit retrofitValidationMedia = new Retrofit.Builder()
                .baseUrl("http://api.radio-canada.ca")
                .addConverterFactory(factory)
                .build();

        mValidationMediaApiService = retrofitValidationMedia.create(ValidationMediaApiServiceInterface.class);

        Retrofit retrofitBingSpeech = new Retrofit.Builder()
                .baseUrl("https://speech.platform.bing.com/")
                .addConverterFactory(factory)
                .build();

        mBingSpeechApiService = retrofitBingSpeech
                .create(BingSpeechApiServiceInterface.class);

        mPlaybackManager = new PlaybackManager(getApplicationContext(),
                mValidationMediaApiService, mBingSpeechApiService);
    }


}
