create table addresses
(
    id         uuid        default uuidv7() primary key,
    address    varchar(100)              not null,
    address2   varchar(100),
    barangay   varchar(50)               not null,
    city       varchar(50)               not null,
    province   varchar(50)               not null,
    region     varchar(50)               not null,

    created_at timestamptz default now() not null
);

create table persons
(
    id          uuid        default uuidv7() primary key,
    first_name  varchar(100)              not null,
    middle_name varchar(100),
    last_name   varchar(100)              not null,
    suffix      varchar(8),

    created_at  timestamptz default now() not null,

    address_id  uuid                      not null,
    constraint fk_persons_address_id foreign key (address_id) references addresses (id)
);

create sequence if not exists seq_students_student_number;

create table students
(
    person_id      uuid primary key,
    student_number varchar(20)               not null,
    created_at     timestamptz default now() not null,

    constraint fk_students_person_id foreign key (person_id) references persons (id),
    constraint uq_students_student_number unique (student_number)
);

create table colleges
(
    id         int generated always as identity primary key,
    code       varchar(20)               not null,
    name       varchar(100)              not null,
    address_id uuid                      not null,
    created_at timestamptz default now() not null,

    constraint uq_colleges_code unique (code),
    constraint fk_colleges_address_id foreign key (address_id) references addresses (id)
);

create table programs
(
    id         int generated always as identity primary key,
    code       varchar(20)               not null,
    name       varchar(100)              not null,
    created_at timestamptz default now() not null,

    constraint uq_programs_code unique (code),
    constraint uq_programs_name unique (name)
);

create table courses
(
    id         int generated always as identity primary key,
    code       varchar(20)               not null,
    name       varchar(200)              not null,
    created_at timestamptz default now() not null,

    constraint uq_courses_code unique (code)
);

create table program_curriculum
(
    program_id     int                       not null,
    course_id      int                       not null,
    suggested_term smallint                  not null,
    created_at     timestamptz default now() not null,

    constraint pk_program_courses_junction_program_course_term_pair primary key (program_id, course_id),
    constraint fk_program_courses_junction_program_id foreign key (program_id) references programs (id),
    constraint fk_program_courses_junction_course_id foreign key (course_id) references courses (id)
);