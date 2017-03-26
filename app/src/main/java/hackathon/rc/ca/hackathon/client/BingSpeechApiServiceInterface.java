package hackathon.rc.ca.hackathon.client;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Hamady Cissé on 2017-03-25.
 * Responsibilities:  The service for the api calls to the Bing Speech
 * States:                   N/A
 * Dependencies:
 * Final dependencies:    N/A
 * Differed dependencies: N/A
 * Inner classes/interfaces: N/A
 */

public interface BingSpeechApiServiceInterface {


    @Headers({
            "Content-Type: application/ssml+xml",
            "X-Microsoft-OutputFormat: audio-16khz-32kbitrate-mono-mp3",
            "X-Search-AppId: 4014878273d74c0085845e2aa59e3c27",
            "X-Search-ClientId: 3629e65096bf4593a390186a7e95e879",
    })
    @POST("/synthesize")
    Call<ResponseBody> getAudio(@Header("Authorization") String token, @Body RequestBody ssml);
}
