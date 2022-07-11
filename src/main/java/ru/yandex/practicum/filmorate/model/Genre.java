package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Size;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    private int id;
    @Size(max = 50)
    private String name;
}