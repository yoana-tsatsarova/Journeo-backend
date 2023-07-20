package javapuffs.journeo;

import jakarta.persistence.*;

@Entity
@Table(name="songs")
public class Song {

    @Id
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Song() {}

    public Song(String id, Playlist playlist) {
        this.id = id;
        this.playlist = playlist;
    }


}
