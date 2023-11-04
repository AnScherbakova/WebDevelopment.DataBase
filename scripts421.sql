ALTER TABLE student ADD  CONSTRAINT age CHECK ( age > 16 );
ALTER TABLE student ADD CONSTRAINT name UNIQUE (name);
ALTER  TABLE student ALTER COLUMN name SET NOT NULL;
ALTER TABLE faculty ADD CONSTRAINT un_name_color UNIQUE (name, color);
ALTER TABLE student ALTER COLUMN age SET default 20;