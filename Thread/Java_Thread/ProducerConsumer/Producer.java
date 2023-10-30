import java.util.Random;

public class Producer extends Thread {
    private BoundedBuffer buffer;
    private Random num;
    private String threadName;

    /**
     * 
     * @param buffer, pointer to the Object, Aggregations
     */
    public Producer(BoundedBuffer buffer, String name) {
        this.buffer = buffer;
        this.num = new Random();
        this.threadName = name;
    }

    @Override
    public void run() {
        for (int index = 0; index < 200; index++) {
            this.buffer.insertItem(index);
            System.out.println("New Item ! : " + index);

            try {
                Thread.sleep(10 + this.num.nextInt(491));
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
        System.out.println("Il Thread " + this.getName() + "e' terminato");
    }

}
