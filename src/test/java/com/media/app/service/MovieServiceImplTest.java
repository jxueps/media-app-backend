package com.media.app.service;

import com.media.app.dao.MovieRepository;
import com.media.app.dto.MovieRequest;
import com.media.app.entity.Movie;
import com.media.app.entity.Priority;
import com.media.app.entity.Role;
import com.media.app.entity.User;
import com.media.app.entity.UserMovie;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie;
    private User user;
    private MovieRequest movieRequest;
    private UserMovie userMovie;

    @BeforeEach
    public void setupTestData() {
        movie = Movie.builder()
                .imdbId("testImdbId")
                .title("testTitle")
                .build();

        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        movieRequest = MovieRequest.builder()
                .imdbId("testImdbId")
                .title("testTitle")
                .build();

        userMovie = UserMovie.builder()
                .user(user)
                .movie(movie)
                .watched(null)
                .priority(Priority.LOW)
                .build();
    }

    @Test
    public void testSaveMovie() {
        when(movieService.existsByImdbId("testImdbId")).thenReturn(false);
        when(movieService.save(movie)).thenReturn(movie);

        Movie savedMovie = movieService.save(movie);

        assertNotNull(savedMovie);
        assertEquals("testImdbId", savedMovie.getImdbId());
    }

    @Test
    public void testSaveMovieNonUniqueImdbId() {
        when(movieService.existsByImdbId("testImdbId")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> movieService.save(movie));
    }

    @Test
    public void testMakeMovie_Success() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieService.makeMovie(movieRequest);

        assertNotNull(result);
        assertEquals(movie.getImdbId(), result.getImdbId());
        assertEquals(movie.getTitle(), result.getTitle());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    public void testMakeMovie_InvalidRequest() {
        MovieRequest movieRequest = MovieRequest.builder()
                .imdbId(null)
                .title("testTitle")
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<MovieRequest>> violations = validator.validate(movieRequest);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });

        verify(movieRepository, never()).save(any());
    }
}
