package it2c.larosa.dass;

import static it2c.larosa.dass.Config.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;




public class validationClass {
 
    public int validateChoiceMain() {
        Scanner sc = new Scanner(System.in);
        int getNum;

        while (true) {
            try {
                getNum = sc.nextInt();

                if (getNum < 1 || getNum > 6) { 
                    System.out.print("\tInvalid Input: Please enter a number between 1 and 6. Try again: ");
                    continue; 
                }
                break; 

            } catch (InputMismatchException e) {
                System.out.print("\tInvalid Input: Must only be a number, try again: ");
                sc.next(); 
            }
        }
        return getNum;
    }
    
    
    public int validateChoice() {
        Scanner sc = new Scanner(System.in);
        int getNum;

        while (true) {
            try {
                getNum = sc.nextInt();

                if (getNum < 1 || getNum > 5) {
                    System.out.print("\tInvalid Input: Please enter a number between 1 and 5. Try again: ");
                    continue; 
                }
                break; 

            } catch (InputMismatchException e) {
                System.out.print("\tInvalid Input: Must only be a number, try again: ");
                sc.next(); 
            }
        }
        return getNum;
    }
    
    public int validateUpdateChoice() {
        Scanner sc = new Scanner(System.in);
        int getNum;

        while (true) {
            try {
                getNum = sc.nextInt();

                if (getNum < 1 || getNum > 8) {
                    System.out.print("\tInvalid Input: Please enter a number between 1 and 8. Try again: ");
                    continue; 
                }
                break; 

            } catch (InputMismatchException e) {
                System.out.print("\tInvalid Input: Must only be a number, try again: ");
                sc.next(); 
            }
        }
        return getNum;
    }
    
    public String validateContactNumber() {
        Scanner sc = new Scanner(System.in);
        String contnum;
        int attempts = 0;

        while (attempts < 3) {
            
            contnum = sc.nextLine().trim();
            if (contnum.matches("\\d{11}")) {
                return contnum;
            } else {
                System.out.println("\tInvalid contact number. Must be 11 digits and numeric.");
                attempts++;
            }
        }

        System.out.println("\tToo many invalid attempts. Process terminated.");
        return "INVALID";
    }
    
    public String validateGender() {
        Scanner sc = new Scanner (System.in);
        String gender;
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Enter Patient Gender (M/F): ");
            gender = sc.nextLine().trim().toUpperCase();
            if (gender.equals("M") || gender.equals("F")) {
                return gender;
            } else {
                System.out.println("\tInvalid input. Please enter 'M' for male or 'F' for female.");
                attempts++;
            }
        }

        System.out.println("\tToo many invalid attempts. Process terminated.");
        return "INVALID";
    }


    public String validateDateOfBirth() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
            Scanner sc = new Scanner(System.in);
            String dob;
            int attempts = 0;

            while (attempts < 3) {
                System.out.print("Enter Patient's Date of Birth (YYYY-MM-DD): ");
                dob = sc.nextLine().trim();

                if (dob.isEmpty()) {
                    System.out.println("\tInvalid Input: Date of birth cannot be empty.");
                    attempts++;
                    continue;
                }

                try {
                    LocalDate parsedDate = LocalDate.parse(dob, formatter);

                    if (parsedDate.isAfter(LocalDate.now())) {
                        System.out.println("\tInvalid Input: Date of birth cannot be in the future.");
                        attempts++;
                        continue;
                    }

                    if (parsedDate.isAfter(LocalDate.now().minusYears(1))) {
                        System.out.println("\tInvalid Input: Patient must be at least 1 year old.");
                        attempts++;
                        continue;
                    }

                    return dob;
                } catch (DateTimeParseException e) {
                    System.out.println("\tInvalid Input: Please enter a valid date in the format (YYYY-MM-DD).");
                    attempts++;
                }
            }

            System.out.println("\tToo many invalid attempts. Process terminated.");
            return "INVALID";
        }
    
    public String validateEmail() {
        Scanner sc = new Scanner(System.in);
        String email;
        int attempts = 0; 

        while (attempts < 3) {
            
            email = sc.nextLine().trim();

            if (email.isEmpty()) {
                System.out.println("\tEmail cannot be empty. Please try again.");
                attempts++;
                continue;
            }

            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                System.out.println("\tInvalid email format. Please try again.");
                attempts++;
                continue;
            }

            String[] validDomains = {"@gmail.com", "@yahoo.com", "@outlook.com"};
            boolean validDomain = false;

            for (String domain : validDomains) {
                if (email.endsWith(domain)) {
                    validDomain = true;
                    break;
                }
            }

            if (!validDomain) {
                System.out.println("\tEmail must have one of the supported domain names (@gmail.com, @yahoo.com, @outlook.com). Please try again.");
                attempts++;
                continue;
            }

            try {
                PreparedStatement findEmail = connectDB().prepareStatement("SELECT pEMAIL FROM tbl_patients WHERE pEMAIL = ?");
                findEmail.setString(1, email);
                ResultSet result = findEmail.executeQuery();

                if (result.next()) {
                    System.out.println("\tError: Email is already registered. Please try again.");
                    attempts++;
                    continue;
                }
            } catch (SQLException e) {
                System.out.println("\tError: " + e.getMessage());
                attempts++;
                continue;
            }

            return email;
        }

        System.out.println("\tToo many invalid attempts. Process terminated.");
        return "INVALID";
    }
    
    public String validatePatientAddress() {
        String address;
        Scanner sc = new Scanner(System.in);
        int attempts = 0; 

        while (attempts < 3) {
            System.out.print("Enter Patient's Address: ");
            address = sc.nextLine().trim();

            if (address.length() < 5) {
                System.out.println("\tAddress is too short. Please enter at least 5 characters.");
                attempts++;
                continue;
            }

            if (!address.matches(".*[a-zA-Z]+.*") || !address.matches("[a-zA-Z0-9\\s,.-]+")) {
                System.out.println("\tInvalid address format. Please enter a valid address.");
                attempts++;
                continue;
            }

            return address;
        }

        System.out.println("\tToo many invalid attempts. Process terminated.");
        return "INVALID";
    }

    public boolean pIDExists(String patientID) {
        String sql = "SELECT COUNT(*) FROM tbl_patients WHERE pID = ?";
        try (Connection conn = connectDB();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error checking Patient ID: " + e.getMessage());
        }
            return false; 
    }

    
}
