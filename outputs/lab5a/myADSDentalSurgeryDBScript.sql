PRAGMA foreign_keys = ON;

DROP TRIGGER IF EXISTS trg_prevent_request_with_unpaid_bill_insert;
DROP TRIGGER IF EXISTS trg_prevent_request_with_unpaid_bill_update;
DROP TRIGGER IF EXISTS trg_limit_weekly_dentist_appointments_insert;
DROP TRIGGER IF EXISTS trg_limit_weekly_dentist_appointments_update;

DROP TABLE IF EXISTS dental_service_bill;
DROP TABLE IF EXISTS appointment;
DROP TABLE IF EXISTS appointment_request;
DROP TABLE IF EXISTS surgery;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS dentist;
DROP TABLE IF EXISTS office_manager;

CREATE TABLE office_manager (
    office_manager_id TEXT PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE
);

CREATE TABLE dentist (
    dentist_id TEXT PRIMARY KEY,
    office_manager_id TEXT NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    specialization TEXT NOT NULL,
    FOREIGN KEY (office_manager_id) REFERENCES office_manager(office_manager_id)
);

CREATE TABLE patient (
    patient_id TEXT PRIMARY KEY,
    office_manager_id TEXT NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    mailing_address TEXT NOT NULL,
    date_of_birth TEXT NOT NULL CHECK (date(date_of_birth) IS NOT NULL),
    FOREIGN KEY (office_manager_id) REFERENCES office_manager(office_manager_id)
);

CREATE TABLE surgery (
    surgery_id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    location_address TEXT NOT NULL,
    telephone_number TEXT NOT NULL
);

CREATE TABLE appointment_request (
    request_id TEXT PRIMARY KEY,
    patient_id TEXT NOT NULL,
    office_manager_id TEXT NOT NULL,
    request_channel TEXT NOT NULL CHECK (request_channel IN ('PHONE', 'ONLINE')),
    request_type TEXT NOT NULL CHECK (request_type IN ('NEW', 'CHANGE', 'CANCEL')),
    preferred_date TEXT NOT NULL CHECK (date(preferred_date) IS NOT NULL),
    preferred_time TEXT NOT NULL CHECK (time(preferred_time) IS NOT NULL),
    status TEXT NOT NULL CHECK (status IN ('PENDING', 'BOOKED', 'CANCELLED', 'DECLINED')),
    requested_at TEXT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY (office_manager_id) REFERENCES office_manager(office_manager_id)
);

CREATE TABLE appointment (
    appointment_id TEXT PRIMARY KEY,
    request_id TEXT UNIQUE,
    patient_id TEXT NOT NULL,
    dentist_id TEXT NOT NULL,
    surgery_id TEXT NOT NULL,
    booked_by_office_manager_id TEXT NOT NULL,
    appointment_date TEXT NOT NULL CHECK (date(appointment_date) IS NOT NULL),
    appointment_time TEXT NOT NULL CHECK (time(appointment_time) IS NOT NULL),
    status TEXT NOT NULL CHECK (status IN ('BOOKED', 'COMPLETED', 'CANCELLED', 'CHANGED')),
    confirmation_email_sent INTEGER NOT NULL CHECK (confirmation_email_sent IN (0, 1)),
    FOREIGN KEY (request_id) REFERENCES appointment_request(request_id),
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY (dentist_id) REFERENCES dentist(dentist_id),
    FOREIGN KEY (surgery_id) REFERENCES surgery(surgery_id),
    FOREIGN KEY (booked_by_office_manager_id) REFERENCES office_manager(office_manager_id),
    UNIQUE (dentist_id, appointment_date, appointment_time)
);

CREATE TABLE dental_service_bill (
    bill_id TEXT PRIMARY KEY,
    patient_id TEXT NOT NULL,
    appointment_id TEXT UNIQUE,
    issue_date TEXT NOT NULL CHECK (date(issue_date) IS NOT NULL),
    amount NUMERIC NOT NULL CHECK (amount >= 0),
    status TEXT NOT NULL CHECK (status IN ('PAID', 'UNPAID', 'VOID')),
    description TEXT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id)
);

CREATE INDEX idx_dentist_last_name ON dentist(last_name, first_name);
CREATE INDEX idx_appointment_dentist_date ON appointment(dentist_id, appointment_date, appointment_time);
CREATE INDEX idx_appointment_patient_date ON appointment(patient_id, appointment_date, appointment_time);
CREATE INDEX idx_appointment_surgery ON appointment(surgery_id, appointment_date, appointment_time);
CREATE INDEX idx_bill_patient_status ON dental_service_bill(patient_id, status);

