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




INSERT INTO movies(movie_title, movie_genre, movie_description,movie_rating,username,movie_poster)
VALUES
('Terminator','action','In the post-apocalyptic future, reigning tyrannical supercomputers teleport a cyborg assassin known as the "\"Terminator"\" back to 1984 to kill Sarah Connor, whose unborn son is destined to lead insurgents against 21st century mechanical hegemony. Meanwhile, the human-resistance movement dispatches a lone warrior to safeguard Sarah. Can he stop the virtually indestructible killing machine?',0,'user', 'user'),
('Hollowman','action','Nothing to see',0,'admin','admin'),
('Sound of music','musical','Women in clothes made of curtains sings in the mountains',0,'user', 'user');

INSERT INTO reviews(review, review_rating, bad_language,username,movie_id,reviewer)
VALUES
('Nice movie with a lot of kills',8.3,false,'user',1,'user'),
('Nice movie, woman sings very good',7,false,'user',3,'user'),
('Nice movie with a lot robots',8.5,false,'user',1,'admin'),
('Indeed a good movie, old though',8,false,'user',3,'user');