package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private int id;

    @NonNull
    @NotBlank
    private String name;

    @Size(min = 0, max = 200)
    private String description;

    @PastOrPresent
    private LocalDate releaseDate;

    @Positive
    private long duration;
}
