/*
 DROP USER 'TASC'@'localhost';
*/
CREATE USER 'TASC'@'localhost' IDENTIFIED BY '?';

GRANT ALL PRIVILEGES ON `identity`.* TO 'TASC'@'localhost';
GRANT ALL PRIVILEGES ON `book`.* TO 'TASC'@'localhost';
GRANT ALL PRIVILEGES ON `order`.* TO 'TASC'@'localhost';
GRANT ALL PRIVILEGES ON `payment`.* TO 'TASC'@'localhost';
