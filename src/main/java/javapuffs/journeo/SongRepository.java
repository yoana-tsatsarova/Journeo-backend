package javapuffs.journeo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SongRepository {

    @Autowired
    ISongRepository iSongRepository;
    public Song findSongById(String songId) {
        return iSongRepository.getSongById(songId);
    }

    public void saveSong(Song song) {
        iSongRepository.save(song);
    }
}
