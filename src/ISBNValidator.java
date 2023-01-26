import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


/**
 * ISBN Validator class
 * A program to validate and sort ISBN numbers
 * @author 26prakash
 * @version 1.25.2023
 */


/**
 * simple constructor; initializes arrays
 */

public class ISBNValidator {
    private String[] validNums;
    private String[] invalidNums;
    private String filename;
    public ISBNValidator()  {
        filename = "isbn_files/isbn2.dat";
        int lines = 0;
        try {
            Scanner in = new Scanner(new File(filename));
            while(in.hasNext()) {
                lines++;
                in.nextLine();
            }
            in.close();
        }
        catch(Exception e)  {
            System.out.println(e.toString());
        }
        validNums = new String[lines];
        invalidNums = new String[lines];
    }

    /**
     * imports .dat file, calls isValidISBN method and stores Strings into
     * corresponding arrays
     */

    public void importData() {
        try {
            Scanner in = new Scanner(new File(filename));
            int validCount = 0, invalidCount = 0;
            while(in.hasNext()) {
                //System.out.println("im here");
                String isbn = in.nextLine();
                //System.out.println("why you no work" + isbn);
                if(isValidISBN(isbn)) {
                    //System.out.println("valid");
                    validNums[validCount++] = isbn;
                }
                else {
                    //System.out.println("invalid");
                    invalidNums[invalidCount++] = isbn;
                }
            }
            in.close();
        }
        catch(Exception e)  {
            System.out.println(e.toString());
        }
    }

    /**
     * @param isbn the isbn number supplied
     * @return true or false
     * determines validity of supplied ISBN number called inside importData
     */
    public boolean isValidISBN(String isbn) {
        int first = Integer.parseInt(isbn.split("-")[0]);
        if (first != 978 && first != 979)
            return false;

        String nIsbn = isbn.replaceAll("-", "");
        int modeq = 0;
        for(int i = 0; i < 13; i++) {
            if(i % 2 == 0) {
                modeq += nIsbn.charAt(i);
            }
            else{
                modeq += nIsbn.charAt(i) * 3;
            }
        }

        return modeq % 10 == 0;
    }
    /**
     * output the user-picked ISBN list or quit the application
     */
    public void runProgram() {
        Scanner scanner = new Scanner(System.in);
        while(true) {

            System.out.println("All ISBN data has been imported and validated. Would you like to:\n1) View all valid ISBN numbers\n2) View all invalid ISBN numbers\n3) Quit");
            int userIn = scanner.nextInt();
            if (userIn == 3)
                break;
            else if (userIn == 1) {
                System.out.println(Arrays.toString(validNums));
                for (int i = 0; i < validNums.length && validNums[i] != null; i++)
                    System.out.println(validNums[i]);
            } else if (userIn == 2) {
                System.out.println(Arrays.toString(invalidNums));
                for (int i = 0; i < invalidNums.length && invalidNums[i] != null; i++)
                    System.out.println(invalidNums[i]);
            } else
                System.out.println("Invalid selection, try again.");
        }
    }

    /**
     * main method of the ISBNValidator class
     * @param args command line arguments, if needed
     */
    public static void main(String[] args){
        ISBNValidator app = new ISBNValidator ();
        System.out.println("* ISBN Validator Program *");
        System.out.println("...Importing data...");
        app.importData(); // imports data from the text file
        app.runProgram(); // runs using a while loop; see examples
        System.out.println("* End of Program *");
    }
}