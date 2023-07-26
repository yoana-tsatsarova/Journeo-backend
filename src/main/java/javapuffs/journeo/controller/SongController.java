package javapuffs.journeo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javapuffs.journeo.Song;
import javapuffs.journeo.dtos.ChatRequestDTO;
import javapuffs.journeo.dtos.SongDTO;
import javapuffs.journeo.service.CountryService;
import javapuffs.journeo.service.SongService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/")
public class SongController {

    private final ObjectMapper mapper = new ObjectMapper();
    private final CountryService countryService;
    private final SongService songService;
    private final RestTemplate restTemplate;

    @Value("${OPEN_AI_MODEL}")
    private String model;

    @Value("${OPEN_AI_URL}")
    private String apiUrl;


    public SongController(CountryService countryService, SongService songService,  @Qualifier("openaiRestTemplate") RestTemplate restTemplate) {
        this.countryService = countryService;
        this.songService = songService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/songs/{country}")
    public ResponseEntity<List<String>> getSongs(@PathVariable String country) {
        try {
            String accessToken = countryService.generateAccessToken();
            HttpEntity<String> entity = countryService.generateEntity(accessToken);
            String playlistId = countryService.fetchPlaylistId(country, entity);
            List<String> trackIds =  countryService.getTrackIdsForSpecificPlaylist(playlistId, entity);
            return ResponseEntity.ok(trackIds);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("coordinates/{country}")
    public ResponseEntity<JsonNode> getCoordinates(@PathVariable String country) {
        try {
            ChatRequestDTO request = new ChatRequestDTO(model, country);
            String response = restTemplate.postForObject(apiUrl, request, String.class);
            JsonNode node = mapper.readTree(response);
            String content =  node.get("choices").get(0).get("message").get("content").asText();
            JsonNode coordinates = mapper.readTree(content);
            return ResponseEntity.ok(coordinates);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("songs/add")
    public ResponseEntity<Void> addSongToPlaylist(@RequestBody SongDTO songDTO) {
        Song song = songService.findSongBySongId(songDTO.songId());
        if(song == null) {
            song = new Song(songDTO.songId());
            songService.saveSong(song);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("favorites")
    public ResponseEntity<List<Song>> getFavoriteSongs() {
        List<Song> favoriteSongs = songService.getAllSongs();
        return ResponseEntity.ok(favoriteSongs);
    }


    @DeleteMapping("favorites/{songId}")
    public ResponseEntity<Void> deleteSongFromFavorites(@PathVariable String songId) {
        Song song = songService.findSongBySongId(songId);
        if (song == null) {
            return ResponseEntity.noContent().build();
        }
        songService.deleteSongBySongId(songId);
        return ResponseEntity.noContent().build();
    }

}
