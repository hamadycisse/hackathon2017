package hackathon.rc.ca.hackathon.dtos;

/**
 * Created by Portable on 2017-03-25.
 */

public class ConcreteImage {

    private int width;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    private String dimensionRatio;

    public String getDimensionRatio() {
        return dimensionRatio;
    }

    public void setDimensionRatio(String dimensionRatio){
        this.dimensionRatio = dimensionRatio;
    }

    private String mediaLink;

    public String getMediaLink() {
        return mediaLink;
    }
    public void setMediaLink() {
        this.mediaLink = mediaLink;
    }
    
    private MediaLink mediaLink = new MediaLink();

    public MediaLink getMediaLink(){
        return mediaLink;
    }
    public void setMediaLink(MediaLink mediaLink){
        this.mediaLink = mediaLink;
    }
}
