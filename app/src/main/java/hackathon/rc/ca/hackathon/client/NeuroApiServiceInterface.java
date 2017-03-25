package hackathon.rc.ca.hackathon.client;

import retrofit2.http.GET;
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

    @GET("/neuro/v1/playlists/{id}")
    Call<List<>> listRepos(@Path("id") String id);
}
