package com.media.app.controller;

import com.media.app.dto.ErrorResponse;
import com.media.app.dto.ReviewRequest;
import com.media.app.entity.Review;
import com.media.app.entity.User;
import com.media.app.entity.UserMovie;
import com.media.app.service.interfaces.ReviewService;
import com.media.app.service.interfaces.UserMovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserMovieService userMovieService;

    @PreAuthorize("#user == authentication.principal")
    @GetMapping("/{userMovieId}")
    public ResponseEntity<?> getReviewForMovieIdFromUser(@PathVariable Integer userMovieId, @AuthenticationPrincipal User user) {

        UserMovie userMovie = userMovieService.findById(userMovieId).get();
        if (userMovie.getUser().getId() != user.getId()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Cannot access resource"));
        }
        if (userMovie.getReview() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMovie.getReview());
    }

    @PreAuthorize("#user == authentication.principal")
    @PostMapping("/{userMovieId}")
    public ResponseEntity<Review> addReview(@Valid @RequestBody ReviewRequest reviewRequest, @PathVariable Integer userMovieId, @AuthenticationPrincipal User user) {
        Review review = reviewService.addReview(reviewRequest, userMovieId);
        return ResponseEntity.ok(review);
    }

    @PreAuthorize("#user == authentication.principal")
    @PutMapping("/{userMovieId}")
    public ResponseEntity<Review> updateReview(@Valid @RequestBody ReviewRequest reviewRequest, @PathVariable Integer userMovieId, @AuthenticationPrincipal User user) {
        Review review = reviewService.updateReview(reviewRequest, userMovieId);
        return ResponseEntity.ok(review);
    }

    @PreAuthorize("#user == authentication.principal")
    @DeleteMapping("/{userMovieId}")
    public void deleteReview(@PathVariable Integer userMovieId, @AuthenticationPrincipal User user) {
        reviewService.deleteReviewById(userMovieId);
    }
}
