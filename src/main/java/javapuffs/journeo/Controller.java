package javapuffs.journeo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private final CountryService service;

    public Controller(CountryService service) {
        this.service = service;
    }

    @GetMapping("{country}")
    public List<String> getmap(@PathVariable String country) {
        try {
            String accessToken = service.generateAccessToken();

            HttpEntity<String> entity = service.generateEntity(accessToken);

            String playlistId = service.fetchPlaylistId(country, entity);

            return service.getTrackIdsForSpecificPlaylist(playlistId, entity);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
