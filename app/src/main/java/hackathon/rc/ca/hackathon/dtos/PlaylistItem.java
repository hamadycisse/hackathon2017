package hackathon.rc.ca.hackathon.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Portable on 2017-03-25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistItem {

    private String title;

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    private String id;

    public String getId() {

        return id;
    }
    public void setId(String id) {

        this.id = id;
    }

    private SummaryMultimediaItem summaryMultimediaItem = new SummaryMultimediaItem();


    public SummaryMultimediaItem getSummaryMultimediaItem() {
        return summaryMultimediaItem;
    }
    public void setSummaryMultimediaItem(SummaryMultimediaItem SummaryMultimediaItem) {
        this.summaryMultimediaItem = SummaryMultimediaItem;
    }
}
