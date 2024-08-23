create table regions
(
    id     bigserial primary key,
    region varchar(255)
);
insert into regions (region)
values ('Тюмень'),
       ('Тобольск');

create table districts
(
    id        bigserial primary key,
    id_region bigint  references regions (id),
    district  varchar(255) default ''
);
insert into districts (id_region, district)
values (1, 'Ленинский'),
       (1, 'Калининский');

create table streets
(
    id          bigserial primary key,
    id_district bigint  references districts (id),
    street      varchar(255) default ''
);
insert into streets (id_district, street)
values (1, 'Федюнинского'),
       (1, 'Мельникайте'),
       (1, 'Червишевский тракт');

create table manufactures
(
    id             bigserial primary key,
    title          varchar(255),
    firm          varchar(255),
    uri            varchar(255) default '/img/1.web'
);

insert into manufactures (title, firm, uri) values
    ('Монетка', '"ООО Элемент Трейд"', './img/1.jpg'),
    ('Сельский Дворик', '"ИП Армян А.А"', './img/2.jpg');                                               );

create table work_sites
(
    id             bigserial primary key,
    id_manufacture bigint references manufactures (id),
    id_street      bigint references streets (id),
    house          bigint,
    frame          varchar(255) default '',
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    comment          varchar(255)
);

insert into work_sites (id_manufacture, id_street, house, frame) values
    (
        1, 1, 34, ''
    );

