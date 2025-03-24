create table roles
(
    id            bigserial primary key,
    name          varchar(255) not null,
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
);

create table users
(
    id             bigserial primary key,
    username       varchar(255),
    password       varchar(255) not null,
    first_name     varchar(255) ,
    last_name      varchar(255) ,
    email          varchar(255) not null,
    phone          varchar(255) ,
    active         boolean default false,
    region_id      bigint  references regiones (id),
    created_at     timestamp default current_timestamp,
    updated_at     timestamp default current_timestamp
);

create table users_roles
(
    user_id        bigint not null references users (id),
    role_id       bigint not null references roles (id),
    created_at     timestamp default current_timestamp,
    updated_at     timestamp default current_timestamp,
    primary key (user_id, role_id)
);

insert into roles (name) values
                             ('ROLE_USER'),
                             ('ROLE_ADMIN'),
                             ('ROLE_SUPER_ADMIN');

insert into users (username, password, last_name, first_name, email, phone, active, region_id) values
    ( 'superAdmin', '$2a$10$A8BL.Fuh0JiHcpapcy0hLeIYT82hyJuwwZhgSPxRABoKP8prowdsq', 'Иванов', 'Иван', 'superadmin@mail.ru', '8 (999)-989-77-22', true, 1 ),
    ( 'admin', '$2a$10$A8BL.Fuh0JiHcpapcy0hLeIYT82hyJuwwZhgSPxRABoKP8prowdsq', 'Петров', 'Петр', 'admin@mail.ru', '8 (999)-989-77-33', true, 1 ),
    ( 'user', '$2a$10$A8BL.Fuh0JiHcpapcy0hLeIYT82hyJuwwZhgSPxRABoKP8prowdsq', 'Петров', 'Петр', 'admin@mail.ru', '8 (999)-989-77-33', true, 1 ),
    ( 'sergonian', '$2a$10$A8BL.Fuh0JiHcpapcy0hLeIYT82hyJuwwZhgSPxRABoKP8prowdsq', 'Сидоров', 'Василий', 'user@mail.ru', '8 (999)-989-77-11', true, 2 ),
    ( 'vasya', '$2a$10$A8BL.Fuh0JiHcpapcy0hLeIYT82hyJuwwZhgSPxRABoKP8prowdsq', 'Сидоров', 'Василий', 'user@mail.ru', '8 (999)-989-77-11', true, 3 );

insert into users_roles (user_id, role_id) values
                             (1, 3),
                             (2, 2),
                             (3, 1),
                             (4, 2),
                             (5, 2);


create table comments
(
    id             bigserial primary key,
    comment          varchar(255) default 'Нет комментариев',
    id_work_site   int references work_sites (id),
    user_id        int references users (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

-- insert into comments (comment, id_work_site, user_id, created_at, updated_at) values
--     (
--         'Мой комментарий', 1, 1, current_date, current_time
--     );