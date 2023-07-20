package javapuffs.journeo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    @Autowired
    SongRepository songRepository;
    public Song findSongById(String songId) {
        return songRepository.findSongById(songId);
    }

    public void saveSong(Song song) {
        songRepository.saveSong(song);
    }
}
