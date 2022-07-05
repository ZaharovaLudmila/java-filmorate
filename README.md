# java-filmorate
Приложение для подбора фильмов для просмотра.
ER - диаграмма
![This is an image](src/main/resources/filmorate_er.jpeg)

Запрос на получение всех пользователей:
SELECT *
FROM users

Запрос на получение пользователя по id:
SELECT *
FROM users AS us
WHERE us.id = ?искомый_ID

Запрос на получение общих друзей:
SELECT fr1.friend_id
FROM friends AS fr1
INNER JOIN friends AS fr2 ON fr1.friend_id = fr2.friend_id
WHERE fr1.user_id = ?искомый_ID1 AND fr2.user_id = ?искомый_ID2 
        AND fr1.confirmed IS TRUE AND fr2.confirmed IS TRUE

Запрос на получение всех фильмов:
SELECT *
FROM films

Запрос на получение фильма по id:
SELECT *
FROM films AS fl
WHERE fl.id = ?искомый_ID

Запрос на получение популярных фильмов:
SELECT
    l.film_id AS filmID,
    f.name AS name,
    f.description AS description,
    f.releaseDate AS releaseDate,
    f.duration AS duration,
    mpa.name AS MPA,
    COUNT(DISTINCT l.user_id)
FROM likes AS l
LEFT OUTER JOIN films AS f ON l.film_id = f.id
LEFT OUTER JOIN motion_picture_associations AS mpa ON l.film_id = mpa.id
GROUP BY l.film_id
ORDER BY COUNT(l.user_id) DESC
LIMIT ?количество, выводимое на экран



