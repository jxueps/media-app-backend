package com.media.app.service;

import com.media.app.dao.ReviewRepository;
import com.media.app.dto.ReviewRequest;
import com.media.app.entity.Movie;
import com.media.app.entity.Priority;
import com.media.app.entity.Review;
import com.media.app.entity.Role;
import com.media.app.entity.User;
import com.media.app.entity.UserMovie;
import com.media.app.service.interfaces.UserMovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserMovieService userMovieService;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Movie movie;
    private User user;
    private UserMovie userMovie;
    private ReviewRequest reviewRequest;
    private Review review;
    private ReviewRequest updatedReviewRequest;
    private UserMovie existingReview;

    @BeforeEach
    public void setupTestData() {
        MockitoAnnotations.openMocks(this);
        Mockito.reset(userMovieService);

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

        reviewRequest = ReviewRequest.builder()
                .notes("Great movie!")
                .value(5F)
                .build();

        userMovie = UserMovie.builder()
                .id(1)
                .movie(movie)
                .user(user)
                .watched(LocalDateTime.now())
                .review(null)
                .build();

        review = Review.builder()
                .notes("Great movie!")
                .value(5F)
                .build();

        updatedReviewRequest = ReviewRequest.builder()
                .notes("Updated notes")
                .value(4.8F)
                .build();

        existingReview = UserMovie.builder()
                .movie(movie)
                .user(user)
                .watched(LocalDateTime.now())
                .review(review)
                .build();
    }

    @Test
    public void testMakeReview_Success() {
        when(reviewService.save(any(Review.class))).thenAnswer(invocation -> {
            Review savedReview = invocation.getArgument(0);
            savedReview.setId(1);
            return savedReview;
        });

        Review result = reviewService.makeReview(reviewRequest, userMovie);
        System.out.println(result);

        assertEquals(reviewRequest.getNotes(), result.getNotes());
        assertEquals(reviewRequest.getValue(), result.getValue());
    }

    @Test
    public void testMakeReview_InvalidUserMovie() {
        assertThrows(IllegalStateException.class, () -> {
            reviewService.makeReview(reviewRequest, null);
        });

        verify(reviewRepository, never()).save(any(Review.class));
        verify(userMovieService, never()).save(any(UserMovie.class));
    }

    @Test
    public void testAddReview_Success() {
        Integer id = 1;
        when(userMovieService.findById(id)).thenReturn(Optional.of(userMovie));
        Review result = reviewService.addReview(reviewRequest, id);

        verify(userMovieService, times(1)).findById(id);

        assertNotNull(result);
        assertEquals(reviewRequest.getNotes(), result.getNotes());
        assertEquals(reviewRequest.getValue(), result.getValue());
        verify(reviewService, times(1)).save(any(Review.class));
    }

    @Test
    public void testAddReview_InvalidState() {
        UserMovie userMovieInvalid = UserMovie.builder()
                .id(2)
                .watched(LocalDateTime.now())
                .user(user)
                .movie(movie)
                .review(review)
                .priority(Priority.LOW)
                .build();

        when(userMovieService.findById(userMovieInvalid.getId())).thenReturn(Optional.of(userMovieInvalid));

        assertThrows(IllegalStateException.class, () -> reviewService.addReview(reviewRequest, userMovieInvalid.getId()));
        verify(reviewService, never()).save(any(Review.class));
    }

    @Captor
    private ArgumentCaptor<Integer> reviewIdCaptor;

    @Test
    public void testDeleteReviewById_Success() {
        int reviewId = 1;

        reviewRepository.deleteById(reviewId);

        verify(reviewRepository, times(1)).deleteById(reviewIdCaptor.capture());

        assertEquals(reviewId, reviewIdCaptor.getValue());
    }

    @Test
    public void testDeleteReviewById_RepositoryThrowsException() {
        int reviewId = 2;

        Throwable exception = assertThrows(RuntimeException.class, () -> reviewService.deleteReviewById(reviewId));

        verify(reviewRepository, never()).deleteById(anyInt());
        assertEquals("No value present", exception.getMessage());
    }

    @Test
    public void testUpdateReview_ExistingReview() {
        when(userMovieService.findById(existingReview.getId())).thenReturn(Optional.of(existingReview));
        when(reviewRepository.findByUserMovieId(userMovie.getId())).thenReturn(Optional.of(review));

        Review result = reviewService.updateReview(updatedReviewRequest, userMovie.getId());

        assertNotNull(result);
        assertEquals(updatedReviewRequest.getNotes(), result.getNotes());
        assertEquals(updatedReviewRequest.getValue(), result.getValue());

        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(userMovieService, times(1)).save(any(UserMovie.class));
    }

    @Test
    public void testUpdateReview_NoExistingReview() {
        when(userMovieService.findById(userMovie.getId())).thenReturn(Optional.of(userMovie));
        when(reviewRepository.findByUserMovieId(userMovie.getId())).thenReturn(Optional.empty());

        ReviewRequest updatedReviewRequest = ReviewRequest.builder()
                .notes("New review notes")
                .value(4.2F)
                .build();

        Review result = reviewService.updateReview(updatedReviewRequest, userMovie.getId());

        assertNotNull(result);
        assertEquals(updatedReviewRequest.getNotes(), result.getNotes());
        assertEquals(updatedReviewRequest.getValue(), result.getValue());

        verify(reviewRepository, never()).save(any(Review.class));

        verify(userMovieService, times(1)).save(any(UserMovie.class));
    }
}
