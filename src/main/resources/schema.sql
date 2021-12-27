alter table book
    add constraint book_author_id_fk
        foreign key (id) references author
            on delete cascade;
alter table advert
    add constraint advert_book_id_fk
        foreign key (id) references book
            on delete cascade;
alter table advert
    add constraint advert_user_id_fk
        foreign key (id) references user_detail
            on delete cascade;
alter table image
    add constraint image_author_id_fk
        foreign key (author_id) references author
            on delete cascade;
alter table image
    add constraint image_book_id_fk
        foreign key (book_id) references book
            on delete cascade;
alter table image
    add constraint image_advert_id_fk
        foreign key (advert_id) references advert
            on delete cascade;
alter table review
    add constraint review_author_id_fk
        foreign key (id) references author
            on delete cascade;
alter table review
    add constraint review_book_id_fk
        foreign key (id) references book
            on delete cascade;
alter table review
    add constraint review_user_id_fk
        foreign key (id) references user_detail
            on delete cascade;
alter table user_roles
    add constraint user_role_user_id_fk
        foreign key (user_id) references user_detail
            on delete cascade;
alter table author_wrote_genre
    add constraint author_genre_id_fk
        foreign key (author_id) references author
            on delete cascade;
alter table author_wrote_genre
    add constraint genre_author_id_fk
        foreign key (genre_id) references genre
            on delete cascade;
alter table book_is_in_genre
    add constraint book_genre_id_fk
        foreign key (book_id) references book
            on delete cascade;
alter table book_is_in_genre
    add constraint genre_book_id_fk
        foreign key (genre_id) references genre
            on delete cascade;