INSERT INTO GENRES(genre_name)
VALUES ('Комедия') ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name)
VALUES ('Драма') ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name)
VALUES ('Мультфильм') ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name)
VALUES ('Триллер') ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name)
VALUES ('Документальный') ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name)
VALUES ('Боевик') ON CONFLICT DO NOTHING;
INSERT INTO MPAS(mpa_name, mpa_description)
VALUES ('G', 'у фильма нет возрастных ограничений') ON CONFLICT DO NOTHING;
INSERT INTO MPAS(mpa_name, mpa_description)
VALUES ('PG', 'детям рекомендуется смотреть фильм с родителями') ON CONFLICT DO NOTHING;
INSERT INTO MPAS(mpa_name, mpa_description)
VALUES ('PG-13', 'детям до 13 лет просмотр не желателен') ON CONFLICT DO NOTHING;
INSERT INTO MPAS(mpa_name, mpa_description)
VALUES ('R', 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого') ON CONFLICT DO NOTHING;
INSERT INTO MPAS(mpa_name, mpa_description)
VALUES ('NC-17', 'лицам до 18 лет просмотр запрещён') ON CONFLICT DO NOTHING;

INSERT INTO USERS(EMAIL, LOGIN, NAME, BIRTHDAY)
VALUES ('email@mail.ru', 'user', 'name', '1999-09-09');
    INSERT INTO USERS(EMAIL, LOGIN, NAME, BIRTHDAY)
VALUES ('friend@mail.ru', 'friend', 'friendName', '1799-09-09');

INSERT INTO FILMS(name, description, releasedate, rate, duration, mpa_id)
VALUES ( 'Film1','ddd','1990-12-12',0,15,1 );
INSERT INTO FILM_GENRES(GENRES_ID, FILM_ID) VALUES ( 1,1 );

INSERT INTO FILMS(name, description, releasedate, rate, duration, mpa_id)
VALUES ( 'Film2','d2d2d','1999-12-12',2,16,5 );
INSERT INTO FILM_GENRES(GENRES_ID, FILM_ID) VALUES ( 2,2 );