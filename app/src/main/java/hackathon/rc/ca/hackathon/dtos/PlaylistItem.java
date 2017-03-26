package hackathon.rc.ca.hackathon.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Portable on 2017-03-25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistItem {


    private SummaryMultimediaItem summaryMultimediaItem = new SummaryMultimediaItem();


    public SummaryMultimediaItem getSummaryMultimediaItem() {
        return summaryMultimediaItem;
    }
    public void setSummaryMultimediaItem(SummaryMultimediaItem SummaryMultimediaItem) {
        this.summaryMultimediaItem = SummaryMultimediaItem;
    }
}
