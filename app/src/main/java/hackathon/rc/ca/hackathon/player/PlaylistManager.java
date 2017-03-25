package hackathon.rc.ca.hackathon.player;

import java.util.*;
import hackathon.rc.ca.hackathon.dtos.PlaylistItem;

/**
 * Created by Boris on 2017-03-25.
 */

public class PlaylistManager {
    private List<PlaylistItem> myPlaylist;
    private int currentIndex;
    private int size;

    /*
    Le constructeur se crée avec une liste de PlaylistItem
    Il y a une copie de la liste passée en argument.
    Mise à 0.
     */
    public PlaylistManager(List<PlaylistItem> playlist){
        myPlaylist = new ArrayList<PlaylistItem>();
        for(PlaylistItem item : playlist){
            myPlaylist.add(item);
        }
        this.currentIndex = 0;
        this.size = this.myPlaylist.size();
    }

    public PlaylistItem getCurrentTrack(){
        return this.myPlaylist.get(this.currentIndex);
    }

    /**
     *
     * @return le prochain PlaylistItem
     */
    public PlaylistItem getNextTrack(){
        if(this.hasNextTrack()) {
            this.currentIndex = this.currentIndex + 1;
            return this.myPlaylist.get(this.currentIndex);
        }
        else{
            return null;
        }
    }

    /**
     *
     * @return l'item précédent
     */
    public PlaylistItem getPreviousTrack(){
        if(this.hasPreviousTrack()){
            this.currentIndex = this.currentIndex - 1;
            return this.myPlaylist.get(this.currentIndex);
        }
        else{
            return null;
        }

    }

    /*
    Ne change pas l'état du Playlist
     */
    public boolean hasNextTrack(){
        return (this.currentIndex + 1 ) <=  (this.size);
    }

    /*
    Ne change pas l'état du Playlist
     */
    public boolean hasPreviousTrack(){
        return (this.currentIndex - 1) >= 0;
    }

}
