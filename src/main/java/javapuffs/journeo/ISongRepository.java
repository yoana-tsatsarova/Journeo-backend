package javapuffs.journeo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ISongRepository extends JpaRepository<Song, String> {

    public Song getSongById(String songId);
}
