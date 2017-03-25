package hackathon.rc.ca.hackathon.dtos;

/**
 * Created by Portable on 2017-03-25.
 */

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

    private summaryMultimediaItem summaryMultimediaItem = new summaryMultimediaItem();


}
