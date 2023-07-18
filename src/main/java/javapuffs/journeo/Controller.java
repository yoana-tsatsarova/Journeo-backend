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

@RestController
@RequestMapping("/")
public class Controller {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("{country}")
    public JsonNode getmap(@PathVariable String country) {
        try {// Replace with your desired country code
            String accessToken = "BQBT1hvgyOvzR9AO5qFSTtKtYchvf8oIv7_wsLTEOTZ5XXRSn6MATrUlSInaax-5G5uU3PkYWga0ChQYl1lluoXW1PYWd1FlHLOuDKQr52X7Q5GfRXI"; // Replace with your Spotify access token

            String url = "https://api.spotify.com/v1/search?q=Top+50+" + country + "&type=playlist&limit=1";
            String authorizationHeader = "Bearer " + accessToken;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();
            JsonNode node = mapper.readTree(responseBody);
            String id  = node.get("playlists").get("items").get(0).get("id").asText();

            String urlId = "https://api.spotify.com/v1/playlists/" + id + "/tracks?offset=0&limit=1";
            System.out.println(urlId);

                ResponseEntity<String> responseSongs = restTemplate.exchange(urlId, HttpMethod.GET, entity, String.class);
                JsonNode songsNode = mapper.readTree(responseSongs.getBody());
                return songsNode.get("items").get(0).get("track").get("id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
