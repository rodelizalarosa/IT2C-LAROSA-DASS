package dentalClinic_data;

import it2c.larosa.dass.IT2CLAROSADASS;
import it2c.larosa.dass.Config;
import it2c.larosa.dass.validationClass;
import it2c.larosa.dass.viewConfig;
import java.util.Scanner;

public class PatientInfo {
    
    Config conf = new Config();
    validationClass val = new validationClass();
        
    public static void managePatients(){
        
        validationClass val = new validationClass();
        Scanner sc = new Scanner (System.in);
        boolean response = true;
        
        do {
                System.out.print("\n");
                System.out.println("===================================");
                System.out.println("     MANAGE PATIENT INFORMATION    ");
                System.out.println("-----------------------------------");
                System.out.println("      1. REGISTER A PATIENT        ");
                System.out.println("      2. VIEW PATIENT RECORD       ");
                System.out.println("      3. EDIT PATIENT RECORD       ");
                System.out.println("      4. DELETE PATIENT RECORD     ");
                System.out.println("      5. EXIT                      ");
                System.out.println("===================================");
                
                System.out.print ("\nEnter Option: ");
                int opt = val.validateChoice();  
                    
                    PatientInfo pINFO = new PatientInfo();
                switch (opt){
                    case 1:
                        pINFO.addPatients();
                        break;
                    case 2: 
                        pINFO.viewPatient();
                        break;
                    case 3:
                        pINFO.viewPatient();
                        pINFO.updatePatient();
                        break;
                    case 4:
                        pINFO.viewPatient();
                        pINFO.deletePatient();
                        pINFO.viewPatient();
                        break;
                    case 5:
                        response = false;
                        System.out.println("\nExiting Manage Patient Information...");
                        break;
                }
                           
        } while(response);
    }
    
    
        public void addPatients(){
            Scanner sc = new Scanner(System.in);

            System.out.print("\n");
            System.out.print("Patient's First Name: ");
            String fname = sc.nextLine();
            
            System.out.print("Patient's Last Name: ");
            String lname = sc.nextLine();
        
            System.out.print("Patient's Contact Number (11 digits): ");
            String contnum = val.validateContactNumber();
            
            System.out.print("Patient's Gender (M/F): ");
            String gender = val.validateGender();
            
            System.out.print("Patient's Date of Birth (YYYY-MM-DD): ");
            String dob = val.validateDateOfBirth();
            
            System.out.print("Patient's Email: ");
            String email = val.validateEmail();
            
            System.out.print("Patient's Address: ");
            String address = val.validatePatientAddress();
            
            String addPATIENT = "INSERT INTO tbl_patients (pFNAME, pLNAME, pCONTNUM, pGENDER, pDOB, pEMAIL, pADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?)";

            conf.addRecords(addPATIENT, fname, lname, contnum, gender, dob, email, address);
            
        }
        
        private void viewPatient() {
            viewConfig cnf = new viewConfig();

            String rodzQuery = "SELECT * FROM tbl_patients";
            String[] rodzHeaders = {"ID", "First Name", "Last Name", "Phone Number", "Gender", "Address", "Email", "Date of Birth"};
            String[] rodzColumns = {"pID", "pFNAME", "pLNAME", "pPHONENUM", "pGENDER", "pADDRESS", "pEMAIL", "pDOB"};

            cnf.viewPatient(rodzQuery, rodzHeaders, rodzColumns);
        }

        public void updatePatientInfo() {
            Scanner sc = new Scanner(System.in);

            String patientID = "";
            boolean idExist = false;
            int attempts = 0;
            int maxAttempts = 3;

            while (!idExist && attempts < maxAttempts) {
                System.out.print("\nEnter Patient ID to update (3 max attempts): ");
                patientID = sc.nextLine().trim();

                if (val.pIDExists(patientID)) {
                    idExist = true;
                    System.out.println("Patient ID found.");
                } else {
                    attempts++;
                    System.out.println("Invalid ID or ID not found.");

                    if (attempts >= maxAttempts) {
                        System.out.println("Maximum attempts reached. Exiting...");
                        return;
                    }
                }
            }

        System.out.println("***********************************************");
        System.out.println("*            Update Patient Details           *");
        System.out.println("***********************************************");
        System.out.println("1. Update First Name");
        System.out.println("2. Update Last Name");
        System.out.println("3. Update Contact Number");
        System.out.println("4. Update Gender");
        System.out.println("5. Update Address");
        System.out.println("6. Update Email Address");
        System.out.println("7. Update Date of Birth");
        System.out.println("***********************************************");

        System.out.print("Please select up to 3 options separated by commas (e.g., 1,3,5): ");
        String input = sc.nextLine().trim();
        String[] choices = input.split(",");

        if (choices.length > 3) {
            System.out.println("\tYou can only update up to 3 fields at a time. Please try again.");
            return;
        }

        // Validate choices
        for (String choice : choices) {
            if (!choice.matches("[1-7]")) {
                System.out.println("\tInvalid option: " + choice + ". Please choose options between 1 and 7.");
                return;
            }
        }

        // Execute updates based on validated choices
        for (String choice : choices) {
            switch (choice.trim()) {
                case "1":
                    System.out.print("Enter new First Name: ");
                    String newFirstName = sc.nextLine().trim();
                    updateField("pFNAME", newFirstName, patientID);
                    break;

                case "2":
                    System.out.print("Enter new Last Name: ");
                    String newLastName = sc.nextLine().trim();
                    updateField("pLNAME", newLastName, patientID);
                    break;

                case "3":
                    System.out.print("Enter new Contact Number: ");
                    String newContactNumber = val.validateContactNumber();
                    if (!newContactNumber.equals("INVALID")) {
                        updateField("pPHONENUM", newContactNumber, patientID);
                    }
                    break;

                case "4":
                    System.out.print("Enter new Gender (M/F): ");
                    String newGender = val.validateGender();
                    if (!newGender.equals("INVALID")) {
                        updateField("pGENDER", newGender, patientID);
                    }
                    break;

                case "5":
                    System.out.print("Enter new Address: ");
                    String newAddress = val.validatePatientAddress();
                    if (!newAddress.equals("INVALID")) {
                        updateField("pADDRESS", newAddress, patientID);
                    }
                    break;

                case "6":
                    System.out.print("Enter new Email Address: ");
                    String newEmail = val.validateEmail();
                    if (!newEmail.equals("INVALID")) {
                        updateField("pEMAIL", newEmail, patientID);
                    }
                    break;

                case "7":
                    System.out.print("Enter new Date of Birth (YYYY-MM-DD): ");
                    String newDob = val.validateDateOfBirth();
                    if (!newDob.equals("INVALID")) {
                        updateField("pDOB", newDob, patientID);
                    }
                    break;

                default:
                    System.out.println("\tInvalid option: " + choice.trim());
                    break;
            }
        }

        System.out.println("\tPatient details updated successfully!");
    }


            
        
        
        }
    
}
