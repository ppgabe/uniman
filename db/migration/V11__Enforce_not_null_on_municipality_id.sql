alter table psgc_barangays
    alter column municipality_id set not null;

alter table addresses
    alter column municipality_id set not null;