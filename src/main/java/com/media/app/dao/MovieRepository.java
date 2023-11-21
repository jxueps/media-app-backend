package com.media.app.dao;

import com.media.app.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    @Query("""
        select m from Movie m
        where m.imdbId = :imdbId
        """)
    Optional<Movie> findByImdbId(String imdbId);

    Boolean existsByImdbId(String imdbId);
}
