import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Tickets{

    static Scanner scanner = new Scanner(System.in);
    private RandomAccessFile ticketRaf = new RandomAccessFile("Tickets.dat","rw");
    private final static int FIX_SIZE =  20;
    private RandomAccessFile userRaf = new RandomAccessFile("Users.dat","rw");
    private RandomAccessFile flightRaf = new RandomAccessFile("FLights.dat", "rw");
    static String inputId;
    Random random = new Random();
    int ticketId = random.nextInt(9000) + 1000;

    public Tickets() throws FileNotFoundException {
    }

    ///////////////Method for buying ticket///////////////////////
    public static void bookingTicket() throws IOException {
        Flightschedules flightschedules =new Flightschedules();
        //flightschedules.readAllFlights();
        Ticket ticket = new Ticket();
        Tickets tickets = new Tickets();
        tickets.reserveFlight();
        ticket.writeTicketToFile(tickets.ticketRaf,tickets.buyTicket());
        System.out.println("Your ticket has been purchased successfully");
    }
    //////////////////Method for printing purchased tickets////////////
    public void printTickets() throws IOException {
        Ticket ticket = new Ticket();
        for (int i=0; i < ticketRaf.length(); i+=248) {
            ticketRaf.seek(i);
            if (fixStringToRead(ticketRaf).equals(Users.input_username)){
                ticketRaf.seek(ticketRaf.getFilePointer()-40);
                System.out.println(ticket.readTicketFromFile(ticketRaf));
            }
        }
    }
    //////////////////Method for reserving flight ticket////////////////
    public void reserveFlight() throws IOException {
        for (int i = 0; i <= userRaf.length()-48 ; i+=48) {
            userRaf.seek(i);
            if (Users.input_username.equals(fixStringToRead(userRaf))&&Users.input_password==userRaf.readInt()){
                System.out.println("Enter the flightId you want to reserve :");
                inputId = scanner.nextLine();
                for (int j = 0; j < flightRaf.length() ; j+=208) {
                    flightRaf.seek(j);
                    if (inputId.equals(fixStringToRead(flightRaf))){
                        flightRaf.seek(flightRaf.getFilePointer()+160);
                        if (userRaf.readInt() < flightRaf.readInt()){
                            System.out.println("Please Charge your account");
                            Main.pressEnterToContinue();
                            Passenger.passengerMenu();
                        }
                        else if (userRaf.readInt() >= flightRaf.readInt()){
                            long userPos = userRaf.getFilePointer()-8;
                            long flightPos = flightRaf.getFilePointer()-8;
                            userRaf.seek(userPos);
                            flightRaf.seek(flightPos);
                            int newCharge = userRaf.readInt() - flightRaf.readInt();
                            userRaf.seek(userPos);
                            userRaf.writeInt(newCharge);
                            flightRaf.seek(flightPos+4);
                            int newSeats = flightRaf.readInt()-1;
                            flightRaf.seek(flightRaf.getFilePointer()-4);
                            flightRaf.writeInt(newSeats);
                        }
                    }
                }
            }
        }
    }
    ///////////////////////////////
    public Ticket buyTicket() throws IOException {
        for (int i = 0; i <= userRaf.length() - 48; i += 48) {
            userRaf.seek(i);
            if (Users.input_username.equals(fixStringToRead(userRaf)) && Users.input_password == userRaf.readInt()) {
                userRaf.seek(userRaf.getFilePointer()-44);
                String passengername = fixStringToRead(userRaf);
                for (int j = 0; j < flightRaf.length(); j += 208) {
                    flightRaf.seek(j);
                    if (inputId.equals(fixStringToRead(flightRaf))) {
                        flightRaf.seek(flightRaf.getFilePointer()-40);
                        String flightId = fixStringToRead(flightRaf);
                        String origin = fixStringToRead(flightRaf);
                        String destination = fixStringToRead(flightRaf);
                        String date = fixStringToRead(flightRaf);
                        String time = fixStringToRead(flightRaf);
                        int price = flightRaf.readInt();
                        flightRaf.close();
                        Ticket ticket = new Ticket(passengername,flightId,origin,destination,date,time,price);
                        ticket.setTicketId(ticketId);
                        return ticket;
                    }
                }
            }
        }
        return null;
    }
    //////////////////Method for removing tickets////////////////////////
    public void removeTicket() throws IOException {
        System.out.println("Enter the ticket-id you want to remove ?");
        int Id = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < ticketRaf.length(); i += 248) {
            ticketRaf.seek(i);
            if (fixStringToRead(ticketRaf).equals(Users.input_username)) {
                ticketRaf.seek(ticketRaf.getFilePointer() + 204);
                if (ticketRaf.readInt() == Id) {
                    ticketRaf.seek(ticketRaf.getFilePointer() - 8);
                    int refund = ticketRaf.readInt();
                    ticketRaf.seek(ticketRaf.getFilePointer()-204);
                    String flightid = fixStringToRead(ticketRaf);
                    for (int j = 0; j < userRaf.length() ; j+=48) {
                        userRaf.seek(j);
                        if (fixStringToRead(userRaf).equals(Users.input_username)&&userRaf.readInt()==Users.input_password){
                            int price = userRaf.readInt();
                            price += refund;
                            userRaf.seek(userRaf.getFilePointer()-4);
                            userRaf.writeInt(price);
                        }
                    }
                    for (int k =0; k < flightRaf.length();k+=208){
                        flightRaf.seek(k);
                        if (fixStringToRead(flightRaf).equals(flightid)){
                            flightRaf.seek(flightRaf.getFilePointer()+164);
                            int seat = flightRaf.readInt();
                            int newseat = seat + 1;
                            flightRaf.seek(flightRaf.getFilePointer()-4);
                            flightRaf.writeInt(newseat);
                        }
                    }
                    ticketRaf.seek(ticketRaf.getFilePointer()-80);
                    ticketRaf.writeChars(fixStringToWrite(""));
                    ticketRaf.writeChars(fixStringToWrite(""));
                    ticketRaf.writeChars(fixStringToWrite(""));
                    ticketRaf.writeChars(fixStringToWrite(""));
                    ticketRaf.writeChars(fixStringToWrite(""));
                    ticketRaf.writeChars(fixStringToWrite(""));
                    ticketRaf.writeInt(0);
                    ticketRaf.writeInt(0);
                }
            }
        }
    }
    //////////////////  Method for Fix String to Write ///////////
    private String fixStringToWrite(String str){
        while (str.length()<FIX_SIZE){
            str += " ";
        }
        return str.substring(0,FIX_SIZE);
    }
    ////////////////// Method for Fix String to Read ////////////
    private String fixStringToRead(RandomAccessFile rFile) throws IOException {
        String tmp ="";
        for (int i = 0; i < FIX_SIZE; i++) {
            tmp += rFile.readChar();
        }
        return tmp.trim();
    }
}
