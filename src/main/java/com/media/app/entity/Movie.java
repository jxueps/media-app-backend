package com.media.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Year;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name="movie")
public class Movie {
    @Id
    @Column(name="imdb_id")
    @NotNull(message = "Id is required")
    private String imdbId;

    @Column(name="title")
    @NotNull(message = "Title is required")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="year")
    private String year;

    @Column(name="runtime")
    private String runtime;

    @Column(name = "rated")
    private String rated;

    @Column(name="poster")
    private String poster;
}
