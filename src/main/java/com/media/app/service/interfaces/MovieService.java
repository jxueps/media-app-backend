package com.media.app.service.interfaces;

import com.media.app.dto.MovieRequest;
import com.media.app.entity.Movie;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface MovieService {

    Movie save(Movie movie);

    Boolean existsByImdbId(String imdbId);

    ResponseEntity<Map<String, Object>> searchApi(String search);

    ResponseEntity<Map<String, Object>> movieApi(String imdbId);

    Optional<Movie> findByImdbId(String imdbId);

    Movie makeMovie(MovieRequest movieRequest);
}
