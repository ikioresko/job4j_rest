create table message (
   id serial primary key not null,
   text varchar(2000),
   author_id INT references person(id)
);

create table room (
   id serial primary key not null,
   name varchar(50)
);

create table role (
   id serial primary key not null,
   name varchar(50)
);

create table person (
   id serial primary key not null,
   username varchar(50),
   password varchar(200),
   role_id INT not null references role(id)
);

insert into role (name) values ('ADMIN');
insert into role (name) values ('USER');

insert into person (username, password, role_id) values ('ivan', '123', 2);
insert into person (username, password, role_id) values ('Ilya', '321', 2);

insert into room (name) values ('general');

insert into message (text, author_id) values ('text', 1);

create table room_messages (
 room_id INT not null references room(id),
 messages_id INT not null references message(id)
);

insert into room_messages (room_id, messages_id) values (1, 1)