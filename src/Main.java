import utils.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String inter = null, fname = null;
        Scanner in = new Scanner(System.in);
        System.out.print("Input python interpreter path(or just 'python'): ");
        if (in.hasNextLine())
            inter = in.nextLine();
        System.out.print("Input .csv file: ");
        if (in.hasNextLine())
            fname = in.nextLine();
        try {
            PyProcess pp = new PyProcess(inter, fname);
            DataFrameViewer df = pp.getData();
            df.draw();
        }
        catch(NumberFormatException | IOException ex) {
            System.out.print("Python unexpected error (make sure pandas installed for this python interpreter)");
        }
        catch(ProcessException ex) {
            ex.printMessage();
        }
    }
}

