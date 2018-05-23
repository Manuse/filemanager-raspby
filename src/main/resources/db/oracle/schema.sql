create table if not exists authority (
id number(1) primary key,
name varchar2(50) unique);

CREATE SEQUENCE authority_id_seq

create or replace trigger trg_auth_id before insert on authority for each row
begin
select authority_id_seq.nextval
into :new.id
from dual;
end;
/

create table users (
id number(2) primary key,
login varchar2(50) NOT NULL UNIQUE,
password varchar2(70),
enabled number(1));

CREATE SEQUENCE users_id_seq_seq;

create or replace trigger trg_users_id before insert on users for each row
begin
select users_id_seq.nextval
into :new.id
from dual;
end;
/

create table users_authority (
id_user number(2),
id_authority number(1));

create table token (
series varchar2(50) primary key,
value varchar2(70),
dates timestamp,
ip_address varchar2(50),
user_agent varchar2(200),
user_login varchar2(50));

create table access_path(
id number(4) primary key,
user_id number(2) not null,
device varchar2(45) not null,
path varchar2(255) not null,
permissions number(1) not null,
space_used number(11,3) default 0,
space_limit number(11,3) default 0,
CONSTRAINT fk_access_path_users1
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE cascade
);

CREATE SEQUENCE access_path_id_seq;


create or replace trigger trg_access_path_id before insert on access_path for each row
begin
select access_path_id_seq.nextval
into :new.id
from dual;
end;
/