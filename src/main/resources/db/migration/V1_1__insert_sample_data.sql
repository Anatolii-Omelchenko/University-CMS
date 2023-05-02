INSERT INTO groups (education_form, group_name)
VALUES ('STATIONARY', 'GR-01'),
       ('STATIONARY', 'GR-02'),
       ('STATIONARY', 'GR-03'),
       ('STATIONARY', 'GR-04'),
       ('STATIONARY', 'GR-05'),
       ('PART_TIME', 'GR-06'),
       ('PART_TIME', 'GR-07'),
       ('PART_TIME', 'GR-08'),
       ('PART_TIME', 'GR-09'),
       ('PART_TIME', 'GR-10');

INSERT INTO subjects (subject_name, description)
VALUES ('Music', 'Music course description...'),
       ('Biology', 'Biology course description...'),
       ('Political science', 'Political course description...'),
       ('Informatics', 'Informatics course description...'),
       ('Maths', 'Maths course description...'),
       ('Art lessons', 'Art course description...'),
       ('Economy', 'Economy course description...'),
       ('Philosophy', 'Philosophy course description...'),
       ('Culturology', 'Culturology course description...'),
       ('Spiritualism', 'Spiritualism course description...');

INSERT INTO lecture_rooms (capacity, room_name)
VALUES (30, 'RM-01'),
       (50, 'RM-02'),
       (40, 'RM-03'),
       (40, 'RM-04'),
       (30, 'RM-05');

INSERT INTO persons (p_type, birthdate, email, gender, name, phone, payment_form, group_ref, password)
VALUES ('STD', '27.09.1991', 'test3@gmail.com', 'MALE', 'Omelchenko Anatolii', '0953431322', 'CONTRACT', 1,
        '$2a$12$4z.FwNRWjXSPx1QJx1SHJ.fEUS/RaW.4tKZ/fLi11nAFOgWM9Wqai'),
       ('STD', '07.11.1994', 'test4@gmail.com', 'FEMALE', 'Bereza Olena', '0953499022', 'OTHER', 1,
        '$2a$12$4z.FwNRWjXSPx1QJx1SHJ.fEUS/RaW.4tKZ/fLi11nAFOgWM9Wqai'),
       ('STD', '14.02.1990', 'test5@gmail.com', 'MALE', 'White Denis', '0953488322', 'CONTRACT', 1, '1234'),
       ('STD', '22.09.2000', 'test6@gmail.com', 'FEMALE', 'Brown Josephine', '0993341322', 'BUDGET', 1, '1234'),
       ('STD', '01.05.1999', 'test7@gmail.com', 'MALE', 'Longstreet Marvin', '0973476422', 'CONTRACT', 1, '1234');

INSERT INTO persons (p_type, birthdate, email, gender, name, phone, academic_degree, experience, password)
VALUES ('TCH', '27.09.1991', 'teacher3@gmail.com', 'MALE', 'Jonson Jons', '0953123652', 'TEACHER', 1, '1234'),
       ('TCH', '07.11.1994', 'teacher4@gmail.com', 'FEMALE', 'Ms. Claus', '0953431432', 'DOCENT', 10, '1234'),
       ('TCH', '14.02.1990', 'teacher5@gmail.com', 'MALE', 'White Ben', '0953412522', 'TEACHER', 4, '1234'),
       ('TCH', '22.09.2000', 'teacher6@gmail.com', 'FEMALE', 'Brown Marley', '0993187322', 'TEACHER', 4, '1234'),
       ('TCH', '01.05.1999', 'teacher7@gmail.com', 'MALE', 'Darkwood Stephen', '0973419422', 'PROFESSOR', 25, '1234');

INSERT INTO lectures (date, lecture_number, group_ref, room_ref, subject_ref, teacher_ref)
VALUES ('20.02.2023', 'FIRST', 1, 3, 1, 10),
       ('20.02.2023', 'SECOND', 1, 3, 2, 6),
       ('20.02.2023', 'THIRD', 1, 3, 3, 7),
       ('20.02.2023', 'FOURTH', 1, 3, 4, 10),
       ('20.02.2023', 'FIFTH', 1, 3, 5, 7)
;

INSERT INTO user_role (person_id, roles)
VALUES (1, 'ADMIN');