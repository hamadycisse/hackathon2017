package hackathon.rc.ca.hackathon.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Portable on 2017-03-25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaLink {

    private String href;

    public String getHref(){
        return href;
    }
    public void setHref(){
        this.href = href;
    }



}
