create table regiones
(
    id     bigserial primary key,
    title varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);
insert into regiones (title)
values ('Тюмень'),
       ('Тобольск');

create table districts
(
    id        bigserial primary key,
    region_id bigint  references regiones (id),
    title  varchar(255) default '',
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);
insert into districts (region_id, title)
values (1, 'Ленинский'),
       (2, 'Тобольский'),
       (2, 'Затобольский'),
       (1, 'Калининский');

create table streets
(
    id          bigserial primary key,
    id_district bigint  references districts (id),
    title      varchar(255) default '',
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);
insert into streets (id_district, title)
values (1, 'Федюнинского'),
       (4, 'Лермонтова'),
       (3, 'Кликайте'),
       (1, 'Мельникайте'),
       (2, 'Урицкого'),
       (3, 'Ломоносова'),
       (1, 'Червишевский тракт');

create table manufactures
(
    id             bigserial primary key,
    title          varchar(255),
    uri            varchar(255) default '/img/1.web',
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into manufactures (title, uri) values
    ('Монетка', './img/1.jpg'),
    ('Сельский Дворик', './img/2.jpg');

create table firm
(
    id             bigserial primary key,
    manufacture_id bigint references manufactures (id),
    title          varchar(255)
);

insert into firm (manufacture_id, title) values
                                          (1, 'ООО "Элемент-Трейд"'),
                                          (2, '"ИП Армян А.А."'),
                                          (2, '"ИП Казарян А.А."'),
                                          (2, '"ИП Симанян А.А."'),
                                          (2, '"ИП Новиков А.А."');

create table installations
(
    id             bigserial primary key,
    title          varchar(255)
);

insert into installations (title) values
                                                ('Встроенный холод'),
                                                ('Выносной холод');

create table work_sites
(
    id              bigserial primary key,
    id_manufacture  bigint references manufactures (id),
    id_street       bigint references streets (id),
    house           bigint,
    frame           varchar(255) default '',
    id_installation bigint references installations (id),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp,
    comment         varchar(255)
);

insert into work_sites (id_manufacture, id_street, house, frame, id_installation) values
    ( 1, 1, 34, '', 1 ),
    ( 2, 2, 45, 'Корпус 2', 2 );

