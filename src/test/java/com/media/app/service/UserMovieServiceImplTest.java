package com.media.app.service;

import com.media.app.dao.UserMovieRepository;
import com.media.app.dto.MovieRequest;
import com.media.app.entity.Movie;
import com.media.app.entity.Priority;
import com.media.app.entity.Role;
import com.media.app.entity.User;
import com.media.app.entity.UserMovie;
import com.media.app.service.interfaces.MovieService;
import com.media.app.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMovieServiceImplTest {
    @Mock
    private UserMovieRepository userMovieRepository;
    @Mock
    private UserService userService;
    @Mock
    private MovieService movieService;
    @InjectMocks
    private UserMovieServiceImpl userMovieService;

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
    public void testSaveUserMovie() {
        when(userMovieRepository.save(any(UserMovie.class))).thenReturn(userMovie);

        UserMovie savedUserMovie = userMovieService.save(userMovie);

        assertNotNull(savedUserMovie);
        assertEquals("testImdbId", savedUserMovie.getMovie().getImdbId());

        verify(userMovieRepository, times(1)).save(userMovie);
    }

    @Test
    public void testFindById() {
        when(userMovieRepository.findById(userMovie.getId())).thenReturn(Optional.of(userMovie));

        UserMovie foundUserMovie = userMovieService.findById(userMovie.getId()).get();

        assertNotNull(foundUserMovie);
        assertEquals(userMovie.getId(), foundUserMovie.getId());

        verify(userMovieRepository, times(1)).findById(userMovie.getId());
    }

    @Test
    public void testFindByIdNotFound() {
        Integer nonExistentId = 123;

        when(userMovieRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Optional<UserMovie> foundUserMovie = userMovieService.findById(nonExistentId);

        assertEquals(foundUserMovie, Optional.empty());

        verify(userMovieRepository, times(1)).findById(nonExistentId);
    }

    @Test
    public void testDeleteById() {
        userMovieService.deleteById(userMovie.getId());

        verify(userMovieRepository, times(1)).deleteById(userMovie.getId());
    }


    @Test
    public void testGetUserMovie_MakeMovie() {
        when(userMovieService.findByImdbIdAndUserId(anyString(), anyLong())).thenReturn(Optional.empty());
        when(userService.findById(anyLong())).thenReturn(Optional.of(user));
        when(movieService.findByImdbId(anyString())).thenReturn(Optional.of(movie));
        when(userMovieService.save(any(UserMovie.class))).thenReturn(userMovie);

        UserMovie result = userMovieService.getUserMovie(movieRequest, user.getId());

        assertEquals(result, userMovie);
    }

    @Test
    public void testGetUserMovie_GetMovie() {
        when(userMovieService.findByImdbIdAndUserId(anyString(), anyLong())).thenReturn(Optional.of(userMovie));

        UserMovie result = userMovieService.getUserMovie(movieRequest, user.getId());

        assertEquals(result, userMovie);
    }

    @Test
    public void testSaveWatchStatus_ToWatchtoWatched() {
        boolean watched = true;
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMovieService.save(any(UserMovie.class))).thenReturn(userMovie);
        UserMovie savedUserMovie = userMovieService.saveWatchStatus(movieRequest, user.getId(), watched);

        assertTrue(savedUserMovie.getWatched() != null);
    }

    @Test
    public void testSaveWatchStatus_WatchedToToWatch() {
        boolean watched = false;
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMovieService.save(any(UserMovie.class))).thenReturn(userMovie);
        UserMovie savedUserMovie = userMovieService.saveWatchStatus(movieRequest, user.getId(), watched);

        assertTrue(savedUserMovie.getWatched() == null);
    }
}