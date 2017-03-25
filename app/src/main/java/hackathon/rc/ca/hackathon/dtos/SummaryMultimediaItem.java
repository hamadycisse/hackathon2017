package hackathon.rc.ca.hackathon.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryMultimediaItem {

    private String item;

    public String getItem() {

        return item;
    }

    public void setItem(String item) {

        this.item = item;
    }

    private String futureId;

    public String getFutureId() {

        return futureId;
    }
    public void setFutureId(String futureId) {

        this.futureId = futureId;
    }

    private String futureDuration;

    public String getFutureDuration() {

        return futureDuration;
    }
    public void setFutureDuration() {

        this.futureId = futureId;
    }

    private String summary;

    public String getSummary () {

        return summary;
    }
    public void setSummary(String summary){

        this.summary = summary;
    }

    private ConcreteImage ConcreteImage = new ConcreteImage();


    public ConcreteImage getConcreteImage() {
        return ConcreteImage;
    }

    public void setConcreteImage(ConcreteImage ConcreteImage) {
        this.ConcreteImage = ConcreteImage;
    }
}
