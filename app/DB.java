package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DB {
    private static ArrayList<String> backup;
    static ArrayList<String> bd;
    private static Scanner sc;
    private static RandomNumber randNumber;

    public DB() throws FileNotFoundException {
        backup = new ArrayList<>();
        randNumber =  new RandomNumber();

        sc = new Scanner(new File("bd/bd.txt"));
        while (sc.hasNext()) {
            backup.add(sc.nextLine());
        }
    }

    public void setup() throws FileNotFoundException {
        bd = backup;
    }

    public static void readerRandomAccess() {
        int size = bd.size();
        for (int i = 0; i < 100; i++) {
            String string = bd.get(randNumber.generate(size));
        }
    }

    public static void writerRandomAccess() {
        int size = bd.size();
        for (int i = 0; i < 100; i++) {
            bd.set(randNumber.generate(size), "MODIFIED");
        }
    }
}