package hackathon.rc.ca.hackathon.player;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import bolts.Capture;
import bolts.Continuation;
import bolts.Task;
import hackathon.rc.ca.hackathon.client.BingAuthApiServiceInterface;
import hackathon.rc.ca.hackathon.client.BingSpeechApiServiceInterface;
import hackathon.rc.ca.hackathon.client.ValidationMediaApiServiceInterface;
import hackathon.rc.ca.hackathon.dtos.Playlist;
import hackathon.rc.ca.hackathon.dtos.PlaylistItem;
import hackathon.rc.ca.hackathon.dtos.SummaryMultimediaItem;
import hackathon.rc.ca.hackathon.dtos.ValidationMedia;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Hamady Cissé on 2017-03-25.
 * Responsibilities:     Responsible of handling the playback
 * States:                   N/A
 * Dependencies:
 * Final dependencies:    N/A
 * Differed dependencies: N/A
 * Inner classes/interfaces: N/A
 */

public class PlaybackManager {

    private static final long TOKEN_DURATION_MS = 10 * 60 * 60 * 1000;  //10 min

    private final Context mApplicationContext;
    private final ValidationMediaApiServiceInterface mValidationMediaApiService;
    private final BingSpeechApiServiceInterface mBingSpeechApiService;
    private final BingAuthApiServiceInterface mBingAuthApiService;
    private final Handler mMainHandler;
    private final MyEventListener mEventListener;

    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayer mTrackInfoPlayer;

    private PlaylistManager mPlaylistManager;
    private String mToken;
    private long mTokenTimeStamp;

    public PlaybackManager(final Context applicationContext,
                           final ValidationMediaApiServiceInterface validationMediaApiService,
                           final BingSpeechApiServiceInterface bingSpeechApiService, final BingAuthApiServiceInterface bingAuthApiService) {

        mApplicationContext = applicationContext;
        mValidationMediaApiService = validationMediaApiService;
        mBingSpeechApiService = bingSpeechApiService;
        mBingAuthApiService = bingAuthApiService;
        mMainHandler = new Handler(Looper.getMainLooper());
        mEventListener = new MyEventListener();
    }

    public void play(final Playlist playlist) {
        if (mSimpleExoPlayer == null) {
            mSimpleExoPlayer = ExoPlayerFactory
                    .newSimpleInstance(mApplicationContext,
                            new DefaultTrackSelector(), new DefaultLoadControl());
        }

        if (mTrackInfoPlayer == null) {
            mTrackInfoPlayer = ExoPlayerFactory
                    .newSimpleInstance(mApplicationContext,
                            new DefaultTrackSelector(), new DefaultLoadControl());
        }

        mPlaylistManager = new PlaylistManager(playlist.getItems());

        if (!mPlaylistManager.hasNextTrack()) {
            return;
        }

        final PlaylistItem currentTrack = mPlaylistManager.getCurrentTrack();
        final String futureId = currentTrack
                .getSummaryMultimediaItem().getFutureId();

        loadMedia(currentTrack.getSummaryMultimediaItem().getTitle(), futureId);
    }

