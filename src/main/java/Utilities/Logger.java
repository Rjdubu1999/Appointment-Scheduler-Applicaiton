package Utilities;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;


public class Logger {
    private static final String FILENAME = "log.txt";

    public Logger(){}

    public static void log (String username, boolean success){
        try(FileWriter fileWriter = new FileWriter(FILENAME, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter)){
            printWriter.println(ZonedDateTime.now() + " " + username + (success? " Success" : "Failure"));
        }catch (IOException q) {
            System.out.println("Logger Error " + q.getMessage());
        }

    }


}
