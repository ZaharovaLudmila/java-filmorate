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
    f* <br/>
FROM FILMS AS f <br/>
LEFT OUTER JOIN LIKES AS l ON f.id = l.film_id <br/>
GROUP BY f.id <br/>
ORDER BY COUNT(l.user_id) DESC  <br/>
LIMIT ?количество, выводимое на экран



