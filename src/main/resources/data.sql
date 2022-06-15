// заполнить genres
INSERT INTO GENRES(genre_name) VALUES  ( 'Комедия' ) ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name) VALUES ( 'Драма' ) ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name) VALUES ( 'Мультфильм' ) ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name) VALUES ( 'Триллер' ) ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name) VALUES ( 'Документальный' ) ON CONFLICT DO NOTHING;
INSERT INTO GENRES(genre_name) VALUES ( 'Боевик' ) ON CONFLICT DO NOTHING;

// заполнить mpas
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'G','у фильма нет возрастных ограничений' ) ON CONFLICT DO NOTHING;
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'PG','детям рекомендуется смотреть фильм с родителями' ) ON CONFLICT DO NOTHING;
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'PG-13','детям до 13 лет просмотр не желателен' ) ON CONFLICT DO NOTHING;
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'R','лицам до 17 лет просматривать фильм можно только в присутствии взрослого' ) ON CONFLICT DO NOTHING;
INSERT INTO MPAS(mpa_name, mpa_description) VALUES ( 'NC-17','лицам до 18 лет просмотр запрещён' ) ON CONFLICT DO NOTHING;
