-- liquibase formatted sql

-- changeset krasnov:1
CREATE INDEX id_student_name ON student (name);