    private void loadMedia(final String trackTitle, final String futureId) {
        final Capture<String> infoFilePath = new Capture<>();

        final String gender = PreferenceManager
                .getDefaultSharedPreferences(mApplicationContext)
                .getString("example_list", null);
        final boolean useMasculine = gender == null || "0".equals(gender);

        Task.callInBackground(new Callable<ValidationMedia>() {
            @Override
            public ValidationMedia call() throws Exception {
                final String ssmlVoiceSpec = useMasculine ?
                        "xml:lang='fr-FR'><voice xml:lang='fr-FR' xml:gender='Male' name='Microsoft Server Speech Text to Speech Voice (fr-FR, Paul, Apollo)'>%s</voice></speak>"
                        : "xml:lang='fr-FR'><voice xml:lang='fr-FR' xml:gender='Female' name='Microsoft Server Speech Text to Speech Voice (fr-FR, HortenseRUS)'>%s</voice></speak>";
                final String textToConvert = String.format("<speak version='1.0' " +
                                ssmlVoiceSpec
                        , trackTitle);
                if (mToken == null || TextUtils.isEmpty(mToken) || (SystemClock
                        .uptimeMillis() - mTokenTimeStamp) >= TOKEN_DURATION_MS) {
                    final Call<ResponseBody> jwtCall = mBingAuthApiService.getToken();
                    final Response<ResponseBody> jwtResponse = jwtCall.execute();
                    mToken = "bearer " + jwtResponse.body().string();
                    mTokenTimeStamp = SystemClock.uptimeMillis();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), textToConvert);
                final Call<ResponseBody> infoAudioCall = mBingSpeechApiService
                        .getAudio(mToken, requestBody);
                final Response<ResponseBody> infoAudio = infoAudioCall.execute();
                final ResponseBody body = infoAudio.body();
                File file = new File(mApplicationContext.getCacheDir(), futureId);
                if (file.exists() && file.delete()) {
                }
                final FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(body.bytes());
                fileOutputStream.flush();
                infoFilePath.set(file.getAbsolutePath());
                final Call<ValidationMedia> mediaUrlCall = mValidationMediaApiService.getMediaUrl(futureId);
                final Response<ValidationMedia> mediaUrl = mediaUrlCall.execute();
                return mediaUrl.body();
            }
        }).continueWith(new Continuation<ValidationMedia, Void>() {
            @Override
            public Void then(final Task<ValidationMedia> task) throws Exception {
                if (task.isFaulted()) {
                    throw new RuntimeException(task.getError().getMessage());
                }
                MediaSource mediaSource = buildMediaSource(Uri.parse(task.getResult().getUrl()), "",
                        PlaybackManager.this.buildDataSourceFactory());

                MediaSource infoMediaSource = new ExtractorMediaSource(Uri.parse(infoFilePath.get()),
                        PlaybackManager.this.buildDataSourceFactory(),
                        new DefaultExtractorsFactory(), mMainHandler, mEventListener);
                mSimpleExoPlayer.setPlayWhenReady(true);
                ConcatenatingMediaSource mediaSources
                        = new ConcatenatingMediaSource(infoMediaSource, mediaSource);

                mSimpleExoPlayer.prepare(mediaSources);
//                mTrackInfoPlayer.prepare(infoMediaSource);
//                mTrackInfoPlayer.setPlayWhenReady(true);
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    private MediaSource buildMediaSource(Uri uri, String overrideExtension, final DataSource.Factory dataSourceFactory) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, dataSourceFactory, mMainHandler, mEventListener);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource(uri, dataSourceFactory, new DefaultExtractorsFactory(),
                        mMainHandler, mEventListener);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private DataSource.Factory buildDataSourceFactory() {
        return new DefaultDataSourceFactory(mApplicationContext, null,
                new DefaultHttpDataSourceFactory(Util.getUserAgent(mApplicationContext,
                        mApplicationContext.getPackageName()), null));
    }

    private DataSource.Factory buildAuthenticatedDataSourceFactory(
            final String jwtToken
    ) {
        final DefaultHttpDataSourceFactory baseDataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(mApplicationContext,
                mApplicationContext.getPackageName()), null);
        final HttpDataSource.RequestProperties defaultRequestProperties = baseDataSourceFactory.getDefaultRequestProperties();
        defaultRequestProperties.set("Authorization", "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzY29wZSI6Imh0dHBzOi8vc3BlZWNoLnBsYXRmb3JtLmJpbmcuY29tIiwic3Vic2NyaXB0aW9uLWlkIjoiMDBhNzk1OWU2MTZjNGEwNmE0MzFhYjdlZjU4MzIwMWMiLCJwcm9kdWN0LWlkIjoiQmluZy5TcGVlY2guRjAiLCJjb2duaXRpdmUtc2VydmljZXMtZW5kcG9pbnQiOiJodHRwczovL2FwaS5jb2duaXRpdmUubWljcm9zb2Z0LmNvbS9pbnRlcm5hbC92MS4wLyIsImF6dXJlLXJlc291cmNlLWlkIjoiL3N1YnNjcmlwdGlvbnMvZjNkMmUxYzQtODFjZC00NDJhLTgxNDAtMDdhNjEwNGZkMGQyL3Jlc291cmNlR3JvdXBzL0hhY2thdGhvbi9wcm92aWRlcnMvTWljcm9zb2Z0LkNvZ25pdGl2ZVNlcnZpY2VzL2FjY291bnRzL2hhY2thdGhvbjIwMTciLCJpc3MiOiJ1cm46bXMuY29nbml0aXZlc2VydmljZXMiLCJhdWQiOiJ1cm46bXMuc3BlZWNoIiwiZXhwIjoxNDkwNDg3NTQ3fQ.9j91Xrkcn3OnZpTpurXgtl8GFlDsGPw27M6dtzjgovc");
        defaultRequestProperties.set("Content-Type", "application/ssml+xml");
        defaultRequestProperties.set("X-Microsoft-OutputFormat", "audio-16khz-32kbitrate-mono-mp3");
        defaultRequestProperties.set("X-Search-AppId", "4014878273d74c0085845e2aa59e3c27");
        defaultRequestProperties.set("X-Search-ClientId", "3629e65096bf4593a390186a7e95e879");
        return new DefaultDataSourceFactory(mApplicationContext, null,
                baseDataSourceFactory);
    }

    public void resume() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
    }

