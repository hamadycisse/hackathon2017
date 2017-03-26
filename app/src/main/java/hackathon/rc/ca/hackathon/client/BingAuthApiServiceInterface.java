package hackathon.rc.ca.hackathon.client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Hamady Cissé on 2017-03-26.
 * Responsibilities:        ....
 * States:                   N/A
 * Dependencies:
 * Final dependencies:    N/A
 * Differed dependencies: N/A
 * Inner classes/interfaces: N/A
 */

public interface BingAuthApiServiceInterface {

    @Headers({
            "Ocp-Apim-Subscription-Key: e95893c6e82d4515b715837c09c6d47d"
    })
    @POST("/sts/v1.0/issueToken")
    Call<ResponseBody> getToken();
}
