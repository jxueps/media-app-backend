package com.media.app.controller;

import com.media.app.dto.ReviewRequest;
import com.media.app.entity.Review;
import com.media.app.entity.User;
import com.media.app.entity.UserMovie;
import com.media.app.service.interfaces.ReviewService;
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

    @PreAuthorize("#user == authentication.principal")
    @GetMapping("/get/{userMovieId}")
    public ResponseEntity<Review> getReviewForMovieIdFromUser(@PathVariable Integer userMovieId, @AuthenticationPrincipal User user) {
        Review review = reviewService.findReviewByUserMovieId(userMovieId).get();
        return ResponseEntity.ok(review);
    }

    @PreAuthorize("#user == authentication.principal")
    @PostMapping("/add/{userMovieId}")
    public ResponseEntity<Review> addReview(@Valid @RequestBody ReviewRequest reviewRequest, @PathVariable Integer userMovieId, @AuthenticationPrincipal User user) {
        Review review = reviewService.addReview(reviewRequest, userMovieId);
        return ResponseEntity.ok(review);
    }

    @PreAuthorize("#user == authentication.principal")
    @PutMapping("/update/{userMovieId}")
    public ResponseEntity<Review> updateReview(@Valid @RequestBody ReviewRequest reviewRequest, @PathVariable Integer userMovieId, @AuthenticationPrincipal User user) {
        Review review = reviewService.updateReview(reviewRequest, userMovieId);
        return ResponseEntity.ok(review);
    }

    @PreAuthorize("#user == authentication.principal")
    @DeleteMapping("/delete/{userMovieId}")
    public void deleteReview(@PathVariable Integer userMovieId, @AuthenticationPrincipal User user) {
        reviewService.deleteReviewById(userMovieId);
    }
}
