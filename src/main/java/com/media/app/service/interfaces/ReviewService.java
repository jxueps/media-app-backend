package com.media.app.service.interfaces;

import com.media.app.dto.ReviewRequest;
import com.media.app.entity.Review;
import com.media.app.entity.UserMovie;

import java.util.Optional;

public interface ReviewService {
    Review save(Review review);

    Optional<Review> findById(Integer id);

    Optional<Review> findReviewByUserMovieId(Integer id);

    Review makeReview(ReviewRequest reviewRequest, UserMovie userMovie);

    Review addReview(ReviewRequest reviewRequest, Integer userMovieId);

    Review updateReview(ReviewRequest reviewRequest, Integer userMovieId);

    void deleteReviewById(Integer reviewId);
}
