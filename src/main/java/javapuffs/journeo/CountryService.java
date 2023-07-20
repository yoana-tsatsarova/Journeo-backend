package javapuffs.journeo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class CountryService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();


    @Value("${CLIENT_SECRET}") // Use the @Value annotation to inject the value of the environment variable
    private String clientSecret;

    @Value("${CLIENT_ID}") // Use the @Value annotation to inject the value of the environment variable
    private String client_id;

    public String fetchPlaylistId(String country, HttpEntity<String> entity) {
        String url = "https://api.spotify.com/v1/search?q=Top+50+" + country + "&type=playlist&limit=10";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();
        try {
            JsonNode node = mapper.readTree(responseBody);
            for (JsonNode playlistNode: node.get("playlists").get("items")) {
                if (playlistNode.get("name").asText().equals("Top 50 - " + country)) {
                    return playlistNode.get("id").asText();
                }
            }
            return node.get("playlists").get("items").get(0).get("id").asText();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateAccessToken() {
        String url = "https://accounts.spotify.com/api/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "client_credentials");
        parameters.add("client_id", client_id);
        parameters.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        try {
            JsonNode accessTokenNode = mapper.readTree(responseEntity.getBody()).get("access_token");
            return accessTokenNode.asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpEntity<String> generateEntity(String accessToken) {
        String authorizationHeader = "Bearer " + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        return new HttpEntity<>(headers);
    }

    public List<String> getTrackIdsForSpecificPlaylist(String playlistId, HttpEntity<String> entity) {

        String urlId = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks?offset=0&limit=10";
        ResponseEntity<String> responseSongs = restTemplate.exchange(urlId, HttpMethod.GET, entity, String.class);
        try {
            JsonNode songsNode = mapper.readTree(responseSongs.getBody());
            List<String> songIdList = new ArrayList<>();
            for (JsonNode songNode: songsNode.get("items")) {
                songIdList.add(songNode.get("track").get("id").asText());
            }
            return songIdList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
