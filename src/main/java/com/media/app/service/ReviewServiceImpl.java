package com.media.app.service;

import com.media.app.dao.ReviewRepository;
import com.media.app.dto.ReviewRequest;
import com.media.app.entity.Review;
import com.media.app.entity.UserMovie;
import com.media.app.service.interfaces.ReviewService;
import com.media.app.service.interfaces.UserMovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserMovieService userMovieService;

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> findById(Integer id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Optional<Review> findReviewByUserMovieId(Integer id) {
        return reviewRepository.findByUserMovieId(id);
    }

    @Override
    public Review addReview(ReviewRequest reviewRequest, Integer userMovieId) {
        Optional<UserMovie> userMovie = userMovieService.findById(userMovieId);

        if (userMovie.isEmpty()) {
            throw new NullPointerException("Cannot find userMovie with ID: " + userMovieId);
        }

        if ( userMovie.get().getReview() != null || userMovie.get().getWatched() == null) {
            throw new IllegalStateException("Invalid -- can't add more than one review and " +
                    "MUST be watched to add a review");
        }

        return makeReview(reviewRequest, userMovie.get());
    }

    @Override
    public Review makeReview(ReviewRequest reviewRequest, UserMovie userMovie) {
        if ( userMovie == null) {
            throw new IllegalStateException("Invalid user movie.");
        }

        Review review = Review.builder()
                .value(reviewRequest.getValue())
                .notes(reviewRequest.getNotes())
                .timestamp(LocalDateTime.now())
                .build();

        review = this.save(review);

        userMovie.setReview(review);
        userMovieService.save(userMovie);
        return review;
    }

    @Override
    public Review updateReview(ReviewRequest reviewRequest, Integer userMovieId) {
        Optional<Review> reviewDb = findReviewByUserMovieId(userMovieId);

        Optional<UserMovie> userMovie = userMovieService.findById(userMovieId);

        if (userMovie.isEmpty()) {
            throw new IllegalStateException("Invalid user movie");
        }

        if (userMovie.get().getReview() == null || reviewDb.isEmpty()) {
            return makeReview(reviewRequest, userMovie.get());
        }

        reviewDb.get().setValue(reviewRequest.getValue());
        reviewDb.get().setNotes(reviewRequest.getNotes());
        this.save(reviewDb.get());

        userMovie.get().setReview(reviewDb.get());
        userMovieService.save(userMovie.get());

        return reviewDb.get();
    }

    @Override
    public void deleteReviewById(Integer userMovieId) {
        UserMovie userMovie = userMovieService.findById(userMovieId).get();
        if (userMovie != null) {
            if (userMovie.getReview() != null) {
                Integer id = userMovie.getReview().getId();
                userMovie.setReview(null);
                reviewRepository.deleteById(id);
            }
            else {
                throw new RuntimeException("No review to delete");
            }
        } else {
            throw new RuntimeException("User movie does not exist");
        }
    }
}
