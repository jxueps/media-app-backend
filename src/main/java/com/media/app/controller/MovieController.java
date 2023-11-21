package com.media.app.controller;

import com.media.app.service.interfaces.MovieService;
import com.media.app.service.interfaces.UserMovieService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/searchByTitle/{string}")
    public ResponseEntity<Map<String, Object>> searchMovies(@PathVariable String string) {
        return movieService.searchApi(string);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/searchById/{imdbId}")
    public ResponseEntity<Map<String, Object>> getMovieById(@PathVariable String imdbId) {
        return movieService.movieApi(imdbId);
    }
}
