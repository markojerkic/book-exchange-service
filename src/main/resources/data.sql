insert into author (id, first_name, last_name, year_of_birth, year_of_death)
    values (1, 'Ivo', 'Andrić', 1892, 1975),
           (2, 'August', 'Šenoa', 1838, 1881),
           (3, 'Ivan', 'Raos', 1921, 1987);

insert into genre (id, name)
    values (1, 'Kriminalistički romani'),
           (2, 'Romantični romani'),
           (3, 'Poezija romantike'),
           (4, 'Domaća moderna književnost');

insert into book (id, isbn, title)
    values (1, '978-1-60309-025-4', 'Na Drini ćuprija'),
           (2, '978-1-60309-025-3', 'Čuvaj se senjske ruke'),
           (3, '978-1-60309-025-2', 'Prosijaci i sinovi'),
           (4, '978-1-60309-025-1', 'Harry Potter');

insert into user_detail (id, email, first_name, last_name, password, username)
    values (1,'markojerkic266@gmail.com',
            'Marko', 'Jerkić',
            '$2a$10$fGNcxTj9KTa3vCh88u6wPekXB99BCo7l3euBTuUdVZir72QFXggkq','marko')
