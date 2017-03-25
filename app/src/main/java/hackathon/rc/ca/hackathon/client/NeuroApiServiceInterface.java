package hackathon.rc.ca.hackathon.client;

import java.util.List;

import hackathon.rc.ca.hackathon.dtos.Playlist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Hamady Cissé on 2017-03-25.
 * Responsibilities:    Used to make API calls to Neuro
 * States:                   N/A
 * Dependencies:
 * Final dependencies:    N/A
 * Differed dependencies: N/A
 * Inner classes/interfaces: N/A
 */

public interface NeuroApiServiceInterface {

    @Headers({
            "Authorization: Client-Key 31e51cda-4ab0-4234-83c2-25d503c69487"
    })
    @GET("/neuro/v1/playlists/{id}")
    Call<Playlist> getPlaylist(@Path("id") String id);
}
