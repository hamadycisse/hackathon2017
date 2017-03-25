package hackathon.rc.ca.hackathon.client;

import hackathon.rc.ca.hackathon.dtos.ValidationMedia;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Hamady Cissé on 2017-03-25.
 * Responsibilities:   Used to make api to the validation media API
 * States:                   N/A
 * Dependencies:
 * Final dependencies:    N/A
 * Differed dependencies: N/A
 * Inner classes/interfaces: N/A
 */

public interface ValidationMediaApiServiceInterface {

    @Headers({
            "Authorization: Client-Key 31e51cda-4ab0-4234-83c2-25d503c69487"
    })
    @GET("/ValidationMedia/v1/Validation" +
            ".html?appCode=medianet&deviceType=android4&connectionType=wifi&output=json")
    Call<ValidationMedia> getMediaUrl(@Query("idMedia") final String idMedia);
}
