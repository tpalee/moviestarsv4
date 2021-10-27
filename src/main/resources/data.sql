INSERT INTO users (username, password, enabled)
VALUES
('user', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE),
('admin', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE),
('thieu', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE);

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_USER'),
('admin', 'ROLE_ADMIN'),
('thieu', 'ROLE_USER'),
('thieu', 'ROLE_ADMIN');




INSERT INTO movies(movie_title, movie_genre, movie_description,movie_rating,username)
VALUES
('Terminator','action','Arnold shoots everybody',0,'user'),
('Hollowman','action','Nothing to see',0,'admin'),
('Sound of music','musical','Women in clothes made of curtains sings in the mountains',0,'user');

INSERT INTO reviews(review, review_rating, bad_language,username,movie_id)
VALUES
('Nice movie with a lot of kills','8.3',false,'user',1),
('It was not so hard','4.7',false,'admin',1),
('Nice movie, woman sings very good','7',false,'user',3);