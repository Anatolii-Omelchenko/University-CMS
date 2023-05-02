create table groups
(
    group_id       SERIAL PRIMARY KEY,
    education_form varchar(32) not null,
    group_name     varchar(5)  not null unique
);

create table lecture_rooms
(
    room_id   SERIAL PRIMARY KEY,
    capacity  integer    not null,
    room_name varchar(5) not null unique
);

create table lectures
(
    lecture_id     SERIAL PRIMARY KEY,
    date           date        not null,
    lecture_number varchar(32) not null,
    group_ref      bigint,
    room_ref       bigint,
    subject_ref    bigint,
    teacher_ref    bigint
);

create table persons
(
    p_type          varchar(31)  not null,
    person_id       SERIAL PRIMARY KEY,
    birthdate       date         not null,
    email           varchar(100) unique,
    gender          varchar(32)  not null,
    name            varchar(100) not null,
    phone           varchar(32) unique,
    payment_form    varchar(32),
    academic_degree varchar(32),
    experience      integer,
    group_ref       bigint,
    password        varchar(100) not null
);

create table subjects
(
    subject_id   SERIAL PRIMARY KEY,
    subject_name varchar(64) not null unique,
    description varchar(255)
);

create table user_role
(
    person_id bigint not null,
    roles     varchar(255)
);

alter table if exists lectures
    add constraint FKeg90mw3wpsf1j9rvw9sktolgq
    foreign key (group_ref)
    references groups
    ON DELETE SET NULL;

alter table if exists lectures
    add constraint FK9auu5t4ifls2isux01fm30fw3
    foreign key (room_ref)
    references lecture_rooms
    ON DELETE SET NULL;

alter table if exists lectures
    add constraint FK308f4c6nqfix10s0ovgwptklo
    foreign key (subject_ref)
    references subjects
    ON DELETE SET NULL;

alter table if exists lectures
    add constraint FKagb0elo3fs3dldwx6hot0y53n
    foreign key (teacher_ref)
    references persons
    ON DELETE SET NULL;

alter table if exists persons
    add constraint FKqocwrjrnb7bypdpplco8s1aks
    foreign key (group_ref)
    references groups;

alter table if exists user_role
    add constraint FKsgy8mguf4fwy2usp3t6qvehcg
    foreign key (person_id)
    references persons;