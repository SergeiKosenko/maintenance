
create table years (
                        id             bigserial primary key,
                        yeare           int default 2024
);
insert into years (yeare)
values (2024),
       (2025),
       (2026);


create table monthes (
                        id             bigserial primary key,
                        monthe          varchar(255)
);
insert into monthes (monthe)
values ('Январь'),
       ('Февраль'),
       ('Март'),
       ('Апрель'),
       ('Май'),
       ('Июнь'),
       ('Июль'),
       ('Август'),
       ('Сентябрь'),
       ('Октябрь'),
       ('Ноябрь'),
       ('Декабрь');

create table periods (
                        id             bigserial primary key,
                        id_year        int,
                        id_month       int,
                        id_work_site   int,
                        at_work        boolean default false,
                        done           boolean default false
);
insert into periods (id_year, id_month, id_work_site, at_work, done)
values (1, 1, 2, false,false),
       (1, 2, 2, false,false),
       (1, 3, 1, false,false),
       (1, 3, 3, false,false);