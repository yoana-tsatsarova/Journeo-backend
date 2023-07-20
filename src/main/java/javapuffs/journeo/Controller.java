package javapuffs.journeo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    private final ObjectMapper mapper = new ObjectMapper();
    private final CountryService countryService;
    private final PlaylistService playlistService;
    private final SongService songService;

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${OPEN_AI_MODEL}")
    private String model;

    @Value("${OPEN_AI_URL}")
    private String apiUrl;






    public Controller(CountryService countryService, PlaylistService playlistService, SongService songService) {
        this.countryService = countryService;
        this.playlistService = playlistService;
        this.songService = songService;
    }

    @GetMapping("songs/{country}")
    public List<String> getSongs(@PathVariable String country) {
        try {
            String accessToken = countryService.generateAccessToken();

            HttpEntity<String> entity = countryService.generateEntity(accessToken);

            String playlistId = countryService.fetchPlaylistId(country, entity);
            List<String> trackIds = countryService.getTrackIdsForSpecificPlaylist(playlistId, entity);
            return trackIds;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("coordinates/{country}")
    public JsonNode getCoordinates(@PathVariable String country) {

        ChatRequestDTO request = new ChatRequestDTO(model, country);
        String response = restTemplate.postForObject(apiUrl, request, String.class);
        JsonNode node = null;
        try {
            node = mapper.readTree(response);
            String content =  node.get("choices").get(0).get("message").get("content").asText();
            return mapper.readTree(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("addsong")
    public SongDTO addSongToPlaylist(@RequestBody SongDTO songDTO) {
        Playlist playlist = playlistService.getPlaylistById(1);
        System.out.println("Playlist" + playlist);
        if (playlist == null) {
            playlist = new Playlist(new ArrayList<>());
            playlistService.savePlaylist(playlist);
        }
        System.out.println("Created" + playlist.getId());

        Song song = songService.findSongById(songDTO.songId());
        if(song == null) {
            song = new Song(songDTO.songId(), playlist);
            songService.saveSong(song);
        }

        playlist.addSong(song);
        playlistService.savePlaylist(playlist);
        return songDTO;
    }


}
