package app;

class ReaderWriter {
    int activeReaders = 0;
    boolean presentWriter = false;

    boolean writingCondition() {
        return activeReaders == 0 && !presentWriter;
    }

    /*verifica se pode ler*/
    boolean readingCondition() {
        return !presentWriter;
    }

    synchronized void startReading() {
        while (!readingCondition()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        activeReaders++;
    }

    synchronized void stopReading() {
        activeReaders--;
        notifyAll();
    }

    synchronized void startWriting() {
        while (!writingCondition()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        presentWriter = true;
    }

    synchronized void stopWriting() {
        presentWriter = false;
        notifyAll();
    }
}