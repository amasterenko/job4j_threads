package ru.job4j.multithreads;

/**
 * Master-slave barrier class. It makes the slave thread always second.
 *
 * @author AndrewMs
 * @version 1.0
 */
public class MasterSlaveBarrier {
    private boolean iSMasterLocked = false;
    private boolean isSlaveLocked = true;

    public synchronized void tryMaster() throws InterruptedException {
        while (iSMasterLocked) {
            wait();
        }
        isSlaveLocked = true;
    }

    public synchronized void trySlave() throws InterruptedException {
        while (isSlaveLocked) {
            wait();
        }
        iSMasterLocked = true;
    }

    public synchronized void doneMaster() {
        isSlaveLocked = false;
        notify();
    }

    public synchronized void doneSlave() {
        iSMasterLocked = false;
        isSlaveLocked = true;
        notify();
    }
}
