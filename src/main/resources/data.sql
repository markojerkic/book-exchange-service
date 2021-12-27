insert into user_detail (email, first_name, last_name, password, username)
select 'markojerkic266@gmail.com',
        'Marko', 'Jerkić',
        '$2a$10$fGNcxTj9KTa3vCh88u6wPekXB99BCo7l3euBTuUdVZir72QFXggkq','marko'
where not exists(select * from user_detail where id = 1 and username = 'marko');

insert into user_roles (user_id, roles)
select 1, 'USER'
where not exists(select * from user_roles where user_id=1 and roles = 'USER');

insert into user_roles (user_id, roles)
select 1, 'ADMIN'
where not exists(select * from user_roles where user_id=1 and roles = 'ADMIN');