    public void pause() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(false);
        }
    }

    @Nullable
    public PlaylistItem getCurrentTrack() {
        if (mPlaylistManager == null) {
            return null;
        }
        return mPlaylistManager.getCurrentTrack();
    }

    public void playNext() {
        if (mPlaylistManager == null || !mPlaylistManager.hasNextTrack()) {
            return;
        }
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(false);
        }
        final PlaylistItem nextTrack = mPlaylistManager.getNextTrack();
        final SummaryMultimediaItem summaryMultimediaItem = nextTrack.getSummaryMultimediaItem();
        loadMedia(summaryMultimediaItem.getTitle(), summaryMultimediaItem.getFutureId());
    }

    public void playPrevious() {
        if (mPlaylistManager == null || !mPlaylistManager.hasPreviousTrack()) {
            return;
        }
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(false);
        }
        final PlaylistItem nextTrack = mPlaylistManager.getPreviousTrack();
        final SummaryMultimediaItem summaryMultimediaItem = nextTrack.getSummaryMultimediaItem();
        loadMedia(summaryMultimediaItem.getTitle(), summaryMultimediaItem.getFutureId());
    }

    private class MyEventListener implements ExtractorMediaSource.EventListener,
            AdaptiveMediaSourceEventListener {

        @Override
        public void onLoadStarted(final DataSpec dataSpec, final int dataType, final int trackType, final Format trackFormat, final int trackSelectionReason, final Object trackSelectionData, final long mediaStartTimeMs, final long mediaEndTimeMs, final long elapsedRealtimeMs) {

        }

        @Override
        public void onLoadCompleted(final DataSpec dataSpec, final int dataType, final int trackType, final Format trackFormat, final int trackSelectionReason, final Object trackSelectionData, final long mediaStartTimeMs, final long mediaEndTimeMs, final long elapsedRealtimeMs, final long loadDurationMs, final long bytesLoaded) {

        }

        @Override
        public void onLoadCanceled(final DataSpec dataSpec, final int dataType, final int trackType, final Format trackFormat, final int trackSelectionReason, final Object trackSelectionData, final long mediaStartTimeMs, final long mediaEndTimeMs, final long elapsedRealtimeMs, final long loadDurationMs, final long bytesLoaded) {

        }

        @Override
        public void onLoadError(final DataSpec dataSpec, final int dataType, final int trackType, final Format trackFormat, final int trackSelectionReason, final Object trackSelectionData, final long mediaStartTimeMs, final long mediaEndTimeMs, final long elapsedRealtimeMs, final long loadDurationMs, final long bytesLoaded, final IOException error, final boolean wasCanceled) {

        }

        @Override
        public void onUpstreamDiscarded(final int trackType, final long mediaStartTimeMs, final long mediaEndTimeMs) {

        }

        @Override
        public void onDownstreamFormatChanged(final int trackType, final Format trackFormat, final int trackSelectionReason, final Object trackSelectionData, final long mediaTimeMs) {

        }

        @Override
        public void onLoadError(final IOException error) {

        }
    }

}