CREATE TRIGGER trg_prevent_request_with_unpaid_bill_insert
BEFORE INSERT ON appointment_request
FOR EACH ROW
WHEN NEW.request_type = 'NEW'
 AND EXISTS (
    SELECT 1
    FROM dental_service_bill bill
    WHERE bill.patient_id = NEW.patient_id
      AND bill.status = 'UNPAID'
)
BEGIN
    SELECT RAISE(ABORT, 'Patient has an outstanding unpaid dental-service bill.');
END;

CREATE TRIGGER trg_prevent_request_with_unpaid_bill_update
BEFORE UPDATE OF patient_id, request_type ON appointment_request
FOR EACH ROW
WHEN NEW.request_type = 'NEW'
 AND EXISTS (
    SELECT 1
    FROM dental_service_bill bill
    WHERE bill.patient_id = NEW.patient_id
      AND bill.status = 'UNPAID'
)
BEGIN
    SELECT RAISE(ABORT, 'Patient has an outstanding unpaid dental-service bill.');
END;

CREATE TRIGGER trg_limit_weekly_dentist_appointments_insert
BEFORE INSERT ON appointment
FOR EACH ROW
WHEN (
    SELECT COUNT(*)
    FROM appointment a
    WHERE a.dentist_id = NEW.dentist_id
      AND a.status <> 'CANCELLED'
      AND strftime('%Y-%W', a.appointment_date) = strftime('%Y-%W', NEW.appointment_date)
) >= 5
BEGIN
    SELECT RAISE(ABORT, 'A dentist cannot be assigned more than 5 appointments in a given week.');
END;

CREATE TRIGGER trg_limit_weekly_dentist_appointments_update
BEFORE UPDATE OF dentist_id, appointment_date, status ON appointment
FOR EACH ROW
WHEN NEW.status <> 'CANCELLED'
 AND (
    SELECT COUNT(*)
    FROM appointment a
    WHERE a.dentist_id = NEW.dentist_id
      AND a.appointment_id <> OLD.appointment_id
      AND a.status <> 'CANCELLED'
      AND strftime('%Y-%W', a.appointment_date) = strftime('%Y-%W', NEW.appointment_date)
) >= 5
BEGIN
    SELECT RAISE(ABORT, 'A dentist cannot be assigned more than 5 appointments in a given week.');
END;

INSERT INTO office_manager (office_manager_id, first_name, last_name, phone_number, email) VALUES
    ('OM100', 'Sarah', 'Collins', '602-555-0100', 'sarah.collins@ads.com');

INSERT INTO dentist (dentist_id, office_manager_id, first_name, last_name, phone_number, email, specialization) VALUES
    ('D100', 'OM100', 'Tony', 'Smith', '602-555-1200', 'tony.smith@ads.com', 'General Dentistry'),
    ('D105', 'OM100', 'Helen', 'Pearson', '602-555-1205', 'helen.pearson@ads.com', 'Endodontics'),
    ('D110', 'OM100', 'Robin', 'Plevin', '602-555-1210', 'robin.plevin@ads.com', 'Oral Surgery'),
    ('D115', 'OM100', 'Amelia', 'Roberts', '602-555-1215', 'amelia.roberts@ads.com', 'Pediatric Dentistry');

INSERT INTO patient (patient_id, office_manager_id, first_name, last_name, phone_number, email, mailing_address, date_of_birth) VALUES
    ('P100', 'OM100', 'Gillian', 'White', '602-555-2100', 'gillian.white@example.com', '14 Palm Street, Phoenix, AZ', '1986-04-17'),
    ('P105', 'OM100', 'Jill', 'Bell', '602-555-2105', 'jill.bell@example.com', '99 Ridge Avenue, Mesa, AZ', '1990-08-09'),
    ('P108', 'OM100', 'Ian', 'MacKay', '602-555-2108', 'ian.mackay@example.com', '52 Granite Road, Tempe, AZ', '1982-11-23'),
    ('P110', 'OM100', 'John', 'Walker', '602-555-2110', 'john.walker@example.com', '7 Copper Lane, Scottsdale, AZ', '1978-01-05'),
    ('P115', 'OM100', 'Maria', 'Lopez', '602-555-2115', 'maria.lopez@example.com', '340 Canyon Vista, Chandler, AZ', '1994-06-15');

