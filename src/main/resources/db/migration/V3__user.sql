create table roles
(
    id             bigserial primary key,
    role          varchar(255)
);

insert into roles (role) values
                             ('User'),
                             ('Admin'),
                             ('SuperAdmin');

create table users
(
    id             bigserial primary key,
    login          varchar(255) ,
    password       varchar(255) ,
    first_name     varchar(255) ,
    last_name      varchar(255) ,
    email          varchar(255) ,
    phone          varchar(255) ,
    active         boolean default false,
    id_role        bigint references roles (id),
    id_region      bigint
);

insert into users (login, password, last_name, first_name, email, phone, active, id_role, id_region) values
    ( 'superAdmin', '123', 'Иванов', 'Иван', 'superadmin@mail.ru', '8 (999)-989-77-22', true, 3, 1 ),
    ( 'admin', '123', 'Петров', 'Петр', 'admin@mail.ru', '8 (999)-989-77-33', true, 2, 1 ),
    ( 'user', '123', 'Сидоров', 'Василий', 'user@mail.ru', '8 (999)-989-77-11', true, 1, 1 );

create table comments
(
    id             bigserial primary key,
    comment          varchar(255) default 'Нет комментариев',
    id_work_site   int references work_sites (id),
    id_user        int references users (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into comments (comment, id_work_site, id_user, created_at, updated_at) values
    (
        'Мой комментарий', 1, 1, current_date, current_time
    );