package com.media.app.service;

import com.media.app.api.OmdbAPI;
import com.media.app.dao.MovieRepository;
import com.media.app.dto.MovieRequest;
import com.media.app.entity.Movie;
import com.media.app.service.interfaces.MovieService;
import com.media.app.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private final OmdbAPI omdbAPI;
    private final MovieRepository movieRepository;

    @Override
    public Movie save(Movie movie) {
        if (movieRepository.existsByImdbId(movie.getImdbId())) {
            System.out.println(movie.getImdbId());
            throw new IllegalArgumentException("Non unique IMDB ID");
        }
        return movieRepository.save(movie);
    }

    public ResponseEntity<Map<String, Object>> searchApi(String search) {
        return omdbAPI.searchApi(search);
    }

    public ResponseEntity<Map<String, Object>> movieApi(String imdbId) {
        return omdbAPI.movieApi(imdbId);
    }

    @Override
    public Optional<Movie> findByImdbId(String imdbId) {
        return movieRepository.findByImdbId(imdbId);
    }

    @Override
    public Movie makeMovie(MovieRequest movieRequest) {
        Movie movie = Movie.builder()
                .imdbId(movieRequest.getImdbId())
                .title(movieRequest.getTitle())
                .description(movieRequest.getDescription())
                .year(movieRequest.getYear())
                .runtime(movieRequest.getRuntime())
                .rated(movieRequest.getRated())
                .poster(movieRequest.getPoster())
                .build();

        return this.save(movie);
    }

    @Override
    public Boolean existsByImdbId(String imdbId) {
        return movieRepository.existsByImdbId(imdbId);
    }
}
