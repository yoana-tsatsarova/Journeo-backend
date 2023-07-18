package javapuffs.journeo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="country_songs")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    private String longitude;

    private String latitude;

    @ElementCollection
    @CollectionTable(name = "country_songs", joinColumns = @JoinColumn(name = "country_id"))
    @Column(name = "song_id")
    private List<String> songIds;

    public Country() {
    }

    public Country(Long id, String country, String longitude, String latitude, List<String> songIds) {
        this.id = id;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.songIds = songIds;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<String> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<String> songIds) {
        this.songIds = songIds;
    }
}
