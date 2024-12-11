-- liquibase formatted sql

--changeset your-name:1
CREATE TABLE IF NOT EXISTS  role
(
    id   BIGINT not null,
    name VARCHAR(20),
    constraint pk_role primary key (id)
);


--changeset your-name:2
CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT not null,
    login    VARCHAR(20) not null,
    email    VARCHAR(50) not null,
    password VARCHAR(128) not null,
    constraint pk_users primary key (id),
    constraint uc_users_login unique (login),
    constraint uc_users_email unique (email)
);

--changeset your-name:3
CREATE TABLE IF NOT EXISTS user_roles
(
    role_id BIGINT not null,
    user_id BIGINT not null,
    constraint pk_user_roles primary key (role_id, user_id),
    constraint fk_userol_on_role foreign key (role_id) references role (id),
    constraint fk_userol_on_user foreign key (user_id) references users (id)
);

--changeset your-name:4
-- Add NOT NULL constraints based on User class validation
ALTER TABLE users ALTER COLUMN login SET NOT NULL;
ALTER TABLE users ALTER COLUMN email SET NOT NULL;
ALTER TABLE users ALTER COLUMN password SET NOT NULL;

--changeset your-name:5
CREATE SEQUENCE IF NOT EXISTS role_seq INCREMENT BY 50;

--changeset your-name:6
CREATE SEQUENCE IF NOT EXISTS users_seq INCREMENT BY 50;

--changeset your-name:7
INSERT INTO role (id, name)
VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER'),
    (3, 'ROLE_MODERATOR');

