package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class IOSearcher {
    public static boolean search(String word,String... files) {
        for (String file : files) {
            try {
                Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
                while (scanner.hasNext()) {
                    if (word.equals(scanner.next()))
                        return true;
                }
                scanner.close();
            }
            catch (FileNotFoundException e){
                System.out.println("File not found:" + e.getMessage());
            }
        }
        return false;
    }
}
