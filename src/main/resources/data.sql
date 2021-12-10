insert into author (first_name, last_name, year_of_birth, year_of_death)
    values ('Ivo', 'Andrić', 1892, 1975),
           ('August', 'Šenoa', 1838, 1881),
           ('Ivan', 'Raos', 1921, 1987),
           ('J.K.', 'Rowlling', 1965, 2050);

insert into genre (name)
    values ('Kriminalistički romani'),
           ('Romantični romani'),
           ('Poezija romantike'),
           ('Domaća moderna književnost');

insert into book (isbn, title)
    values ('978-1-60309-025-4', 'Na Drini ćuprija'),
           ('978-1-60309-025-3', 'Čuvaj se senjske ruke'),
           ('978-1-60309-025-2', 'Prosijaci i sinovi'),
           ('978-1-60309-025-1', 'Harry Potter');

insert into author_wrote_book (author_id, book_id)
    values (1, 1),
           (2, 2),
           (3, 3),
           (4, 4);

insert into user_detail (email, first_name, last_name, password, username)
    values ('markojerkic266@gmail.com',
            'Marko', 'Jerkić',
            '$2a$10$fGNcxTj9KTa3vCh88u6wPekXB99BCo7l3euBTuUdVZir72QFXggkq','marko');

INSERT INTO advert (advert_status, advert_type, description,
                           last_modified, price, title, transaction_type,
                           advertised_book_id, user_created_id)
                           VALUES (0, 1, 'Jako dobro očuvano. Super štivo. Preporučam svima',
                                   now(), 50,
                                   'Jako dobro očuvana "Na drini ćuprija"', 0, 1, 1);

