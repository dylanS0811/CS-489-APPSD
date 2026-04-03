.headers on
.mode column

SELECT
    a.appointment_id,
    a.appointment_date,
    a.appointment_time,
    a.status AS appointment_status,
    p.patient_id,
    p.first_name || ' ' || p.last_name AS patient_name,
    d.dentist_id,
    d.first_name || ' ' || d.last_name AS dentist_name,
    s.surgery_id,
    s.name AS surgery_name
FROM appointment a
JOIN patient p
    ON p.patient_id = a.patient_id
JOIN dentist d
    ON d.dentist_id = a.dentist_id
JOIN surgery s
    ON s.surgery_id = a.surgery_id
WHERE a.patient_id = 'P105'
  AND a.appointment_date = '2013-09-14'
ORDER BY a.appointment_time;
