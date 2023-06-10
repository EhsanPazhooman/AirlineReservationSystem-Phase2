import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Flightschedules {
    static Scanner input = new Scanner(System.in);
    private RandomAccessFile raf = new RandomAccessFile("FLights.dat", "rw");
    private static final int FIX_SIZE = 20;

    public Flightschedules() throws FileNotFoundException {
    }


    //////////////// Method for adding admin flights /////////////////
    public void addFlight() throws IOException {
        Flightschedules flightschedules = new Flightschedules();
        flightschedules.writeFlightToFile();
        Main.pressEnterToContinue();
        Admin.adminMenu();
    }
    /////////// Method for editing the features of a flight /////////////
    public void update() throws IOException {
        System.out.println("Enter the flight-id you want to update :");
        String ID = input.nextLine();
        raf.seek(0);
        for (int i = 0; i < raf.length(); i+=208) {
            raf.seek(i);
            if (ID.equals(fixStringToRead()))
            {
                System.out.println("Which feature you want to change ? ");
                System.out.println("1-Origin");
                System.out.println("2-Destination");
                System.out.println("3-Date");
                System.out.println("4-Time");
                System.out.println("5-Price");
                System.out.println("6-Seats");
                String update = input.nextLine();
                switch (update) {
                    case "1":
                        System.out.println("Enter your new Origin :");
                        String newOrigin = input.nextLine();
                        raf.seek(i+20);
                        fixStringToWrite(newOrigin);
                        break;
                    case "2":
                        System.out.println("Enter your new Destination :");
                        String newDestination = input.nextLine();
                        fixStringToWrite(newDestination);
                        break;
                    case "3":
                        System.out.println("Enter your new Date :");
                        String newDate = input.nextLine();
                        fixStringToWrite(newDate);
                        break;
                    case "4":
                        System.out.println("Enter your new Time : ");
                        String newTime = input.nextLine();
                        fixStringToWrite(newTime);
                        break;
                    case "5":
                        System.out.println("Enter your new Price : ");
                        int newPrice = input.nextInt();
                        raf.writeInt(newPrice);
                        break;
                    case "6":
                        System.out.println("Enter your new Seats : ");
                        int newSeats = input.nextInt();
                        raf.writeInt(newSeats);
                        break;
                    default:
                        System.out.println("Wrong chosen option");
                        update();
                }
            }
        }
        Main.pressEnterToContinue();
        Admin.adminMenu();
    }

    //////////////// Method for removing flight ///////////////
    public void remove() throws IOException {
        System.out.println("Enter the flight-id you want to remove ?");
        String ID = input.nextLine();

        for (int i = 0; i < raf.length(); i += 208) {
            raf.seek(i);
            if (fixStringToRead().equals(ID)){
                raf.seek(raf.getFilePointer()-40);
                raf.writeChars(fixStringToWrite(""));
                raf.writeChars(fixStringToWrite(""));
                raf.writeChars(fixStringToWrite(""));
                raf.writeChars(fixStringToWrite(""));
                raf.writeChars(fixStringToWrite(""));
                raf.writeInt(0);
                raf.writeInt(0);
            }
        }
        System.out.println("Flight with ID " + ID + " not found.");

        Main.pressEnterToContinue();
        Admin.adminMenu();
    }

    //////////// Method for printing flight schedules ///////////
    public void displayFlights() throws IOException {
        Flightschedules flightschedules = new Flightschedules();
        flightschedules.readAllFlights();
        Main.pressEnterToContinue();
        Admin.adminMenu();
    }
    ///////////////////////////////
    public void readAllFlights()throws IOException{
        for (int i = 0; i < raf.length(); i+=208) {
            raf.seek(i);
            Flight flight = readFlightFromFile();
            if (!flight.getFlightid().isBlank() && !flight.getOrigin().isBlank() && !flight.getDestination().isBlank()
                    && !flight.getDate().isBlank() && !flight.getTime().isBlank())
            {
                System.out.println(flight);
            }
        }
    }
    /////////////////// Method for Writing in User File////////////
    public void writeFlightToFile() throws IOException {
        System.out.println("Enter your flightId : ");
        String id = input.nextLine();
        System.out.println("Enter your origin : ");
        String origin = input.nextLine();
        System.out.println("Enter your destination : ");
        String destination = input.nextLine();
        System.out.println("Enter your date : ");
        String date = input.nextLine();
        System.out.println("Enter your time : ");
        String time = input.nextLine();
        System.out.println("Enter your price :");
        int price = input.nextInt();
        input.nextLine();
        System.out.println("Enter your seats :");
        int seats = input.nextInt();
        raf.seek(raf.length());
        raf.writeChars(fixStringToWrite(id));
        raf.writeChars(fixStringToWrite(origin));
        raf.writeChars(fixStringToWrite(destination));
        raf.writeChars(fixStringToWrite(date));
        raf.writeChars(fixStringToWrite(time));
        raf.writeInt(price);
        raf.writeInt(seats);

        if (origin.equals(destination)) {
            System.out.println("Same origin and destination ?!");
            System.out.println("Not possible");
            Main.pressEnterToContinue();
            addFlight();
        }
    }

    //////////////////  Method for Reading from Flights File//////////
    public Flight readFlightFromFile() throws IOException {
        String id = fixStringToRead();
        String origin = fixStringToRead();
        String destination = fixStringToRead();
        String date = fixStringToRead();
        String time = fixStringToRead();
        int price = raf.readInt();
        int seats = raf.readInt();
        return new Flight(id,origin,destination,date,time,price,seats);
    }
    //////////////////  Method for Fix String to Write ///////////
    private String fixStringToWrite(String str) {
        while (str.length() < FIX_SIZE) {
            str += " ";
        }
        return str.substring(0, FIX_SIZE);
    }

    ////////////////// Method for Fix String to Read ////////////
    private String fixStringToRead() throws IOException {
        String tmp = "";
        for (int i = 0; i < FIX_SIZE; i++) {
            tmp += raf.readChar();
        }
        return tmp.trim();
    }
    /////////////Method for Searching Flights According to Origin or Destination////////////
    public void searchFlights() throws IOException {
        System.out.println("Which feature you want to search for ?");
        System.out.println("1-Origin");
        System.out.println("2-Destination");
        int choice = input.nextInt();
        input.nextLine();
        switch (choice){
            case 1 : {
                System.out.println("Enter your Origin :");
                String origin = input.nextLine();
                for (int i = 40; i <= raf.length() - 168; i+=208) {
                    raf.seek(i);
                    if (fixStringToRead().equals(origin)) {
                        raf.seek(raf.getFilePointer()-80);
                        System.out.println(readFlightFromFile());
                    }
                }
                break;
            }
            case 2 : {
                System.out.println("Enter your Destination :");
                String destination = input.nextLine();
                for (int i = 80; i <= raf.length() - 128; i+=208) {
                    raf.seek(i);
                    if (fixStringToRead().equals(destination)) {
                        raf.seek(raf.getFilePointer()-120);
                        System.out.println(readFlightFromFile());
                    }
                }
                break;
            }
            default:
                System.out.println("Invalid option.");
        }
    }
}

