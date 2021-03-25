import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private Cooker cooker;
    private List<Officiant> officiants;
    private List<Customer> customers;
    private List<Object> dishes;
    private Integer clientCounter;

    public Restaurant() {
        cooker = new Cooker(this);
        officiants = new ArrayList<>();
        customers = new ArrayList<>();
        dishes = new ArrayList<>();
        clientCounter = 0;

        officiants.add(new Officiant("Garry", this));
        officiants.add(new Officiant("Kim", this));
        officiants.add(new Officiant("Hardy", this));

        cooker.start();
        for (Officiant of : officiants) {
            of.start();
        }
    }

    public void addDish(Object dish) {
        synchronized (dishes) {
            dishes.add(dish);
            dishes.notify();
        }
    }

    public Object getDish() throws InterruptedException {
        synchronized (dishes) {
            if (dishes.isEmpty()) dishes.wait();
        }
        return dishes.remove(0);
    }

    public void addCustomer(Customer c) {
        c.start();
        synchronized (customers) {
            customers.add(c);
            customers.notify();
        }
    }

    public void customerServiceDone() {
        synchronized (clientCounter) {
            clientCounter++;
        }
        if (isLast()) {
            cooker.interrupt();
            officiants.forEach(Thread::interrupt);
        }
    }

    public Customer getFreeCustomer() throws InterruptedException{
        synchronized (customers) {
            if (customers.isEmpty()) customers.wait();
        }

        return customers.isEmpty() ? null : customers.remove(0);
    }

    public void makeOrder(Object order) {
        cooker.addOrder(order);
    }

    public boolean isLast() {
        return clientCounter == Main.CLIENTS_COUNT;
    }
}
