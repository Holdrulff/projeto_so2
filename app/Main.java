package app;

import java.io.FileNotFoundException;

public class Main {
    private static Lock lock;
    private static RandomNumber randNumber;
    private static ReaderWriter controller;
    private static DB bd;

    private static final int proportions = 101;
    private static final int turnsEachProportion = 50;
    private static Thread[] threads;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        lock = new Lock();
        controller = new ReaderWriter();
        bd = new DB();

        for (int aux = 0; aux < 2; aux++) {
            long startingTime = System.currentTimeMillis();
            System.out.println("Implementation: " + (aux+1));
            for (int i = 0; i < proportions; i++) {
                int average = 0;
                for (int j = 0; j < turnsEachProportion; j++) {
                    bd.setup();
                    randNumber = new RandomNumber();
                    newThreads(i, aux+1);
                    long start = System.currentTimeMillis();
                    startThreads();
                    joinThreads();
                    long finish = System.currentTimeMillis();
                    average += finish - start;
                }
                average /= turnsEachProportion;
                System.out.println("Average - " + i + " writers and " + (100 - i) + " readers - " + average);
            }
            long endingTime = System.currentTimeMillis();
            System.out.println("Duration:  " + ((endingTime - startingTime) / 60000) + " minutes");
        }
    }

    private static void newThreads(int proportion, int deploy) throws FileNotFoundException {
        threads = new Thread[100];

        for (int i = 0; i < proportion; i++) {
            loop(new Writer(0, controller, lock, deploy));
        }
        for (int i = 0; i < 100 - proportion; i++) {
            loop(new Reader(0, controller, lock, deploy));
        }
    }

    private static void startThreads() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }

    private static void joinThreads() throws InterruptedException {
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }

    private static void loop(Runnable obj) {
        int randomNumber = randNumber.generate();
        threads[randomNumber] = new Thread(obj);
    }
}