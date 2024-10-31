package app;

import java.io.FileNotFoundException;

public class Reader implements Runnable{
    private Lock lock;
    private ReaderWriter controller;
    private int deploy;

    public Reader(int i, ReaderWriter controller, Lock lock, int deploy) throws FileNotFoundException {
        this.controller = controller;
        this.deploy = deploy;
        this.lock = lock;
    }

    @Override
    public void run() {
        if(deploy == 1) readerAndWriter();
        else if(deploy == 2)
            try {
                notReaderNorWriter();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void notReaderNorWriter() throws InterruptedException {
        lock.block();
        dbToSleep();
        lock.unblock();
    }

    private void readerAndWriter() {
        controller.startReading();
        dbToSleep();
        controller.stopReading();
    }

    private void dbToSleep() {
        DB.readerRandomAccess();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}