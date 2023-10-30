import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
    private int buffer[];
    private int inLogicPointer;
    private int outLogicPointer;
    private ReentrantLock mutex;
    private Semaphore notFull;
    private Semaphore notEmpty;

    /**
     * 
     * @param bufferlenght, lenght of the array
     */
    public BoundedBuffer(int bufferLenght) {
        this.buffer = new int[bufferLenght];
        this.inLogicPointer = 0;
        this.outLogicPointer = 0;
        this.mutex = new ReentrantLock();
        this.notFull = new Semaphore(bufferLenght);
        this.notEmpty = new Semaphore(0);
    }

    /**
     * Producer's Method
     * @param item, producted by the Producer
     */
    public insertItem(int item) {
        try {
            this.notFull.acquire();
            this.mutex.lock();
            /*
            * critical section
            */
            this.buffer[this.inLogicPointer] = item;
            this.inLogicPointer = (this.inLogicPointer + 1) % this.buffer.length;
            /*
            * end of the critical section
            */
            this.mutex.unlock();
            this.notEmpty.release();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Consumers method
     */
    public int removeItem() {
        int item = -1;
        try {
            this.notEmpty.acquire();
            this.mutex.lock();
            /*
             * critical section
             */
            item = this.buffer[this.outLogicPointer];
            this.outLogicPointer = (this.outLogicPointer + 1) % this.buffer.length;
            /*
             * end of the critical section
             */
            this.mutex.unlock();
            this.notFull.release();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
        return item;
    }
}