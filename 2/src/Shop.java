import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Shop {
    List<Object> cars;
    ReentrantLock lock;
    Condition condition;

    public Shop() {
        cars = new ArrayList<>();
        lock = new ReentrantLock(true);
        condition = lock.newCondition();
    }

    public void sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " is up to buy new car");
            if (cars.isEmpty()) {
                System.out.println("There is no cars");
                condition.await();
            }
            System.out.printf("%s bought new car\n", Thread.currentThread().getName());
            cars.remove(0);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void giveCreatedCars() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " creates new car");
            cars.add(new Object());
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
