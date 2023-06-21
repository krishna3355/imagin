package Imagin.in;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping("/taxdeductions")
    public ResponseEntity<List<EmployeeTaxDeduction>> getEmployeeTaxDeductions() {
        List<Employee> employees = fetchEmployees();

        List<EmployeeTaxDeduction> taxDeductions = new ArrayList<>();

        for (Employee employee : employees) {
            double yearlySalary = calculateYearlySalary(employee);
            double taxAmount = calculateTaxAmount(yearlySalary);
            double cessAmount = calculateCessAmount(yearlySalary);

            EmployeeTaxDeduction deduction = new EmployeeTaxDeduction();
            deduction.setEmployeeCode(employee.getEmployeeCode());
            deduction.setFirstName(employee.getFirstName());
            deduction.setLastName(employee.getLastName());
            deduction.setYearlySalary(yearlySalary);
            deduction.setTaxAmount(taxAmount);
            deduction.setCessAmount(cessAmount);

            taxDeductions.add(deduction);
        }

        return ResponseEntity.ok(taxDeductions);
    }

    private double calculateYearlySalary(Employee employee) {
        LocalDate doj = employee.getDoj();
        LocalDate currentFinancialYearStart = LocalDate.now().withMonth(4).withDayOfMonth(1);
        LocalDate currentFinancialYearEnd = currentFinancialYearStart.plusYears(1).minusDays(1);
        int monthsWorked = (int) ChronoUnit.MONTHS.between(doj, currentFinancialYearEnd.plusDays(1));

        double totalSalary = employee.getSalary() * monthsWorked;

        double lossOfPayPerDay = employee.getSalary() / 30;
        int lossOfPayDays = calculateLossOfPayDays(employee);
        double lossOfPayAmount = lossOfPayPerDay * lossOfPayDays;

        return totalSalary - lossOfPayAmount;
    }

    private int calculateLossOfPayDays(Employee employee) {
         }

    private double calculateTaxAmount(double yearlySalary) {
        if (yearlySalary <= 250000) {
            return 0;
        } else if (yearlySalary <= 500000) {
            return (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            return (yearlySalary - 500000) * 0.1 + 25000;
        } else {
            return (yearlySalary - 1000000) * 0.2 + 75000;
        }
    }

    private double calculateCessAmount(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        }
        return 0;
    }


}