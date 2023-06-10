import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Users {
    static Scanner scanner = new Scanner(System.in);
    private static final int FIX_SIZE = 20;
    private RandomAccessFile raf = new RandomAccessFile("Users.dat","rw");
    static String input_username;
    static int input_password;

    public Users() throws FileNotFoundException {}

    //////////////Main Menu///////////////////
    public static void mainMenu() throws IOException {
        System.out.println("••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••");
        System.out.println("\t" + "\t" + "Welcome To Airline Reservation System" + "\t");
        System.out.println("••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••");
        System.out.println("\t" + "•••••••••••••••" + "Menu Options" + "••••••••••••••••••••••");
        System.out.println("Ⅰ" + '-' + "Sign Up");
        System.out.println("Ⅱ" + '-' + "Sign In");
        System.out.println("Ⅲ" + '-' + "Exit");

        int option = scanner.nextInt();

        scanner.nextLine();

        switch (option) {
            case 1:
                signUp();
                break;
            case 2:
                logIn(scanner);
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Invalid option.");
        }
    }

    //////////// Method for Sign Up ///////////////
    public static void signUp() throws IOException {
        Users users = new Users();
        users.writeUserToFile();
        System.out.println();
        System.out.println("Back to Main Menu ? ");
        System.out.println("1-Yes");
        System.out.println("2-No");

        int option = scanner.nextInt();
        switch (option) {
            case 1:
                mainMenu();
                break;
            case 2:
                System.exit(0);
        }
    }
    ////////////// Method for Sign in ///////////////
    public static void logIn(Scanner scanner) throws IOException {
        Users users = new Users();
        System.out.print("Enter username: ");
        input_username = scanner.nextLine();
        System.out.print("Enter password: ");
        input_password = scanner.nextInt();

        if (input_username.equals("ehsan")&&input_password==1381){
            System.out.println("Welcome Admin");
            System.out.println();
            //Admin.adminMenu();
        }

        if(users.checkLogin(input_username,input_password)){
            System.out.println("User"+" "+input_username+" "+"login successfully");
            //Passenger.passengerMenu();
        }

        if(!users.checkLogin(input_username, input_password)){
            System.out.println("Invalid username or password");
            Main.pressEnterToContinue();
            mainMenu();
        }
    }
    /////////Method for checking correct username & password////////
    public boolean checkLogin(String username,int password) throws IOException {
        boolean checkUserName = false;
        boolean checkPassword = false;

        for (int i = 0; i <= raf.length()-48; i+=48) {
            raf.seek(i);
            if (username.equals(fixStringToRead()))
                checkUserName = true;
        }
        raf.seek(0);
        for (int i = 40; i <= raf.length()-8 ; i+=48) {
            raf.seek(i);
            if (password==raf.readInt())
                checkPassword=true;
        }
        if (checkUserName && checkPassword){
            return true;
        }else {
            return false;
        }
    }
    ///////////////// Method for Changing Password ///////////////
    public void changePassword() throws IOException {
        System.out.println("Enter your current password :");
        int old_password = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter your new Password :");
        int new_password = scanner.nextInt();
        for (int i = 0; i<= raf.length()-48 ; i+=48) {
            raf.seek(i);
            if (input_username.equals(fixStringToRead())&&old_password==raf.readInt()){
                System.out.println(raf.getFilePointer());
                raf.seek(raf.getFilePointer()-4);
                raf.writeInt(new_password);
                System.out.println("Password has been changed");
                Main.pressEnterToContinue();
                Users.mainMenu();
            }
        }
    }
    //////////////////////////////////////////////
    public void readAllUsers() throws IOException{
        for (int i = 0; i < raf.length(); i+=48) {
            raf.seek(i);
            System.out.println(readUserFromFile());
        }
        raf.close();
    }
    /////////////////// Method for Writing in User File////////////
    public void writeUserToFile() throws IOException{
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        int password = scanner.nextInt();
        raf.seek(raf.length());
        raf.writeChars(fixStringToWrite(username));
        raf.writeInt(password);
        int charge = 0;
        raf.writeInt(charge);
        System.out.println("User "+ username + " registered successfully.");
    }
    //////////////////  Method for Reading from User File//////////
    public User readUserFromFile() throws IOException{
        String userName = fixStringToRead();
        int passWord = raf.readInt();
        int chaRge = raf.readInt();
        return new User(userName,passWord,chaRge);
    }
    //////////////////  Method for Fix String to Write ///////////
    private String fixStringToWrite(String str){
        while (str.length()<FIX_SIZE){
            str += " ";
        }
        return str.substring(0,FIX_SIZE);
    }
    ////////////////// Method for Fix String to Read ////////////
    private String fixStringToRead() throws IOException {
        String tmp ="";
        for (int i = 0; i < FIX_SIZE; i++) {
            tmp += raf.readChar();
        }
        return tmp.trim();
    }
    /////////////// Method for Charging account ////////////////
    public void addCharge() throws IOException {
        for (int i = 0; i <= raf.length() - 48; i += 48) {
            raf.seek(i);
            if (Users.input_username.equals(fixStringToRead()) && Users.input_password == raf.readInt()) {
                raf.seek(i+44);
                int currentCharge = raf.readInt();
                raf.seek(raf.getFilePointer()-4);
                System.out.println("How much do you want to charge your account ?");
                int inputCharge = scanner.nextInt();
                raf.writeInt(inputCharge+currentCharge);
                System.out.println("Your current charge is :");
                raf.seek(raf.getFilePointer()-4);
                System.out.println(raf.readInt());
            }
        }
    }
}

