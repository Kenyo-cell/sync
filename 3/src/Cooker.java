import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cooker extends Thread {
    private final int COOK_TIME = 8000;
    private List<Object> orders;
    private Restaurant restaurant;
    private Lock lock;
    private Condition condition;

    public Cooker(Restaurant restaurant) {
        orders = new ArrayList<>();
        this.restaurant = restaurant;
        System.out.println("The cooker is up to work");
        lock = new ReentrantLock(true);
        condition = lock.newCondition();
    }

    public void addOrder(Object order) {
        lock.lock();
        try {
            orders.add(order);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }


    public void cook() {
        lock.lock();
        try {
            if (orders.isEmpty()) condition.await();
            System.out.println("The cooker start cooking the order");
            Thread.sleep(COOK_TIME);
            System.out.println("Done cooking dish");
            restaurant.addDish(orders.remove(0));
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        while (!restaurant.isLast()) {
            cook();
        }
    }
}
