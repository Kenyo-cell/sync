import java.util.ArrayList;
import java.util.List;

public class Shop {
    List<Object> cars;

    public Shop() {
        cars = new ArrayList<>();
    }

    public void sellCar() throws InterruptedException {
        synchronized (cars) {
            System.out.println(Thread.currentThread().getName() + " is up to buy new car");
            if (cars.isEmpty()) {
                System.out.println("There is no cars");
                cars.wait();
            }
        }
        System.out.printf("%s bought new car\n", Thread.currentThread().getName());
        cars.remove(0);
    }

    public void giveCreatedCars() {
        synchronized (cars) {
            System.out.println(Thread.currentThread().getName() + " creates new car");
            cars.add(new Object());
            cars.notify();
        }
    }
}
