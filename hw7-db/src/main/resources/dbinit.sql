create sequence users_id_seq start 1;
create sequence bonuses_id_seq start 1;
create table users_tab (id integer primary key default nextval('users_id_seq'), login varchar(255), password varchar(255), nickname varchar(255));
create table bonuses_tab (id integer primary key default nextval('bonuses_id_seq'), owner_login varchar(255), amount int);