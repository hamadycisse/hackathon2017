package hackathon.rc.ca.hackathon.player;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import hackathon.rc.ca.hackathon.client.ValidationMediaApiServiceInterface;
import hackathon.rc.ca.hackathon.dtos.Playlist;
import hackathon.rc.ca.hackathon.dtos.ValidationMedia;
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

    private final Context mApplicationContext;
    private final ValidationMediaApiServiceInterface mValidationMediaApiService;
    private final Handler mMainHandler;
    private final MyEventListener mEventListener;

    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayer mTrackInfoPlayer;

    private PlaylistManager mPlaylistManager;

    public PlaybackManager(final Context applicationContext,
                           final ValidationMediaApiServiceInterface validationMediaApiService) {

        mApplicationContext = applicationContext;
        mValidationMediaApiService = validationMediaApiService;
        mMainHandler = new Handler(Looper.getMainLooper());
        mEventListener = new MyEventListener();
    }

    public void play(final Playlist playlist) {
        if (mSimpleExoPlayer == null) {
            mSimpleExoPlayer = ExoPlayerFactory
                    .newSimpleInstance(mApplicationContext,
                            new DefaultTrackSelector(), new DefaultLoadControl());
        }

//        if (mTrackInfoPlayer == null) {
//            mTrackInfoPlayer = ExoPlayerFactory
//                    .newSimpleInstance(mApplicationContext,
//                            new DefaultTrackSelector(), new DefaultLoadControl());
//        }

        mPlaylistManager = new PlaylistManager(playlist.getItems());

        Task.callInBackground(new Callable<ValidationMedia>() {
            @Override
            public ValidationMedia call() throws Exception {
                final Call<ValidationMedia> mediaUrlCall = mValidationMediaApiService.getMediaUrl(playlist.getItems().get(0)
                        .getSummaryMultimediaItem().getFutureId());
                final Response<ValidationMedia> mediaUrl = mediaUrlCall.execute();
                return mediaUrl.body();
            }
        }).continueWith(new Continuation<ValidationMedia, Void>() {
            @Override
            public Void then(final Task<ValidationMedia> task) throws Exception {
                if (task.isFaulted()) {
                    throw new RuntimeException(task.getError().getMessage());
                }
                MediaSource mediaSource = buildMediaSource(Uri.parse(task.getResult().getUrl()), "");
                mSimpleExoPlayer.prepare(mediaSource);
                mSimpleExoPlayer.setPlayWhenReady(true);

//                MediaSource infoMediaSource = buildMediaSource(Uri.parse("https://archive" +
//                        ".org/download/testmp3testfile/mpthreetest.mp3"), "");
//                mTrackInfoPlayer.prepare(mediaSource);
//                mTrackInfoPlayer.setPlayWhenReady(true);
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, buildDataSourceFactory(), mMainHandler, mEventListener);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource(uri, buildDataSourceFactory(), new DefaultExtractorsFactory(),
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
