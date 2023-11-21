package com.media.app.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OmdbAPITest {

    @Autowired
    private OmdbAPI omdbAPI;

    @MockBean
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, Object> createMockApiResponse() {
        String json = "{\"Title\":\"Captain Marvel\",\"Year\":\"2019\",\"Rated\":\"PG-13\",\"Released\":\"08 Mar 2019\",\"Runtime\":\"123 min\",\"Genre\":\"Action, Adventure, Sci-Fi\",\"Director\":\"Anna Boden, Ryan Fleck\",\"Writer\":\"Anna Boden, Ryan Fleck, Geneva Robertson-Dworet\",\"Actors\":\"Brie Larson, Samuel L. Jackson, Ben Mendelsohn\",\"Plot\":\"Carol Danvers becomes one of the universe's most powerful heroes when Earth is caught in the middle of a galactic war between two alien races.\",\"Language\":\"English\",\"Country\":\"United States, Australia\",\"Awards\":\"10 wins & 57 nominations\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"6.8/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"79%\"},{\"Source\":\"Metacritic\",\"Value\":\"64/100\"}],\"Metascore\":\"64\",\"imdbRating\":\"6.8\",\"imdbVotes\":\"594,177\",\"imdbID\":\"tt4154664\",\"Type\":\"movie\",\"DVD\":\"28 May 2019\",\"BoxOffice\":\"$426,829,839\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}";

        try {
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

    private ResponseEntity<Map<String, Object>> createMockApiResponseWithError() {
        Map<String, Object> errorResponse = Collections.singletonMap("error", "Simulated error from external API");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @Test
    public void testSearchApi_Success() {
        Map<String, Object> mockResponse = createMockApiResponse();
        ResponseEntity<Map<String, Object>> successResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(successResponseEntity);

        ResponseEntity<Map<String, Object>> result = omdbAPI.searchApi("Marvel");

        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }

    @Test
    public void testSearchApi_ClientError() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class)))
                .thenReturn(createMockApiResponseWithError());

        ResponseEntity<Map<String, Object>> responseEntity = omdbAPI.searchApi("InvalidMovie");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().containsKey("error"));
    }

    @Test
    void testMovieApi_Success() {
        ResponseEntity<Map<String, Object>> successResponse = new ResponseEntity<>(createMockApiResponse(), HttpStatus.OK);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(successResponse);

        ResponseEntity<Map<String, Object>> result = omdbAPI.movieApi("tt4154664");

        assertEquals(successResponse, result);
    }
}
