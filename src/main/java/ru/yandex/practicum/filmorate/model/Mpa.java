package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mpa {

    private int id;
    @Size(max = 50)
    private String name;
}
