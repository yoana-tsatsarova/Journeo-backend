package javapuffs.journeo.repository;

import javapuffs.journeo.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISongRepository extends JpaRepository<Song, String> {

    Song getSongById(String id);

}
