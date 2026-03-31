package edu.miu.cs.cs489appsd.lab2a.employeepensionapp;

import edu.miu.cs.cs489appsd.lab2a.employeepensionapp.model.Employee;
import edu.miu.cs.cs489appsd.lab2a.employeepensionapp.model.PensionPlan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeePensionApp {

    public static void main(String[] args) {
        LocalDate referenceDate = args.length > 0 ? LocalDate.parse(args[0]) : LocalDate.now();
        List<Employee> employees = loadEmployees();

        printAllEmployees(employees);
        System.out.println();
        printUpcomingEnrollees(employees, referenceDate);
    }

    private static void printAllEmployees(List<Employee> employees) {
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getYearlySalary).reversed()
                        .thenComparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName))
                .collect(Collectors.toList());

        System.out.println("All Employees in JSON Format");
        System.out.println(buildEmployeesJson(sortedEmployees));
    }

    private static void printUpcomingEnrollees(List<Employee> employees, LocalDate referenceDate) {
        LocalDate nextQuarterStart = getNextQuarterStart(referenceDate);
        LocalDate nextQuarterEnd = nextQuarterStart.plusMonths(3).minusDays(1);

        List<Employee> upcomingEnrollees = employees.stream()
                .filter(employee -> !employee.isEnrolled())
                .filter(employee -> {
                    LocalDate eligibilityDate = employee.getPensionEligibilityDate();
                    return !eligibilityDate.isBefore(nextQuarterStart) && !eligibilityDate.isAfter(nextQuarterEnd);
                })
                .sorted(Comparator.comparing(Employee::getEmploymentDate).reversed()
                        .thenComparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName))
                .collect(Collectors.toList());

        System.out.println("Quarterly Upcoming Enrollees Report in JSON Format");
        System.out.println("Reference Date: " + referenceDate);
        System.out.println("Next Quarter: " + nextQuarterStart + " to " + nextQuarterEnd);
        System.out.println(buildEmployeesJson(upcomingEnrollees));
    }

    private static LocalDate getNextQuarterStart(LocalDate referenceDate) {
        int currentQuarterIndex = (referenceDate.getMonthValue() - 1) / 3;
        int nextQuarterIndex = (currentQuarterIndex + 1) % 4;
        int year = referenceDate.getYear() + (currentQuarterIndex == 3 ? 1 : 0);
        int startMonth = nextQuarterIndex * 3 + 1;
        return LocalDate.of(year, startMonth, 1);
    }

    private static List<Employee> loadEmployees() {
        return List.of(
                new Employee(
                        1L,
                        "Daniel",
                        "Agar",
                        LocalDate.parse("2023-01-17"),
                        new BigDecimal("105945.50"),
                        new PensionPlan("EX1089", null, new BigDecimal("100.00"))
                ),
                new Employee(
                        2L,
                        "Benard",
                        "Shaw",
                        LocalDate.parse("2022-09-03"),
                        new BigDecimal("197750.00"),
                        new PensionPlan(null, LocalDate.parse("2025-09-03"), null)
                ),
                new Employee(
                        3L,
                        "Carly",
                        "Agar",
                        LocalDate.parse("2014-05-16"),
                        new BigDecimal("842000.75"),
                        new PensionPlan("SM2307", LocalDate.parse("2017-05-17"), new BigDecimal("1555.50"))
                ),
                new Employee(
                        4L,
                        "Wesley",
                        "Schneider",
                        LocalDate.parse("2023-07-21"),
                        new BigDecimal("74500.00"),
                        null
                ),
                new Employee(
                        5L,
                        "Anna",
                        "Wiltord",
                        LocalDate.parse("2023-03-15"),
                        new BigDecimal("85750.00"),
                        null
                ),
                new Employee(
                        6L,
                        "Yosef",
                        "Tesfalem",
                        LocalDate.parse("2024-10-31"),
                        new BigDecimal("100000.00"),
                        null
                )
        );
    }

    private static String buildEmployeesJson(List<Employee> employees) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            sb.append("  {\n");
            sb.append("    \"employeeId\": ").append(employee.getEmployeeId()).append(",\n");
            sb.append("    \"firstName\": ").append(toJsonString(employee.getFirstName())).append(",\n");
            sb.append("    \"lastName\": ").append(toJsonString(employee.getLastName())).append(",\n");
            sb.append("    \"employmentDate\": ").append(toJsonString(employee.getEmploymentDate().toString())).append(",\n");
            sb.append("    \"yearlySalary\": ").append(formatMoney(employee.getYearlySalary())).append(",\n");
            sb.append("    \"pensionPlan\": ").append(buildPensionPlanJson(employee.getPensionPlan())).append("\n");
            sb.append("  }");

            if (i < employees.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("]");
        return sb.toString();
    }

    private static String buildPensionPlanJson(PensionPlan pensionPlan) {
        if (pensionPlan == null) {
            return "null";
        }

        return new StringBuilder()
                .append("{ ")
                .append("\"planReferenceNumber\": ").append(toJsonString(pensionPlan.getPlanReferenceNumber())).append(", ")
                .append("\"enrollmentDate\": ").append(toJsonString(pensionPlan.getEnrollmentDate() == null
                        ? null
                        : pensionPlan.getEnrollmentDate().toString())).append(", ")
                .append("\"monthlyContribution\": ").append(formatMoneyOrNull(pensionPlan.getMonthlyContribution()))
                .append(" }")
                .toString();
    }

    private static String formatMoney(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    private static String formatMoneyOrNull(BigDecimal amount) {
        return amount == null ? "null" : formatMoney(amount);
    }

    private static String toJsonString(String value) {
        if (value == null) {
            return "null";
        }

        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}
