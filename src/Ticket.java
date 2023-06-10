import java.io.IOException;
import java.io.RandomAccessFile;

public class Ticket {
    private String flightId ;
    private String origin ;
    private String destination ;
    private String date ;
    private String time;
    private int price;
    private String passengerName;
    private int ticketId ;
    private final static int FIX_SIZE = 20;

    public Ticket(){};
    public Ticket(String passengerName,String flightId,String origin, String destination, String date, String time, int price) {
        this.passengerName = passengerName;
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    //////////Passenger name getter & setter////////////////
    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    /////////// FlightId getter & setter ///////////////
    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
    ////////// Origin getter & setter /////////////
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    ///////// Destination getter & setter //////////
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    ///////// Date getter & setter //////////
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    //////////Time getter & setter ////////
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    ///////////Price getter & setter///////
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    //////////TicketId getter & setter//////
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    ////////////////////////////////
    @Override
    public String toString() {
        return  "»▶ passengerName='" + passengerName + '\'' +
                ", ticketId='" + getTicketId() + '\'' +
                ",  flightId='" + flightId + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", price=" + price ;
    }
    ////////////////////////////////
    public void writeTicketToFile(RandomAccessFile raf,Ticket ticket) throws IOException{
        raf.seek(raf.length());
        raf.writeChars(fixStringToWrite(ticket.passengerName));
        raf.writeChars(fixStringToWrite(ticket.flightId));
        raf.writeChars(fixStringToWrite(ticket.origin));
        raf.writeChars(fixStringToWrite(ticket.destination));
        raf.writeChars(fixStringToWrite(ticket.date));
        raf.writeChars(fixStringToWrite(ticket.time));
        raf.writeInt(ticket.price);
        raf.writeInt(ticket.getTicketId());
    }
    ////////////////////////////////
    public Ticket readTicketFromFile(RandomAccessFile raf) throws IOException{
        String passengername = fixStringToRead(raf);
        String flightID = fixStringToRead(raf);
        String oriGin = fixStringToRead(raf);
        String destiNation = fixStringToRead(raf);
        String daTe = fixStringToRead(raf);
        String tiMe = fixStringToRead(raf);
        int prIce = raf.readInt();
        int ticketID = raf.readInt();
        Ticket ticket = new Ticket(passengername,flightID,oriGin,destiNation,daTe,tiMe,prIce);
        ticket.setTicketId(ticketID);
        return ticket;
    }
    ////////////////////////////////
    private String fixStringToWrite(String str){
        while (str.length()<FIX_SIZE){
            str += " ";
        }
        return str.substring(0,FIX_SIZE);
    }
    ////////////////////////////////
    private String fixStringToRead(RandomAccessFile raf) throws IOException{
        String tmp ="";
        for (int i = 0; i < FIX_SIZE; i++) {
            tmp += raf.readChar();
        }
        return tmp.trim();
    }
}
