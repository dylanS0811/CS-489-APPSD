package edu.miu.cs.cs489appsd.lab2a.employeepensionapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PensionPlan {
    private final String planReferenceNumber;
    private final LocalDate enrollmentDate;
    private final BigDecimal monthlyContribution;

    public PensionPlan(String planReferenceNumber, LocalDate enrollmentDate, BigDecimal monthlyContribution) {
        this.planReferenceNumber = planReferenceNumber;
        this.enrollmentDate = enrollmentDate;
        this.monthlyContribution = monthlyContribution;
    }

    public String getPlanReferenceNumber() {
        return planReferenceNumber;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public BigDecimal getMonthlyContribution() {
        return monthlyContribution;
    }
}
