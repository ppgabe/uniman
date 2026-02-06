create table psgc_data_q4_2025
(
    psgc_rev_1           char(10)     not null primary key,
    name                 varchar(100) not null,
    correspondence_code  char(9),
    geographic_level     varchar(10),
    city_class           varchar(10),
    urban_classification varchar(4),

    constraint uq_psgc_data_correspondence_code unique (correspondence_code)
)