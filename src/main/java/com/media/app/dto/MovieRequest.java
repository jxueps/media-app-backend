package com.media.app.dto;

import com.media.app.entity.Priority;
import com.media.app.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@ToString
public class MovieRequest {
    @NotNull(message = "Id is required")
    private String imdbId;

    @NotNull(message = "Title is required")
    private String title;

    private String description;

    private String year;

    private String runtime;

    private String rated;

    private String poster;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}
