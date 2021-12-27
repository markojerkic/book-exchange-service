create table author
(
    id            bigserial
        primary key,
    first_name    varchar(255),
    last_name     varchar(255),
    year_of_birth timestamp,
    year_of_death timestamp
);
create table book
(
    id             bigserial
        primary key,
    isbn           varchar(255)
        constraint uk_ehpdfjpu1jm3hijhj4mm0hx9h
            unique,
    title          varchar(255),
    book_author_id bigint
        constraint book_author
            references author
            on delete cascade
);
create table genre
(
    id          bigserial
        primary key,
    description varchar(255),
    name        varchar(255)
);
create table user_detail
(
    id         bigserial
        primary key,
    email      varchar(255)
        constraint uk_al4vy84pj6kshsqrsn9kc63sj
            unique,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    username   varchar(255)
        constraint uk_bw2k2v2ql8t36dw09nel1h62s
            unique
);

create table advert
(
    id                 bigserial
        primary key,
    advert_status      integer,
    advert_type        integer,
    description        varchar(255),
    last_modified      timestamp,
    price              real,
    title              varchar(255),
    transaction_type   integer,
    advertised_book_id bigint
        constraint fkswjgqvnu981r3oor7ntgjqtxh
            references book
            on delete cascade,
    user_created_id    bigint
        constraint fkpyieny6a3o8ewyrffeq86yspw
            references user_detail
            on delete cascade
);
create table image
(
    uuid                 uuid not null
        primary key,
    image_file_extension integer,
    image_order          integer,
    author_id            bigint
        constraint fk57ve9pf6jpb4rwm81ka5umlcv
            references advert
            on delete cascade,
    author               bigint
        constraint fkl5ufkogr4wbm9f7i28xudtod4
            references author
            on delete cascade,
    book_id              bigint
        constraint fk56boxkje8rys2n78amvgkk913
            references book
            on delete cascade
);
create table review
(
    id           bigserial
        primary key,
    description  varchar(255),
    review_grade integer,
    author_id    bigint
        constraint fklmvjvn4m5ctphmk5tbgnmyxrs
            references author
            on delete cascade,
    book_id      bigint
        constraint fk70yrt09r4r54tcgkrwbeqenbs
            references book
            on delete cascade,
    user_id      bigint
        constraint fk1jgiefym5e4t6cnn1owh1jdrs
            references user_detail
            on delete cascade
);
create table user_roles
(
    user_id bigint not null
        constraint fk411xjm896kl8bwxu1wpsay56j
            references user_detail,
    roles   varchar(255)
        constraint uk_gbm9yw7rkmdfyiauag3ft9d4f
            unique
);
create table author_wrote_genre
(
    author_id bigint not null
        constraint fk807v59spwtumawxu7gaj9f0eg
            references author
            on delete cascade,
    genre_id  bigint not null
        constraint fkcjp1f5jsds34la2snpoiml68i
            references genre
            on delete cascade
);
create table book_is_in_genre
(
    book_id  bigint not null
        constraint fknqh119bboalr4mboa91sfecm9
            references book
            on delete cascade,
    genre_id bigint not null
        constraint fk1rauql8i03hnxuwhg90793aqu
            references genre
            on delete cascade
);