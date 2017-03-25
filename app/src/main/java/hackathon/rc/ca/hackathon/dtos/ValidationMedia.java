package hackathon.rc.ca.hackathon.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Portable on 2017-03-25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationMedia {

    private String url;

    public String getUrl(){
        return url;
    }
    public void setUrl(){
        this.url = url;
    }



}
