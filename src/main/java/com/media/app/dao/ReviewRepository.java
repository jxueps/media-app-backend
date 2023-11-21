package com.media.app.dao;

import com.media.app.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r JOIN UserMovie um ON r.id = um.review.id WHERE um.id= :userMovieId")
    Optional<Review> findByUserMovieId(Integer userMovieId);
}
