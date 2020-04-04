package utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;

// Python process exception class
public class ProcessException extends Exception{
    BufferedReader error;
    public ProcessException(BufferedReader err){
        error = err;
    }

    public void printMessage() {
        String s;
        try {
            while ((s = error.readLine()) != null)
                System.out.print(s);
        }
        catch(IOException ex){}
    }
}
