ALTER TABLE person
    ADD COLUMN role_id int;

ALTER TABLE person
    ADD CONSTRAINT fk_person_role
        FOREIGN KEY (role_id) REFERENCES role(id);
