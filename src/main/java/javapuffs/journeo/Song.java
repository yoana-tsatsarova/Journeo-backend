package javapuffs.journeo;

import jakarta.persistence.*;

@Entity
@Table(name="songs")
public class Song {

    @Id
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Song() {}

    public Song(String id) {
        this.id = id;
    }


}