INSERT INTO surgery (surgery_id, name, location_address, telephone_number) VALUES
    ('S10', 'ADS Tempe Surgery', '105 Oak Center, Tempe, AZ', '480-555-3010'),
    ('S13', 'ADS Mesa Surgery', '22 Horizon Drive, Mesa, AZ', '480-555-3013'),
    ('S15', 'ADS Phoenix Surgery', '501 Camelback Road, Phoenix, AZ', '602-555-3015');

INSERT INTO appointment_request (
    request_id, patient_id, office_manager_id, request_channel, request_type,
    preferred_date, preferred_time, status, requested_at
) VALUES
    ('AR1001', 'P100', 'OM100', 'PHONE',  'NEW',    '2013-09-12', '10:00:00', 'BOOKED',   '2013-09-08 09:10:00'),
    ('AR1002', 'P105', 'OM100', 'ONLINE', 'NEW',    '2013-09-12', '12:00:00', 'BOOKED',   '2013-09-08 11:15:00'),
    ('AR1003', 'P108', 'OM100', 'PHONE',  'NEW',    '2013-09-12', '10:00:00', 'BOOKED',   '2013-09-09 08:30:00'),
    ('AR1004', 'P108', 'OM100', 'ONLINE', 'CHANGE', '2013-09-14', '14:00:00', 'BOOKED',   '2013-09-11 14:20:00'),
    ('AR1005', 'P105', 'OM100', 'PHONE',  'NEW',    '2013-09-14', '16:30:00', 'BOOKED',   '2013-09-12 10:45:00'),
    ('AR1006', 'P110', 'OM100', 'ONLINE', 'NEW',    '2013-09-15', '18:00:00', 'BOOKED',   '2013-09-12 17:50:00');

INSERT INTO appointment (
    appointment_id, request_id, patient_id, dentist_id, surgery_id,
    booked_by_office_manager_id, appointment_date, appointment_time, status, confirmation_email_sent
) VALUES
    ('A1001', 'AR1001', 'P100', 'D100', 'S15', 'OM100', '2013-09-12', '10:00:00', 'BOOKED',    1),
    ('A1002', 'AR1002', 'P105', 'D100', 'S15', 'OM100', '2013-09-12', '12:00:00', 'BOOKED',    1),
    ('A1003', 'AR1003', 'P108', 'D105', 'S10', 'OM100', '2013-09-12', '10:00:00', 'COMPLETED', 1),
    ('A1004', 'AR1004', 'P108', 'D105', 'S10', 'OM100', '2013-09-14', '14:00:00', 'BOOKED',    1),
    ('A1005', 'AR1005', 'P105', 'D110', 'S15', 'OM100', '2013-09-14', '16:30:00', 'COMPLETED', 1),
    ('A1006', 'AR1006', 'P110', 'D110', 'S13', 'OM100', '2013-09-15', '18:00:00', 'BOOKED',    1);

INSERT INTO dental_service_bill (bill_id, patient_id, appointment_id, issue_date, amount, status, description) VALUES
    ('B1001', 'P100', 'A1001', '2013-09-12', 120.00, 'PAID',   'Routine dental consultation'),
    ('B1002', 'P108', 'A1003', '2013-09-12', 300.00, 'UNPAID', 'Root canal treatment follow-up'),
    ('B1003', 'P105', 'A1005', '2013-09-14', 240.00, 'PAID',   'Wisdom tooth extraction'),
    ('B1004', 'P115', NULL,    '2013-09-10', 180.00, 'UNPAID', 'Outstanding balance from a previous dental procedure');

-- ==============================================================
-- REQUIRED QUERY 1
-- Display the list of ALL Dentists registered in the system,
-- sorted in ascending order of their lastNames.
-- ==============================================================
SELECT
    dentist_id,
    last_name,
    first_name,
    specialization,
    phone_number,
    email
FROM dentist
ORDER BY last_name ASC, first_name ASC;

-- ==============================================================
-- REQUIRED QUERY 2
-- Display the list of ALL Appointments for dentist D105.
-- Include the Patient information.
-- ==============================================================
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

-- ==============================================================
-- REQUIRED QUERY 3
-- Display the list of ALL Appointments that have been
-- scheduled at a Surgery Location.
-- ==============================================================
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

-- ==============================================================
-- REQUIRED QUERY 4
-- Display the list of the Appointments booked for patient P105
-- on 2013-09-14.
-- ==============================================================
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
