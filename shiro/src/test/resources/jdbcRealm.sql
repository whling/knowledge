create table if not exists shiro.roles_permissions
(
	id int auto_increment
		primary key,
	role_name varchar(32) null,
	permission varchar(64) null
);

create table if not exists shiro.user_roles
(
	id int auto_increment
		primary key,
	username varchar(64) null,
	role_name varchar(32) null
);

create table if not exists shiro.users
(
	id int auto_increment
		primary key,
	username varchar(64) null,
	password varchar(64) null
);


INSERT INTO shiro.users (id, username, password) VALUES (1, 'whling', '123');

INSERT INTO shiro.user_roles (id, username, role_name) VALUES (1, 'whling', 'administrator');
INSERT INTO shiro.user_roles (id, username, role_name) VALUES (2, 'whling', 'user');

INSERT INTO shiro.roles_permissions (id, role_name, permission) VALUES (1, 'administrator', 'user:delete');
INSERT INTO shiro.roles_permissions (id, role_name, permission) VALUES (2, 'user', 'user:query');