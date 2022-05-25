--liquibase formatted sql

--changeset reinis:1

CREATE TABLE airport
(
    airport VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    city    VARCHAR(255) NOT NULL,
    CONSTRAINT airport_id PRIMARY KEY (airport)
);