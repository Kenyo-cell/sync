public class Main {

    public static final int CLIENTS_COUNT = 5;
    public static final int CLIENT_INTERVAL = 5000;

    public static void main(String[] args) throws InterruptedException {
        Restaurant restaurant = new Restaurant();

        for (int i = 0; i < CLIENTS_COUNT; i++) {
            Thread.sleep(CLIENT_INTERVAL);
            restaurant.addCustomer(new Customer("Customer " + (i + 1)));
        }
    }
}
