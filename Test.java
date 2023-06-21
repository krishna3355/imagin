package Imagin.in;

@RestController
@RequestMapping("/employees")
public class Test {

    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        List<String> errors = validateEmployeeData(employee);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join(", ", errors));
        }


        return ResponseEntity.ok("Employee details stored successfully.");
    }

    private List<String> validateEmployeeData(Employee employee) {
        List<String> errors = new ArrayList<>();

        // Validate the Employee ID
        if (employee.getEmployeeId() == null) {
            errors.add("Employee ID is required.");
        }

        // Validate the First Name
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            errors.add("First Name is required.");
        }

        // Validate the Last Name
        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
            errors.add("Last Name is required.");
        }

        // Validate the Email
        if (employee.getEmail() == null || !isValidEmail(employee.getEmail())) {
            errors.add("Email is invalid or missing.");
        }

        // Validate the Date of Joining (DOJ)
        if (employee.getDoj() == null) {
            errors.add("Date of Joining (DOJ) is required.");
        }

        // Validate the Salary
        if (employee.getSalary() == null || employee.getSalary() <= 0) {
            errors.add("Salary is invalid or missing.");
        }

        // Validate the Phone Numbers
        if (employee.getPhoneNumbers() == null || employee.getPhoneNumbers().isEmpty()) {
            errors.add("At least one Phone Number is required.");
        }


        return errors;
    }

    private boolean isValidEmail(String email) {
    }
}