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
}
