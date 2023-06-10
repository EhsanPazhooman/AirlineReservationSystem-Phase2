import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
public class Passenger {
    static Scanner scanner = new Scanner(System.in);

    /////////////////// Passenger Menu Options //////////////////////
    public static void passengerMenu() throws IOException {
        System.out.println("••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••");
        System.out.println("\t" + "\t" + "Passenger Menu Option" + "\t");
        System.out.println("••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••");
        System.out.println("\t" + "•••••••••••••••" + "Menu Options" + "••••••••••••••••••••••");
        System.out.println("Ⅰ" + '-' + "Change Password");
        System.out.println("Ⅱ" + '-' + "Search flight ticket");
        System.out.println("Ⅲ" + '-' + "Booking ticket");
        System.out.println("Ⅳ" + '-' + "Booked tickets");
        System.out.println("Ⅴ" + '-' + "ticket cancellation");
        System.out.println("Ⅵ"+'-' + "Add charge");
        System.out.println("0" + '-' + "Sign out");
        Users users = new Users();
        Tickets tickets = new Tickets();
        Flightschedules flightschedules = new Flightschedules();
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                users.changePassword();
                break;
            case "2":
                flightschedules.searchFlights();
                Main.pressEnterToContinue();
                break;
            case "3":
                Tickets.bookingTicket();
                Main.pressEnterToContinue();
                break;
            case "4":
                tickets.printTickets();
                Main.pressEnterToContinue();
                break;
            case "5":
                tickets.removeTicket();
                Main.pressEnterToContinue();
                break;
            case "6":
                users.addCharge();
                Main.pressEnterToContinue();
                break;
            case "0":
                Users.mainMenu();

        }
        passengerMenu();
    }
}
