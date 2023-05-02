
    create table groups (
       group_id bigserial not null,
        education_form varchar(32) not null,
        group_name varchar(5) not null,
        primary key (group_id)
    );

    create table lecture_rooms (
       room_id bigserial not null,
        capacity integer not null,
        room_name varchar(5) not null,
        primary key (room_id)
    );

    create table lectures (
       lecture_id bigserial not null,
        date date not null,
        lecture_number varchar(32) not null,
        group_ref bigint,
        room_ref bigint,
        subject_ref bigint,
        teacher_ref bigint,
        primary key (lecture_id)
    );

    create table persons (
       p_type varchar(31) not null,
        person_id bigserial not null,
        birthdate date not null,
        email varchar(100),
        gender varchar(32) not null,
        name varchar(100) not null,
        password varchar(255) not null,
        phone varchar(32),
        payment_form varchar(32),
        academic_degree varchar(32),
        experience integer default 0,
        group_ref bigint,
        primary key (person_id)
    );

    create table subjects (
       subject_id bigserial not null,
        subject_name varchar(64) not null,
        primary key (subject_id)
    );

    create table user_role (
       person_id bigint not null,
        roles varchar(255)
    );

    alter table if exists groups 
       add constraint UK_7o859iyhxd19rv4hywgdvu2v4 unique (group_name);

    alter table if exists lecture_rooms 
       add constraint UK_mixrlqj9f12653508053ysu75 unique (room_name);

    alter table if exists persons 
       add constraint UK_1x5aosta48fbss4d5b3kuu0rd unique (email);

    alter table if exists persons 
       add constraint UK_jffg8yqa27hkgfnkdimfv4i4m unique (phone);

    alter table if exists subjects 
       add constraint UK_gaix2pna1ulbxhdl4kbq9yglt unique (subject_name);

    alter table if exists lectures 
       add constraint FKeg90mw3wpsf1j9rvw9sktolgq 
       foreign key (group_ref) 
       references groups;

    alter table if exists lectures 
       add constraint FK9auu5t4ifls2isux01fm30fw3 
       foreign key (room_ref) 
       references lecture_rooms;

    alter table if exists lectures 
       add constraint FK308f4c6nqfix10s0ovgwptklo 
       foreign key (subject_ref) 
       references subjects;

    alter table if exists lectures 
       add constraint FKagb0elo3fs3dldwx6hot0y53n 
       foreign key (teacher_ref) 
       references persons;

    alter table if exists persons 
       add constraint FKqocwrjrnb7bypdpplco8s1aks 
       foreign key (group_ref) 
       references groups;

    alter table if exists user_role 
       add constraint FKsgy8mguf4fwy2usp3t6qvehcg 
       foreign key (person_id) 
       references persons;
