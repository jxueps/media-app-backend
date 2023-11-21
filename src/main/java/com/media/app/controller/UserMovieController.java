package com.media.app.controller;

import com.media.app.dto.MovieRequest;
import com.media.app.entity.User;
import com.media.app.entity.UserMovie;
import com.media.app.service.interfaces.MovieService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userMovie")
public class UserMovieController {
    private final UserMovieService userMovieService;

    @PreAuthorize("#user == authentication.principal")
    @GetMapping("/{userMovieId}")
    public ResponseEntity<UserMovie> getUserMovie(@PathVariable Integer userMovieId, @AuthenticationPrincipal User user) {
        Optional<UserMovie> userMovieOptional = userMovieService.findById(userMovieId);

        if (userMovieOptional.isPresent()) {
            return ResponseEntity.ok(userMovieOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("#user == authentication.principal")
    @PostMapping("/addToWatch")
    public ResponseEntity<UserMovie> addMovieToWatch(@Valid @RequestBody MovieRequest movieRequest, @AuthenticationPrincipal User user) {
        UserMovie userMovie = userMovieService.saveWatchStatus(movieRequest, user.getId(), false);

        return ResponseEntity.ok(userMovie);
    }

    @PreAuthorize("#user == authentication.principal")
    @PostMapping("/addWatched")
    public ResponseEntity<UserMovie> addMovieWatched(@Valid @RequestBody MovieRequest movieRequest, @AuthenticationPrincipal User user) {
        UserMovie userMovie = userMovieService.saveWatchStatus(movieRequest, user.getId(), true);
        return ResponseEntity.ok(userMovie);
    }

    @PreAuthorize("#user == authentication.principal")
    @GetMapping("/getToWatch")
    public ResponseEntity<List<UserMovie>> getToWatchedMoviesByUser(@AuthenticationPrincipal User user) {
        List<UserMovie> userMovies = userMovieService.findToWatchMoviesByUser(user.getId());
        return ResponseEntity.ok(userMovies);
    }

    @PreAuthorize("#user == authentication.principal")
    @GetMapping("/getWatched")
    public ResponseEntity<List<UserMovie>> getWatchedMoviesByUser(@AuthenticationPrincipal User user) {
        List<UserMovie> userMovies = userMovieService.findWatchedMoviesByUser(user.getId());
        return ResponseEntity.ok(userMovies);
    }

    @PreAuthorize("#user == authentication.principal")
    @DeleteMapping("/removeFromToWatch/{id}")
    public void deleteMovieFromUser (@PathVariable Integer id, @AuthenticationPrincipal User user) {
        userMovieService.deleteById(id);
    }

}

