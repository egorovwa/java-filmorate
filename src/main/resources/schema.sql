/* test*/
DROP ALL OBJECTS;




CREATE TABLE IF NOT EXISTS films
(
    film_id     integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        varchar(50) NOT NULL,
    description varchar(200),
    releaseDate date, /*ограничение в Sql?*/
    rate        integer,
    duration    integer, /*почитатьт как толко положит.*/
    mpa_id      integer
);

CREATE TABLE IF NOT EXISTS genres
(
    genres_id  integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_name varchar(20) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS film_genres
(
    id        integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genres_id integer REFERENCES genres (genres_id),
    film_id   integer REFERENCES films (film_id)
);
CREATE TABLE IF NOT EXISTS mpas
(
    mpa_id          integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    mpa_name        varchar(6) NOT NULL UNIQUE,
    mpa_description varchar(80)
);
CREATE TABLE IF NOT EXISTS users
(
    user_id  integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    varchar(40),
    login    varchar(20) NOT NULL UNIQUE,
    name     varchar(40),
    birthday date

);
CREATE TABLE IF NOT EXISTS friendship
(
    id        integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id   integer REFERENCES users (user_id),
    friend_id integer REFERENCES users (user_id),
    status    boolean, /*boolean type как сделть икать */
    UNIQUE (user_id, friend_id)
);


CREATE TABLE IF NOT EXISTS likes
(
    id      integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id integer REFERENCES users (user_id),
    film_id integer REFERENCES films (film_id)
);

ALTER TABLE films
    ADD CONSTRAINT IF NOT EXISTS films_add_References FOREIGN KEY (mpa_id) REFERENCES mpas (mpa_id);
