package javapuffs.journeo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song findSongBySongId(String songId) {
        return songRepository.findSongBySongId(songId);
    }

    public void saveSong(Song song) {
        songRepository.saveSong(song);
    }

    public List<Song> getAllSongs() {
        return songRepository.getAllSongs();
    }

    public void deleteSongBySongId(String songId) {
        songRepository.deleteSongBySongId(songId);
    }


}
