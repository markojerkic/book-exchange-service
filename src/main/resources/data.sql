insert into author (first_name, last_name, year_of_birth, year_of_death)
select 'Ivo', 'Andrić', 1892, 1975
where not exists(select * from author where id = 1);
insert into author (first_name, last_name, year_of_birth, year_of_death)
select 'August', 'Šenoa', 1838, 1881
where not exists(select * from author where id = 2);
insert into author (first_name, last_name, year_of_birth, year_of_death)
select 'Ivan', 'Raos', 1921, 1987
where not exists(select * from author where id = 3);
insert into author (first_name, last_name, year_of_birth, year_of_death)
select 'J.K.', 'Rowlling', 1965, 2050
where not exists(select * from author where id = 4);

insert into genre (name)
select 'Kriminalistički romani'
where not exists(select * from genre where id = 1);
insert into genre (name)
select 'Romantični romani'
where not exists(select * from genre where id = 2);
insert into genre (name)
select 'Poezija romantike'
where not exists(select * from genre where id = 3);
insert into genre (name)
select 'Hrvatska moderna'
where not exists(select * from genre where id = 4);

insert into book (isbn, title)
select '978-1-60309-025-4', 'Na Drini ćuprija'
where not exists(select * from book where id = 1);
insert into book (isbn, title)
select '978-1-60309-025-3', 'Čuvaj se senjske ruke'
where not exists(select * from book where id = 2);
insert into book (isbn, title)
select '978-1-60309-025-2', 'Prosjaci i sinovi'
where not exists(select * from book where id = 3);
insert into book (isbn, title)
select '978-1-60309-025-1', 'Harry Potter'
where not exists(select * from book where id = 4);

insert into author_wrote_book (author_id, book_id)
select 1, 1
where not exists(select * from author_wrote_book where author_id = 1 and book_id = 1);
insert into author_wrote_book (author_id, book_id)
select 2, 2
where not exists(select * from author_wrote_book where author_id = 2 and book_id = 2);
insert into author_wrote_book (author_id, book_id)
select 3, 3
where not exists(select * from author_wrote_book where author_id = 3 and book_id = 3);
insert into author_wrote_book (author_id, book_id)
select 4, 4
where not exists(select * from author_wrote_book where author_id = 4 and book_id = 4);

insert into user_detail (email, first_name, last_name, password, username)
select 'markojerkic266@gmail.com',
        'Marko', 'Jerkić',
        '$2a$10$fGNcxTj9KTa3vCh88u6wPekXB99BCo7l3euBTuUdVZir72QFXggkq','marko'
where not exists(select * from user_detail where id = 1);

insert into advert (advert_status, advert_type, description,
                    last_modified, price, title, transaction_type,
                    advertised_book_id, user_created_id)
select 0, 1, 'Jako dobro očuvano. Super štivo. Preporučam svima',
        now(), 50,
        'Jako dobro očuvana "Na drini ćuprija"', 0, 1, 1
where not exists(select * from advert where id = 1);

