package edu.miu.cs.cs489appsd.lab2a.employeepensionapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private final long employeeId;
    private final String firstName;
    private final String lastName;
    private final LocalDate employmentDate;
    private final BigDecimal yearlySalary;
    private final PensionPlan pensionPlan;

    public Employee(long employeeId, String firstName, String lastName, LocalDate employmentDate,
                    BigDecimal yearlySalary, PensionPlan pensionPlan) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employmentDate = employmentDate;
        this.yearlySalary = yearlySalary;
        this.pensionPlan = pensionPlan;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public BigDecimal getYearlySalary() {
        return yearlySalary;
    }

    public PensionPlan getPensionPlan() {
        return pensionPlan;
    }

    public boolean isEnrolled() {
        return pensionPlan != null && pensionPlan.getEnrollmentDate() != null;
    }

    public LocalDate getPensionEligibilityDate() {
        return employmentDate.plusYears(3);
    }
}
