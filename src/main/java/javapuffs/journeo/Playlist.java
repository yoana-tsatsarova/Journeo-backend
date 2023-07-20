package javapuffs.journeo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    @Column(name = "song_ids")
    private List<Song> songs;

    public Playlist() {
    }

    public Playlist(List<Song> songs) {
        this.songs = songs;
        this.id = 1;
    }

    public int getId() {
        return id;
    }

    public List<Song> getSongIds() {
        return songs;
    }

    public void setSongIds(List<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        songs.add(song);
    }
}
