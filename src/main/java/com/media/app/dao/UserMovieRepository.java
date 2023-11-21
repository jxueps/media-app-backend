package com.media.app.dao;

import com.media.app.entity.Movie;
import com.media.app.entity.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserMovieRepository extends JpaRepository<UserMovie, Integer> {
    @Query("""
        select um from UserMovie um
        where um.user.id = :userId and um.watched = null
        """)
    List<UserMovie> findToWatchMoviesByUser(Long userId);

    @Query("""
        select um from UserMovie um
        where um.user.id = :userId and um.watched != null
        """)
    List<UserMovie> findWatchedMoviesByUser(Long userId);

    @Query("""
        select um from UserMovie um
        where um.user.id = :userId and um.movie.imdbId = :imdbId
        """)
    Optional<UserMovie> findByImdbIdAndUserId(String imdbId, Long userId);

    Optional<UserMovie> findById(int id);
}
