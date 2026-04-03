.headers on
.mode column

SELECT
    dentist_id,
    last_name,
    first_name,
    specialization,
    phone_number,
    email
FROM dentist
ORDER BY last_name ASC, first_name ASC;
