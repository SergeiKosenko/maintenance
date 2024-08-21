create table streets (
    id bigserial primary key,
    title varchar(255),
    house int,
    frame varchar(255) default '');
insert into streets (title, house, frame) values
('Федюнинского', 108, 'Г'),
('Мельникайте', 122, '');

