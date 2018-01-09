create table if not exists authority (
id int(1) unsigned auto_increment primary key,
name varchar(50));

create table if not exists users (
id int(2) unsigned auto_increment primary key,
login varchar(50) NOT NULL UNIQUE,
password varchar(70),
enabled boolean,
space_used double(11,2) unsigned default 0,
space_limit double(11,2) unsigned default 0);

create table if not exists users_authority (
id_user int(2) unsigned,
id_authority int(1) unsigned);

create table if not exists token (
series varchar(50) primary key,
value varchar(70),
date timestamp,
ip_address varchar(50),
user_agent varchar(200),
user_login varchar(50));

create table if not exists access_path(
id int(4) unsigned auto_increment primary key,
user_id int(2) unsigned not null,
device varchar(45) not null,
path varchar(255) not null,
permissions int(1) unsigned not null,
INDEX fk_access_path_users1_idx (user_id ASC),
CONSTRAINT fk_access_path_users1
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE cascade
);
