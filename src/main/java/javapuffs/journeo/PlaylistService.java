package javapuffs.journeo;

import org.springframework.stereotype.Service;

@Service
public class PlaylistService {
    PlaylistRepo repo;

    public PlaylistService(PlaylistRepo repo) {
        this.repo = repo;
    }


    public Playlist getPlaylistById(Integer id) {
        return repo.getPlaylistById(id);
    }

    public void savePlaylist(Playlist playlist) {
        repo.savePlaylist(playlist);
    }
}
