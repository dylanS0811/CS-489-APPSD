.headers on
.mode column

SELECT
    a.appointment_id,
    a.appointment_date,
    a.appointment_time,
    a.status AS appointment_status,
    d.dentist_id,
    d.first_name || ' ' || d.last_name AS dentist_name,
    p.patient_id,
    p.first_name || ' ' || p.last_name AS patient_name,
    p.phone_number AS patient_phone,
    p.email AS patient_email,
    s.surgery_id,
    s.name AS surgery_name
FROM appointment a
JOIN dentist d
    ON d.dentist_id = a.dentist_id
JOIN patient p
    ON p.patient_id = a.patient_id
JOIN surgery s
    ON s.surgery_id = a.surgery_id
WHERE a.dentist_id = 'D105'
ORDER BY a.appointment_date, a.appointment_time;
