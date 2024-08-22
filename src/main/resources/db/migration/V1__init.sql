create table region
(
    id     bigserial primary key,
    region varchar(255)
);
insert into region (region)
values ('Тюмень'),
       ('Тобольск');

create table district
(
    id        bigserial primary key,
    id_region int          default 1,
    district  varchar(255) default ''
);
insert into district (id_region, district)
values (1, 'Ленинский'),
       (1, 'Калининский');

create table streets
(
    id          bigserial primary key,
    id_district int          default 1,
    street      varchar(255) default ''
);
insert into streets (id_district, street)
values (1, 'Федюнинского'),
       (1, 'Мельникайте'),
       (1, 'Червишевский тракт');

create table work_site
(
    id             bigserial primary key,
    id_street      int,
    id_manufacture int,
    house          int,
    frame          varchar(10) default ''
);
insert into work_site (id_street, id_manufacture, house, frame)
values (1, 1, 108, 'Г'),
       (2, 1, 122, ''),
       (3, 1, 23, ''),
       (3, 2, 23, '');

