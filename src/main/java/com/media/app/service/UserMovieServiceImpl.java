package com.media.app.service;

import com.media.app.dao.UserMovieRepository;
import com.media.app.dto.MovieRequest;
import com.media.app.entity.Movie;
import com.media.app.entity.User;
import com.media.app.entity.UserMovie;
import com.media.app.service.interfaces.MovieService;
import com.media.app.service.interfaces.UserMovieService;
import com.media.app.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserMovieServiceImpl implements UserMovieService {
    private final UserMovieRepository userMovieRepository;
    private final MovieService movieService;
    private final UserService userService;

    @Override
    public UserMovie save(UserMovie userMovie) {
        return userMovieRepository.save(userMovie);
    }

    @Override
    public Optional<UserMovie> findById(Integer id) {
        return userMovieRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        userMovieRepository.deleteById(id);
    }

    @Override
    public List<UserMovie> findToWatchMoviesByUser(Long userId) {
        return userMovieRepository.findToWatchMoviesByUser(userId);
    }

    @Override
    public List<UserMovie> findWatchedMoviesByUser(Long userId) {
        return userMovieRepository.findWatchedMoviesByUser(userId);
    }

    @Override
    public Optional<UserMovie> findByImdbIdAndUserId(String imdbId, Long userId) {
        return userMovieRepository.findByImdbIdAndUserId(imdbId, userId);
    }

    @Override
    public UserMovie saveWatchStatus(MovieRequest movieRequest, Long userId, boolean watched) {
        UserMovie userMovie = getUserMovie(movieRequest, userId);
        if (watched) {
            userMovie.setWatched(LocalDateTime.now());
        } else {
            userMovie.setWatched(null);
        }
        return this.save(userMovie);
    }

    @Override
    public UserMovie getUserMovie(MovieRequest movieRequest, Long userId) {
        Optional<UserMovie> userMovieDb = findByImdbIdAndUserId(movieRequest.getImdbId(), userId);
        if (userMovieDb.isEmpty()) {
            // makes movie if not present in db -- only possible if userMovie is empty
            Optional<User> userDb = userService.findById(userId);

            if (userDb.isEmpty()) {
                throw new IllegalArgumentException("Invalid user ID: " + userId);
            }

            Optional<Movie> movieDb = movieService.findByImdbId(movieRequest.getImdbId());
            Movie movie;

            if (movieDb.isEmpty()) {
                movie = movieService.makeMovie(movieRequest);
                movie = movieService.save(movie);
            } else {
                movie = movieDb.get();
            }

            // make userMovie
            UserMovie userMovie = UserMovie.builder()
                    .movie(movie)
                    .user(userDb.get())
                    .priority(movieRequest.getPriority())
                    .build();

            return this.save(userMovie);
        }
        return userMovieDb.get();
    }
}
