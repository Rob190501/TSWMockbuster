DROP DATABASE IF EXISTS mockbuster;
CREATE DATABASE IF NOT EXISTS mockbuster;
USE mockbuster;

DROP TABLE IF EXISTS user;
CREATE TABLE user (	
  id int PRIMARY KEY AUTO_INCREMENT,
  email varchar(200) NOT NULL,
  password varchar(200) NOT NULL,
  first_name varchar(200) NOT NULL,
  last_name varchar(200) NOT NULL,
  birthday date NOT NULL,
  billing_address varchar(200) NOT NULL,
  credit DECIMAL(10, 2) NOT NULL DEFAULT 0,
  is_admin boolean NOT NULL DEFAULT FALSE,
  UNIQUE(email)
) AUTO_INCREMENT=1;

DROP TABLE IF EXISTS phone;
CREATE TABLE phone (	
  num varchar(10) NOT NULL,
  user_id int NOT NULL,
  PRIMARY KEY(num, user_id),
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE NO ACTION ON UPDATE CASCADE
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders (	
  id int PRIMARY KEY AUTO_INCREMENT,
  order_date date NOT NULL DEFAULT (CURDATE()),
  total DECIMAL(10, 2) NOT NULL,
  user_id int NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE NO ACTION ON UPDATE CASCADE
) AUTO_INCREMENT=1;

DROP TABLE IF EXISTS movie;
CREATE TABLE movie (	
  id int PRIMARY KEY AUTO_INCREMENT,
  title varchar(200) NOT NULL,
  plot varchar(200) NOT NULL,
  duration smallint NOT NULL,
  movie_year smallint NOT NULL,
  available_licenses smallint NOT NULL,
  daily_rental_price DECIMAL(10, 2) NOT NULL,
  purchase_price DECIMAL(10, 2) NOT NULL,
  is_visible boolean NOT NULL DEFAULT true,
  poster_path varchar(200) NOT NULL DEFAULT ''
) AUTO_INCREMENT=1;

DROP TABLE IF EXISTS movie_rental_order;
CREATE TABLE movie_rental_order (	
  order_id int NOT NULL,
  movie_id int NOT NULL,
  daily_price DECIMAL(10, 2) NOT NULL,
  days smallint NOT NULL,
  PRIMARY KEY (order_id, movie_id),
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (movie_id) REFERENCES movie(id) ON DELETE NO ACTION ON UPDATE CASCADE
);

DROP TABLE IF EXISTS movie_purchase_order;
CREATE TABLE movie_purchase_order (	
  order_id int NOT NULL,
  movie_id int NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (order_id, movie_id),
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (movie_id) REFERENCES movie(id) ON DELETE NO ACTION ON UPDATE CASCADE
);



INSERT INTO user (email, password, first_name, last_name, birthday, billing_address, credit, is_admin) VALUES
('carlo.neri@example.com', SHA2('pass4', 512), 'Carlo', 'Neri', '2004-01-15', 'Piazza San Marco 15 30100 Venezia', 100, TRUE),
('paolo.rosa@example.com', SHA2('pass7', 512), 'Paolo', 'Rosa', '2000-01-30', 'Via Torino 9 10100 Torino', 250, TRUE);
INSERT INTO user (email, password, first_name, last_name, birthday, billing_address) VALUES
('mario.rossi@example.com', SHA2('pass1', 512), 'Mario', 'Rossi', '2000-04-15', 'Via Roma 1 00100 Roma'),
('luigi.bianchi@example.com', SHA2('pass2', 512), 'Luigi', 'Bianchi', '2000-06-15', 'Corso Italia 10 20100 Milano'),
('anna.verdi@example.com', SHA2('pass3', 512), 'Anna', 'Verdi', '2000-06-15', 'Via Napoli 20 80100 Napoli'),
('maria.gialli@example.com', SHA2('pass5', 512), 'Maria', 'Gialli', '2000-09-15', 'Via Firenze 5 50100 Firenze'),
('giulia.blu@example.com', SHA2('pass6', 512), 'Giulia', 'Blu', '2000-04-15', 'Via Bologna 12 40100 Bologna'),
('francesca.azzurri@example.com', SHA2('pass8', 512), 'Francesca', 'Azzurri', '2000-07-15', 'Via Palermo 8 90100 Palermo'),
('giovanni.violetto@example.com', SHA2('pass9', 512), 'Giovanni', 'Violetto', '2005-04-15', 'Via Bari 3 70100 Bari'),
('sara.ocra@example.com', SHA2('pass10', 512), 'Sara', 'Ocra', '2000-08-15', 'Via Genova 14 16100 Genova');



INSERT INTO phone (num, user_id) VALUES 
('3456789012', 1),  -- Carlo Neri
('3456789013', 2),  -- Paolo Rosa
('3456789014', 3),  -- Mario Rossi
('3456789015', 4),  -- Luigi Bianchi
('3456789016', 5),  -- Anna Verdi
('3456789017', 6),  -- Maria Gialli
('3456789018', 7),  -- Giulia Blu
('3456789019', 8);  -- Francesca Azzurri



INSERT INTO movie (title, plot, duration, movie_year, available_licenses, daily_rental_price, purchase_price, poster_path) VALUES
('Il Padrino', 'Storia epica di una famiglia mafiosa', 175, 1972, 10, 5, 20, 'ilpadrino.jpg'),
('Forrest Gump', 'Le avventure di un uomo straordinario nella sua semplicità', 142, 1994, 15, 4, 18, 'forrestgump.jpeg'),
('Inception', 'Un ladro che ruba segreti attraverso l\'uso della tecnologia del sogno', 148, 2010, 12, 6, 25, 'inception.png'),
('Il Cavaliere Oscuro', 'La lotta di Batman contro il crimine e il Joker', 152, 2008, 8, 5, 22, 'thedarkknight.jpg'),
('Pulp Fiction', 'Storie intrecciate di criminali a Los Angeles', 154, 1994, 10, 4, 20, 'pulpfiction.jpg'),
('Schindlers List', 'La vera storia di Oskar Schindler che salvò ebrei durante l Olocausto', 195, 1993, 5, 6, 30, 'schindlerslist.jpg'),
('Matrix', 'Un hacker scopre la verita sulla sua realtà', 136, 1999, 9, 4, 18, 'matrix.jpg'),
('Interstellar', 'Un viaggio attraverso lo spazio per salvare l\'umanità', 169, 2014, 7, 5, 24, 'interstellar.jpg'),
('Gladiatore', 'Un generale romano diventa gladiatore per vendicarsi', 155, 2000, 10, 4, 20, 'gladiatore.jpg'),
('Titanic', 'Una storia d\'amore durante il disastro del Titanic', 195, 1997, 6, 5, 22, 'titanic.jpg'),
('Ritorno al Futuro', 'Un adolescente viaggia nel tempo per sistemare il passato', 116, 1985, 12, 5, 18, 'ritornoalfuturo.jpg');



INSERT INTO orders (order_date, total, user_id) VALUES
('2024-01-15', 55, 1),
('2024-02-20', 33, 2),
('2024-03-25', 24, 3),
('2024-04-10', 33, 4),
('2024-05-05', 20, 5),
('2024-06-15', 60, 6),
('2024-07-20', 20, 7),
('2024-08-25', 65, 8);



INSERT INTO movie_rental_order (order_id, movie_id, daily_price, days) VALUES
(1, 1, 5, 3),    -- Noleggio: Utente 1 noleggia Il Padrino per 3 giorni
(2, 2, 4, 2),    -- Noleggio: Utente 2 noleggia Forrest Gump per 2 giorni
(3, 3, 6, 4),    -- Noleggio: Utente 3 noleggia Inception per 4 giorni
(4, 4, 5, 3),    -- Noleggio: Utente 4 noleggia Il Cavaliere Oscuro per 3 giorni
(5, 5, 4, 5),    -- Noleggio: Utente 5 noleggia Pulp Fiction per 5 giorni
(6, 6, 6, 5),    -- Noleggio: Utente 6 noleggia Schindlers List per 5 giorni
(8, 8, 5, 5);    -- Noleggio: Utente 8 noleggia Interstellar per 5 giorni



INSERT INTO movie_purchase_order (order_id, movie_id, price) VALUES
(1, 2, 18),    -- Acquisto: Utente 1 acquista Forrest Gump
(1, 4, 22),    -- Acquisto: Utente 1 acquista Il Cavaliere Oscuro
(2, 3, 25),    -- Acquisto: Utente 2 acquista Inception
(4, 7, 18),    -- Acquisto: Utente 4 acquista Matrix
(6, 6, 30),    -- Acquisto: Utente 6 acquista Schindlers List
(7, 9, 20),    -- Acquisto: Utente 7 acquista Gladiatore
(8, 1, 20),    -- Acquisto: Utente 8 acquista Il Padrino
(8, 5, 20);    -- Acquisto: Utente 8 acquista Pulp Fiction



-- 4
SELECT o.id AS order_id, o.order_date, o.total AS order_total, m.title AS movie_title, mr.daily_price AS price, 'Noleggio' AS order_type, mr.days AS days
FROM (movie_rental_order mr JOIN movie m ON mr.movie_id = m.id) JOIN orders o ON mr.order_id = o.id
WHERE o.id = 1
UNION
SELECT o.id AS order_id, o.order_date, o.total AS order_total, m.title AS movie_title, mp.price AS price, 'Acquisto' AS order_type, '' AS days
FROM (movie_purchase_order mp JOIN movie m ON mp.movie_id = m.id) JOIN orders o ON mp.order_id = o.id
WHERE o.id = 1;

-- 5
SELECT *
FROM orders;

-- 6
UPDATE user
SET is_admin = true
WHERE id = 3;

-- 7
UPDATE movie
SET is_visible = false
WHERE id = 1;

-- 8
SELECT *
FROM orders
WHERE order_date = '2024-01-15';

-- 9
SELECT *
FROM orders o JOIN user u ON o.user_id = u.id
WHERE u.email = 'mario.rossi@example.com';

-- 10
SELECT SUM(total) as totale_globale
FROM orders;

-- 11
SELECT *
FROM movie
WHERE title LIKE 't%';

-- 12
SELECT *
FROM orders
WHERE id NOT IN (SELECT order_id
				 FROM movie_purchase_order);
                 
-- 13
SELECT *
FROM orders
WHERE id NOT IN (SELECT order_id
				 FROM movie_rental_order);
