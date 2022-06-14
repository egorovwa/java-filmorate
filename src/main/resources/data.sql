// заполнить genres
INSERT INTO GENRES(genre_name) VALUES ( 'Комедия' );
INSERT INTO GENRES(genre_name) VALUES ( 'Драма' );
INSERT INTO GENRES(genre_name) VALUES ( 'Мультфильм' );
INSERT INTO GENRES(genre_name) VALUES ( 'Триллер' );
INSERT INTO GENRES(genre_name) VALUES ( 'Документальный' );
INSERT INTO GENRES(genre_name) VALUES ( 'Боевик' );

// заполнить mpas
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'G','у фильма нет возрастных ограничений' );
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'PG','детям рекомендуется смотреть фильм с родителями' );
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'PG-13','детям до 13 лет просмотр не желателен' );
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'R','лицам до 17 лет просматривать фильм можно только в присутствии взрослого' );
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'NC-17','лицам до 18 лет просмотр запрещён' );
