-- liquibase formatted sql

-- changeset krasnov:2
CREATE INDEX id_faculty_name_color ON faculty (name, color);