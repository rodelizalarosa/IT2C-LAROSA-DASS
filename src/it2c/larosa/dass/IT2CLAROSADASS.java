package it2c.larosa.dass;

import dentalClinic_data.PatientInfo;
import static it2c.larosa.dass.Config.connectDB;
import java.util.Scanner;
import it2c.larosa.dass.Config;
import it2c.larosa.dass.validationClass;


public class IT2CLAROSADASS {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner (System.in);
        validationClass val = new validationClass();
        String response;
        
    do {
        System.out.print("\n");
        System.out.println("*************************************************************");
        System.out.println("*       Welcome to Dental Appointment Scheduler System!     *");
        System.out.println("*-----------------------------------------------------------*");
        System.out.println("*           1. MANAGE PATIENT INFORMATION                   *");
        System.out.println("*           2. MANAGE DOCTOR INFORMATION                    *");
        System.out.println("*           3. MANAGE STAFF INFORMATION                     *");
        System.out.println("*           4. APPOINTMENT                                  *");
        System.out.println("*           5. VIEW RECORDS                                 *");
        System.out.println("*           6. EXIT                                         *");
        System.out.println("*************************************************************");
                
        System.out.print ("Enter Action: ");
        int act = val.validateChoiceMain();
                                      
        switch (act){
            case 1:
                PatientInfo.managePatients();
            break;
            case 2:
                DoctorInfo.manageDoctors();
            break;
            case 3:
                StaffInfo.manageStaffs();
            break;    
            case 4:
                Appointment.manageAppointments();
            break;
            case 5:
                AppReport.viewRecords();
            break;
            case 6: 
                System.out.println("\nThank You for using Dental Clinic Appointment System!");
                System.exit(0);
            break;
        }
        
        System.out.print("\nDo you want to continue? (yes/no): ");
        response = sc.next();
                
    } while(response.equalsIgnoreCase("yes") && response.equalsIgnoreCase("Yes") );
        System.out.println("\nThank You for using Dental Clinic Appointment System!");
    
    }  

        
    }
    
}
