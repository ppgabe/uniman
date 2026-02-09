alter table addresses
    drop column municipality_id,
    drop column province_id,
    drop column region_code;

-- We don't need these columns because barangay_id already gives us what we need.
-- This was a genuine accident on my end. Oops :)
-- By Gabriel