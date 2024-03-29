CREATE TABLE IF NOT EXISTS MOTION_PICTURE_ASSOCIATIONS (
    MPA_ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    MPA_NAME VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS GENRES (
    GENRE_ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    GENRE_NAME VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS FILMS (
    FILM_ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    FILM_NAME VARCHAR(150) NOT NULL,
    DESCRIPTION VARCHAR(200),
    RELEASE_DATE DATE,
    DURATION INTEGER,
    MPA_ID INTEGER REFERENCES MOTION_PICTURE_ASSOCIATIONS (MPA_ID)
);

CREATE TABLE IF NOT EXISTS FILM_GENRES (
    FILM_ID INTEGER REFERENCES FILMS (FILM_ID) ON DELETE CASCADE,
    GENRE_ID INTEGER REFERENCES GENRES (GENRE_ID) ON DELETE CASCADE,
    PRIMARY KEY (FILM_ID, GENRE_ID)
);

CREATE TABLE IF NOT EXISTS USERS (
    USER_ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    USER_NAME VARCHAR(100),
    EMAIL VARCHAR(100),
    LOGIN VARCHAR(100) NOT NULL,
    BIRTHDAY DATE
);

CREATE TABLE IF NOT EXISTS LIKES (
    FILM_ID INTEGER REFERENCES FILMS (FILM_ID) ON DELETE CASCADE,
    USER_ID INTEGER REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    PRIMARY KEY (FILM_ID, USER_ID)
);

CREATE TABLE IF NOT EXISTS FRIENDS (
    USER_ID INTEGER REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    FRIEND_ID INTEGER REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    CONFIRMED BOOLEAN,
    PRIMARY KEY (USER_ID, FRIEND_ID)
    );

