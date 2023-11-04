-- liquibase formatted sql

-- changeset ascherbakova:1
CREATE INDEX student_name_index ON student (name);

-- changeset ascherbakova:2

CREATE INDEX  faculty_name_color_index ON faculty (name, color);
