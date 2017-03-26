package hackathon.rc.ca.hackathon.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryMultimediaItem {

    private String title;

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    private String futureId;

    public String getFutureId() {

        return futureId;
    }
    public void setFutureId(String futureId) {

        this.futureId = futureId;
    }

    private int futureDuration;

    public int getFutureDuration() {

        return futureDuration;
    }
    public void setFutureDuration(final int futureDuration) {

        this.futureDuration = futureDuration;
    }

    private String summary;

    public String getSummary () {

        return summary;
    }
    public void setSummary(String summary){

        this.summary = summary;
    }

    private ConceptualImage summaryImage = new ConceptualImage();


    public ConceptualImage getSummaryImage() {
        return summaryImage;
    }

    public void setSummaryImage(ConceptualImage conceptualImage) {
        this.summaryImage = conceptualImage;
    }
}
