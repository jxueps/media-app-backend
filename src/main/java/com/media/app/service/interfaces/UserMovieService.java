package com.media.app.service.interfaces;

import com.media.app.dto.MovieRequest;
import com.media.app.entity.UserMovie;

import java.util.List;
import java.util.Optional;

public interface UserMovieService {
    UserMovie save(UserMovie userMovie);

    Optional<UserMovie> findById(Integer id);

    void deleteById(Integer id);

    List<UserMovie> findToWatchMoviesByUser(Long userId);

    List<UserMovie> findWatchedMoviesByUser(Long userId);

    Optional<UserMovie> findByImdbIdAndUserId(String imdbId, Long userId);

    UserMovie saveWatchStatus(MovieRequest movieRequest, Long userId, boolean watched);

    UserMovie getUserMovie(MovieRequest movieRequest, Long userId);

}
