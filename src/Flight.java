import java.text.MessageFormat;
import java.util.ArrayList;

public class Flight {
    private String flightId;
    private String origin;
    private String destination;
    private String date;
    private String time;
    private int price;
    private int seats;


    public Flight(String flightId, String origin, String destination,String date,String time,int price,int seats) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.seats=seats;
    }

    /////////////// Flightid getter & setter ////////////////
    public String getFlightid() {
        return flightId;
    }

    public void setFlightid(String flightid) {
        this.flightId = flightId;
    }

    ////////////// Origin getter & setter /////////////////
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /////////// Destination getter & setter ///////////
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    //////////// Date getter & setter //////////////
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    //////////// Time getter & setter //////////////
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    ///////////// Price getter & setter //////////////
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    ///////////// Seats getter & setter //////////////
    public int getSeats() {
        return seats;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }

    //////////////////////////////////////////////////
    @Override
    public String toString() {
        return "Flight {" +
                "flightId='" + flightId + '\'' +
                ", origin='" + origin+ '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", price=" + price +
                ", seats=" + seats +
                '}';
    }
}
