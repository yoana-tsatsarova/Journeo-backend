package javapuffs.journeo.service;

import javapuffs.journeo.Song;
import javapuffs.journeo.repository.SongRepository;
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
