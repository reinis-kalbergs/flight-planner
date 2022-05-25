--liquibase formatted sql

--changeset reinis:3

CREATE TABLE flight
(
    id             BIGINT PRIMARY KEY,
    from_id        VARCHAR(100) NOT NULL,
    to_id          VARCHAR(100) NOT NULL,
    carrier        VARCHAR(100) NOT NULL,
    departure_time timestamp    NOT NULL,
    arrival_time   timestamp    NOT NULL,
    CONSTRAINT flight_from_id_fkey FOREIGN KEY (from_id) REFERENCES airport (airport),
    CONSTRAINT flight_to_id_fkey FOREIGN KEY (to_id) REFERENCES airport (airport),
    UNIQUE (from_id, to_id, carrier, departure_time, arrival_time)
);

