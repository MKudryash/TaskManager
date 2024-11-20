CREATE USER my_user WITH SUPERUSER PASSWORD 'my_password';
CREATE DATABASE taskmanager;
GRANT ALL PRIVILEGES ON DATABASE taskmanager TO my_user;
GRANT ALL ON ALL TABLES IN SCHEMA public TO my_user;



\c taskmanager;

-- users определение

-- Drop table

-- DROP TABLE users;

CREATE TABLE users (
	id serial4 NOT NULL,
	login varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);


-- testjava.task определение

-- Drop table

-- DROP TABLE testjava.task;

CREATE TABLE task (
	id serial4 NOT NULL,
	title varchar NOT NULL,
	description varchar NULL,
	duedate date NOT NULL,
	status varchar(20) NOT NULL,
	priority varchar(20) NOT NULL,
	user_id int4 NULL,
	author_id int4 NULL,
	CONSTRAINT task_pkey PRIMARY KEY (id),
	CONSTRAINT task_priority_check CHECK (((priority)::text = ANY ((ARRAY['low'::character varying, 'medium'::character varying, 'high'::character varying])::text[]))),
	CONSTRAINT task_status_check CHECK (((status)::text = ANY ((ARRAY['pending'::character varying, 'in progress'::character varying, 'completed'::character varying])::text[]))),
	CONSTRAINT task_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT task_users_fk FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- user_roles определение

-- Drop table

-- DROP TABLE user_roles;

CREATE TABLE user_roles (
	user_id int8 NOT NULL,
	"role" varchar(50) NOT NULL,
	CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role),
	CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


-- "comment" определение

-- Drop table

-- DROP TABLE "comment";

CREATE TABLE "comment" (
	id serial4 NOT NULL,
	"content" varchar NOT NULL,
	"date" date NOT NULL,
	user_id int4 NULL,
	task_id int4 NULL,
	CONSTRAINT comment_pkey PRIMARY KEY (id),
	CONSTRAINT comment_task_id_fkey FOREIGN KEY (task_id) REFERENCES task(id),
	CONSTRAINT comment_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id)
);
