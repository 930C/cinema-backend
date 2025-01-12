package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.enumeration.MovieStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "movie_id_unique", columnNames = {"id"}),
        @UniqueConstraint(name = "movie_imdbId_unique", columnNames = {"imdbId"})
})
public class Movie {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "movie_sequence", sequenceName = "movie_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "movie_sequence")
    @Column(name = "id")
    private @Id Long id;

    private @NonNull @Column(columnDefinition = "TEXT") String title;
    private String releaseYear;
    private String posterImage;
    private @NonNull @NotNull MovieRating rated = MovieRating.NA;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private @Column(columnDefinition = "TEXT") String actors;
    private @Column(columnDefinition = "TEXT") String plot;
    private String trailer;
    private String imdbId;
    private String imdbRating;
    private String imdbRatingCount;

    @Enumerated(EnumType.STRING)
    private MovieStatus movieStatus = MovieStatus.IN_CATALOG;

    public Movie(@NonNull String title, String releaseYear, String posterImage, MovieRating rated, String runtime,
                 String genre,
                 String director, String writer, String actors, String plot, String trailer, String imdbId,
                 String imdbRating, String imdbRatingCount) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.posterImage = posterImage;
        this.rated = rated;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.trailer = trailer;
        this.imdbId = imdbId;
        this.imdbRating = imdbRating;
        this.imdbRatingCount = imdbRatingCount;
    }

    public String getRated() {
        return rated.label;
    }
}
