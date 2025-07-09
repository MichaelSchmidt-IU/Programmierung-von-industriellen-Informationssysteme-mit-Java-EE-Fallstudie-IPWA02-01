ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'DeinNeuesPasswort';
FLUSH PRIVILEGES;
NEU


-- 1. Neue Datenbank erstellen
CREATE DATABASE likeherotozero CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. Benutzer erstellen
CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'passwort123';

-- 3. Rechte zuweisen
GRANT ALL PRIVILEGES ON likeherotozero.* TO 'springuser'@'localhost';

-- 4. Rechte anwenden
FLUSH PRIVILEGES;
