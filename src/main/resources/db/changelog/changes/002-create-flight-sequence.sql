--liquibase formatted sql

--changeset reinis:2

CREATE SEQUENCE IF NOT EXISTS flight_sequence START WITH 1 INCREMENT BY 1;