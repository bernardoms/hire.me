DROP TABLE URL IF EXISTS;
create table URL
(
   id integer not null,
   originalURL varchar(255) not null,
   alias varchar(255) not null,
   views long not null,
   primary key(id)
);