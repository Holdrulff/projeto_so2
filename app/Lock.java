package app;

public class Lock {

    boolean isBlocked = false;
    Thread blockedBy = null;
    int blockedCounter = 0;


    public synchronized void block() throws InterruptedException {
        Thread threadAtual = Thread.currentThread();
        while (isBlocked && blockedBy != threadAtual) {
            wait();
        }
        isBlocked = true;
        blockedCounter++;
        blockedBy = threadAtual;
    }

    public synchronized void unblock() {
        if (Thread.currentThread() == this.blockedBy) {
            blockedCounter--;

            if (blockedCounter == 0) {
                isBlocked = false;
                notify();
            }
        }
    }
}