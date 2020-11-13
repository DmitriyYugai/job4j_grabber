drop table if exists posts;

create table posts(
	id serial primary key,
	name varchar(255),
	description text,
	link text unique,
	created timestamp
);

--select * from posts;