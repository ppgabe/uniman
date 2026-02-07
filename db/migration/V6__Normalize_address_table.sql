alter table addresses
    rename column barangay to barangay_id;

alter table addresses
    rename column city to municipality_id;

alter table addresses
    rename column province to province_id;

alter table addresses
    rename column region to region_code;

alter table addresses
    alter column barangay_id type int USING barangay_id::integer;

alter table addresses
    alter column municipality_id type int USING municipality_id::integer;

alter table addresses
    alter column province_id type int USING province_id::integer;

alter table addresses
    alter column region_code type char(2);

alter table addresses
    add constraint fk_addresses_barangay_id foreign key (barangay_id) references psgc_barangays (id),
    add constraint fk_addresses_city_id foreign key (municipality_id) references psgc_municipalities (id),
    add constraint fk_addresses_province_id foreign key (province_id) references psgc_provinces (id),
    add constraint fk_addresses_region_id foreign key (region_code) references psgc_regions (code);

alter table addresses
    alter column barangay_id set not null,
    alter column province_id set not null,
    alter column region_code set not null;
