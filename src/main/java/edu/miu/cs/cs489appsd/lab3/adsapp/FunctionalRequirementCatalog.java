package edu.miu.cs.cs489appsd.lab3.adsapp;

import java.util.List;

public final class FunctionalRequirementCatalog {

    private FunctionalRequirementCatalog() {
    }

    public static List<String> getFunctionalRequirements() {
        return List.of(
                "The system should allow the Office Manager to register dentists in the ADS network.",
                "The system should record each dentist's unique ID, first name, last name, contact phone number, email, and specialization.",
                "The system should allow the Office Manager to enroll new patients who require dental services.",
                "The system should record each patient's first name, last name, contact phone number, email, mailing address, and date of birth.",
                "The system should allow patients to request appointments by phone through the Office Manager or by submitting an online form on the ADS website.",
                "The system should allow the Office Manager to book appointments for patients.",
                "The system should record each appointment's date and time, patient, dentist, and surgery location.",
                "The system should send a confirmation email to the patient after an appointment is booked.",
                "The system should allow dentists to sign in and view all of their appointments together with patient details.",
                "The system should allow patients to sign in and view all of their appointments together with dentist information.",
                "The system should maintain information about each surgery, including its name, location address, and telephone number.",
                "The system should allow patients to request cancellation of an existing appointment.",
                "The system should allow patients to request a change to an existing appointment.",
                "The system should prevent a dentist from being assigned more than five appointments in a given week.",
                "The system should prevent a patient from requesting a new appointment when the patient has an outstanding unpaid dental-service bill."
        );
    }
}
