import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Admin {
    static Scanner scanner = new Scanner(System.in);

    static void adminMenu() throws IOException {
        System.out.println("••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••");
        System.out.println("\t" + "•••••••••••••••" + "Admin Menu Options" + "••••••••••••••••••••••");
        System.out.println("••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••");
        System.out.println("Ⅰ" + '-' + "Add");
        System.out.println("Ⅱ" + '-' + "Update");
        System.out.println("Ⅲ" + '-' + "Remove");
        System.out.println("Ⅳ" + '-' + "Flight Schedules");
        System.out.println("0" + '-' + "Sign Out");


        Flightschedules flightShedules = new Flightschedules();
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                flightShedules.addFlight();
                break;
            case "2":
                flightShedules.update();
                break;
            case "3":
                flightShedules.remove();
            case "4":
                flightShedules.displayFlights();
            case "0":
                Users.mainMenu();
            default:
                System.out.println("Invalid option.");

        }
    }
}

