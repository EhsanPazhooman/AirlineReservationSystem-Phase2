import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Users.mainMenu();
    }
    ///////////////System Pause Method//////////////////////
    public static void pressEnterToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
}


