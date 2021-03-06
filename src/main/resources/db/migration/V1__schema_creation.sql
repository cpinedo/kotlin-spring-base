CREATE SCHEMA IF NOT EXISTS base
    AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS base.roles
(
    id uuid NOT NULL,
    name text,
    role integer,
    CONSTRAINT roles_pkey PRIMARY KEY (id));

ALTER TABLE IF EXISTS base.roles
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS base.users
(
    id uuid NOT NULL,
    email text,
    password text,
    username text,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS base.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS base.user_roles
(
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
        REFERENCES base.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
        REFERENCES base.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS base.user_roles
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS base.refresh_token
(
    id uuid NOT NULL,
    expiry_date timestamp without time zone NOT NULL,
    session_expiry_date timestamp without time zone NOT NULL,
    token character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id uuid,
    CONSTRAINT refresh_token_pkey PRIMARY KEY (id),
    CONSTRAINT uk_r4k4edos30bx9neoq81mdvwph UNIQUE (token),
    CONSTRAINT fkjtx87i0jvq2svedphegvdwcuy FOREIGN KEY (user_id)
        REFERENCES base.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS base.refresh_token
    OWNER to postgres;

INSERT INTO base.roles(
    id, name, role)
VALUES ('b27944d2-8dc8-11ec-b909-0242ac120002', 'user', 0);

INSERT INTO base.roles(
    id, name, role)
VALUES ('daad142e-8dc8-11ec-b909-0242ac120002', 'admin', 1);