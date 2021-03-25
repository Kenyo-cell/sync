import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Customer extends Thread {
    private Officiant officiant;
    private Object dish;
    private Lock lock;
    private Condition condition;
    private final int THINKING_TIME = 2000;
    private final int EATING_TIME = 10000;


    public Customer(String name) {
        super(name);
        System.out.printf("%s join the restaurant\n", getName());
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    private void makeOrder() throws InterruptedException {
        lock.lock();
        try {
            if (officiant == null) {
                System.out.printf("%s waiting for officiant\n", getName());
                condition = lock.newCondition();
                condition.await();
            }
            System.out.printf("%s thinking of order\n", getName());
            Thread.sleep(THINKING_TIME);
            officiant.transferOrder(new Object());
        } finally {
            lock.unlock();
        }
    }

    private void eat() throws InterruptedException {
        lock.lock();
        try {
            if (dish == null) {
                System.out.printf("%s waiting for dish\n", getName());
                condition = lock.newCondition();
                condition.await();
            }
            System.out.printf("%s start eating\n", getName());
            Thread.sleep(EATING_TIME);
            System.out.printf("%s left our restaurant\n", getName());
            officiant.letCustomerGo();
        } finally {
            lock.unlock();
        }
    }

    public void setDish(Object dish) {
        this.dish = dish;
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void setOfficiant(Officiant of) {
        officiant = of;
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void run() {
        try {
            makeOrder();
            eat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
