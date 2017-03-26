package hackathon.rc.ca.hackathon.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamady Cissé on 2017-03-26.
 * Responsibilities:        ....
 * States:                   N/A
 * Dependencies:
 * Final dependencies:    N/A
 * Differed dependencies: N/A
 * Inner classes/interfaces: N/A
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConceptualImage {

    private List<ConcreteImage> concreteImages = new ArrayList<>();

    public List<ConcreteImage> getConcreteImages() {
        return concreteImages;
    }

    public void setConcreteImages(final List<ConcreteImage> concreteImages) {
        this.concreteImages = concreteImages;
    }

    private String alt;

    public String getAlt() {
        return alt;
    }

    public void setAlt(final String alt) {
        this.alt = alt;
    }

    private String legend;

    public String getLegend() {
        return legend;
    }

    public void setLegend(final String legend) {
        this.legend = legend;
    }

    private String imageCredits;

    public String getImageCredits() {
        return imageCredits;
    }

    public void setImageCredits(final String imageCredits) {
        this.imageCredits = imageCredits;
    }
}
