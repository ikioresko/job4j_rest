create table person (
   id serial primary key not null,
   login varchar(2000),
   password varchar(2000)
);

create table employee (
   id serial primary key not null,
   first_name varchar(50),
   last_name varchar(50),
   inn INT,
   hiring_date TIMESTAMP
);

insert into employee (first_name, last_name, inn, hiring_date)
values ('Semen', 'Vasiliev', 123456789, now());
insert into employee (first_name, last_name, inn, hiring_date)
values ('Vasiliy', 'Semenov', 987654321, now());

insert into person (login, password) values ('parsentev', '123');
insert into person (login, password) values ('ban', '123');
insert into person (login, password) values ('ivan', '123');

create table employee_person_set (
 employee_id INT not null references employee(id),
 person_set_id INT not null references person(id)
);

insert into employee_person_set (employee_id, person_set_id) values (1, 2);
insert into employee_person_set (employee_id, person_set_id) values (1, 3);
insert into employee_person_set (employee_id, person_set_id) values (2, 1);