package javapuffs.journeo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    private final ObjectMapper mapper = new ObjectMapper();
    private final CountryService countryService;
    private final SongService songService;

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${OPEN_AI_MODEL}")
    private String model;

    @Value("${OPEN_AI_URL}")
    private String apiUrl;


    public Controller(CountryService countryService, SongService songService) {
        this.countryService = countryService;
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
    public void addSongToPlaylist(@RequestBody SongDTO songDTO) {
        Song song = songService.findSongBySongId(songDTO.songId());
        if(song == null) {
            song = new Song(songDTO.songId());
            songService.saveSong(song);
        }
    }

    @GetMapping("favorites")
    public List<Song> getFavoriteSongs() {
        return songService.getAllSongs();
    }


    @DeleteMapping("favorites/{songId}")
    public void deleteSongFromFavorites(@PathVariable String songId) {
        songService.deleteSongBySongId(songId);
    }

    @GetMapping("userid")
    public String getUserId() {
        String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1/me";

        RestTemplate restTemplate = new RestTemplate();
        String accessToken = countryService.generateAccessToken();


        // Set the Authorization header with the access token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        RequestEntity<Void> requestEntity;
        try {
            requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(SPOTIFY_API_BASE_URL));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        // Make the request to the Spotify API
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        // Parse the JSON response and get the user ID
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            System.out.println(responseBody);
            // Parse the responseBody to get the user ID
            // The user ID is available in the "id" field of the response
            // Example: String userId = extractUserIdFromResponseBody(responseBody);
            // Return the user ID
        }

        return null; // Return null if something went wrong
    }
}
