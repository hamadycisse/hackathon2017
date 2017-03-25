package hackathon.rc.ca.hackathon.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Portable on 2017-03-25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlist {
    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String summary;

    public String getSummary () {
        return summary;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }

}
