package javapuffs.journeo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongRepository {

    ISongRepository iSongRepository;

    public SongRepository(ISongRepository iSongRepository) {
        this.iSongRepository = iSongRepository;
    }

    public Song findSongBySongId(String songId) {
        return iSongRepository.getSongById(songId);
    }

    public void saveSong(Song song) {
        iSongRepository.save(song);
    }

    public List<Song> getAllSongs() {
        return iSongRepository.findAll();
    }

    public void deleteSongBySongId(String songId) {
        iSongRepository.deleteById(songId);
    }
}
