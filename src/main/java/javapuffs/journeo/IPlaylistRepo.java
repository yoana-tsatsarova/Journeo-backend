package javapuffs.journeo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlaylistRepo extends JpaRepository<Playlist, Integer> {

    public Playlist getPlaylistsById(Integer id);
}
