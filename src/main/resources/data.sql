INSERT INTO users (username, email, password, enabled)
VALUES
('user', 'user@gmail.com', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE),
('admin','admin@gmail.com', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE),
('thieu','thieuvanderlee1980@hotmail.com', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE);

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_ADMIN'),
('thieu', 'ROLE_USER');




INSERT INTO movies(movie_title, movie_genre, movie_description,movie_rating,username,movie_poster)
VALUES
('Terminator','action','In the post-apocalyptic future, reigning tyrannical supercomputers teleport a cyborg assassin known as the "\"Terminator"\" back to 1984 to kill Sarah Connor, whose unborn son is destined to lead insurgents against 21st century mechanical hegemony. Meanwhile, the human-resistance movement dispatches a lone warrior to safeguard Sarah. Can he stop the virtually indestructible killing machine?',0,'user', 'user'),
('Hollowman','action','Nothing to see',5,'admin','admin'),
('Once upon a time in the West','action','The original version by the director was 166 minutes when it was first released on 21 December 1968. This version was shown in European cinemas, and was a box-office success. For the US release on 28 May 1969, Once Upon a Time in the West was edited down to 145 minutes by Paramount and was a financial flop.',0,'admin','admin'),
('Sound of music','musical','Women in clothes made of curtains sings in the mountains',0,'user', 'user');

INSERT INTO reviews(review, review_rating, bad_language,username,movie_id,reviewer)
VALUES
('Mijn favoriet uit de reeks. Ten onrechte als actiefilm aangemerkt, dit is een sf thriller met horror elementen. Bepaalde effecten zijn zeker gedateerd (het namaakhoofd van Schwarz, de stop motion robot) maar over het algemeen is toch duidelijk het visuele genie (en de obsessie met machines en trucks) van Cameron al zichtbaar, zeker in de scenes die de grimmige toekomst tonen. Eigenlijk zijn er 2 elementen die deze film voor mij ver boven de andere uit de reeks uittillen: Michael Biehn en de soundtrack van Brad Fiedel. Nu zit er ook een wat mindere synth stukjes bij (teveel random toetsaanslagen bij bepaalde achtervolgingen) maar dit blijft veruit de meest sfeervolle Terminator en dat mag voor een belangrijk deel aan Fiedel mogen toegerekend. Natuurlijk is Schwarz ook geknipt. Verder mag toch bewondering opwekken het zeer beperkte budget waarmee Cameron gewerkt heeft. Wat ik wel vreemd vind is dat Terminator 2 zekere updates gekregen heeft (de stuntman op de motor die te duidelijk niet op Schwarz leek is aangepast) maar dat de mindere effecten hier gehandhaafd zijn gebleven en er nooit een Special Edition is gekomen. Misschien uit respect voor het harde werk van de crew want talent hadden ze zeker. Het steekt verder ook niet uit als een zere duim, daarvoor is het geheel gewoon veel te effectief in het transporteren van de kijker.',8.3,false,'user',1,'user'),
('Nice movie, woman sings very good',7,false,'user',3,'user'),
('Nice movie with a lot robots',8.5,true,'admin',1,'admin'),
('Indeed a good movie, old though',8,false,'user',3,'user');