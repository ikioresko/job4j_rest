create table passports (
   id serial primary key not null,
   serial INT,
   number INT,
   exp_date TIMESTAMP
);

insert into passports (serial, number, exp_date) values (4512, 000001, current_date - interval '1 month');
insert into passports (serial, number, exp_date) values (4512, 000002, current_date + interval '2 month');
insert into passports (serial, number, exp_date) values (4512, 000003, current_date + interval '4 month');
insert into passports (serial, number, exp_date) values (4513, 000004, current_date + interval '2 month');