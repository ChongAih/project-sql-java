-- create database stated in docker-compose
CREATE DATABASE inventory;

-- Create username and password for API authentication
--\c auth;

-- org/springframework/security/core/userdetails/jdbc/users.ddl
-- create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
-- create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
-- create unique index ix_auth_username on authorities (username,authority);

--CREATE TABLE users (
--  username VARCHAR(50) NOT NULL,
--  password VARCHAR(500) NOT NULL,
--  enabled BOOLEAN NOT NULL,
--  PRIMARY KEY (username)
--);
--
--CREATE TABLE authorities (
--  username VARCHAR(50) NOT NULL,
--  authority VARCHAR(50) NOT NULL,
--  FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
--  UNIQUE (username, authority)
--);
--
---- insert the encoded password (encoder must be the same used in coded
--INSERT INTO users (username, password, enabled)
--VALUES
--('user1', '$2a$10$W9jd1d6sVe6dNKxeYzTlZuuKovX5rHj36zHnrtkVOcyFI/jhc7ASW', true), --password1
--('user2', '$2a$10$OOHTa4Hm1fAnbprRTfi.teewvskNUO2jFpGEe7w.Xevhmi33OBG2K', true); --password2
--
--INSERT INTO authorities (username, authority)
--VALUES
--('user1', 'ROLE_USER'),
--('user2', 'ROLE_USER');