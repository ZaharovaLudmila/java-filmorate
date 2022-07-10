# java-filmorate
## Приложение для подбора фильмов для просмотра.
## ER - диаграмма
![This is an image](src/main/resources/filmorate_er.jpeg)

#### Запрос на получение всех пользователей:

SELECT * <br/>
FROM users

#### Запрос на получение пользователя по id:

SELECT * <br/>
FROM users AS us <br/>
WHERE us.id = ?искомый_ID

#### Запрос на получение общих друзей:

SELECT u.user_id, <br/>
       u.email, <br/>
       u.login, <br/>
       u.name, <br/>
       u.birthday, <br/>
FROM friends AS fr1 <br/>
INNER JOIN friends AS fr2 ON fr1.friend_id = fr2.friend_id <br/>
LEFT OUTER JOIN users u on fr1.friend_id = u.user_id
WHERE fr1.user_id = ?искомый_ID1 AND fr2.user_id = ?искомый_ID2 <br/> 
        AND fr1.confirmed IS TRUE AND fr2.confirmed IS TRUE

#### Запрос на получение всех фильмов:

SELECT * <br/>
FROM films

#### Запрос на получение фильма по id:

SELECT *  <br/>
FROM films AS fl <br/>
WHERE fl.id = ?искомый_ID

#### Запрос на получение популярных фильмов:

SELECT<br/>
    l.film_id AS filmID, <br/>
    f.name AS name, <br/>
    f.description AS description, <br/>
    f.releaseDate AS releaseDate, <br/>
    f.duration AS duration, <br/>
    f.MPA_id AS MPA_id, <br/>
    COUNT(DISTINCT l.user_id) <br/>
FROM likes AS l <br/>
LEFT OUTER JOIN films AS f ON l.film_id = f.id <br/>
GROUP BY l.film_id <br/>
ORDER BY COUNT(l.user_id) DESC <br/>
LIMIT ?количество, выводимое на экран



