.headers on
.mode column

SELECT
    a.appointment_id,
    a.appointment_date,
    a.appointment_time,
    s.surgery_id,
    s.name AS surgery_name,
    s.location_address,
    d.first_name || ' ' || d.last_name AS dentist_name,
    p.first_name || ' ' || p.last_name AS patient_name
FROM appointment a
JOIN surgery s
    ON s.surgery_id = a.surgery_id
JOIN dentist d
    ON d.dentist_id = a.dentist_id
JOIN patient p
    ON p.patient_id = a.patient_id
ORDER BY a.appointment_date, a.appointment_time, s.surgery_id;
