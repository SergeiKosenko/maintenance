create table regiones
(
    id     bigserial primary key,
    title varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);
insert into regiones (title)
values ('Тюмень'),
       ('Екатеринбург'),
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
       (3, 'Тобольский'),
       (3, 'Затобольский'),
       (1, 'Калининский'),
       (2, 'Байкаловский'),
       (2, 'Талицкий');

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

create table firm
(
    id             bigserial primary key,
    title          varchar(255)
);

create table manufactures
(
    id             bigserial primary key,
    title          varchar(255),
    uri            varchar(255) default '/img/1.web',
    id_firm        bigint  references firm (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into firm (title) values
                                          ('ООО "Элемент-Трейд"'),
                                          ('ООО "Мясной Мир"');


insert into manufactures (title, uri, id_firm) values
                                                   ('Монетка', './img/1.jpg', 1),
                                                   ('Сельский Дворик', './img/2.jpg', 2);

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
    id_street       bigint references streets (id),
    house           varchar(255),
    frame           varchar(255) default '',
    id_manufacture  bigint references manufactures (id),
    id_installation bigint references installations (id),
    at_work         boolean      default false,
    done            boolean      default false,
    no_done         boolean      default false,
    user_at_work    varchar(255) default '',
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

-- insert into work_sites (id_street, house, frame, id_manufacture, id_installation) values
--     ( 1, '34', '', 1, 1 ),
--     ( 2, '45', 'Корпус 2', 2, 2 );

