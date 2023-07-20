package javapuffs.journeo;

import org.springframework.stereotype.Repository;

@Repository
public class PlaylistRepo {

    IPlaylistRepo repo;

    public PlaylistRepo(IPlaylistRepo repo) {
        this.repo = repo;
    }

    public Playlist getPlaylistById(Integer id) {
        return repo.getPlaylistsById(id);
    }

    public void savePlaylist(Playlist playlist) {
        repo.save(playlist);
    }
}
