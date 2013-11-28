drop table ac_user;

create table ac_user (
	id number(19,0),
	loginname varchar2(64) not null unique,
	name varchar2(64) not null,
	password varchar2(255) not null,
	email varchar2(128) not null,
	salt varchar2(64) not null,
	roles varchar2(255) not null,
	registerdate date not null,
	primary key (id)
);

create sequence ac_seq_user start with 1 increment by 1;