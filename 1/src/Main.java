public class Main {

    public static Shop shop;
    public static final int MAX_CLIENTS_CARS = 10;
    public static final int CLIENT_FREQUENCY = 1000;

    public static void main(String[] args) throws InterruptedException {
	    shop = new Shop();
	    Manufacturer m = new Manufacturer();
        new Thread(m::create, "Manufacturer").start();
	    for (int i = 0; i < MAX_CLIENTS_CARS; i++) {
	        Customer c = new Customer();
            new Thread(c::buy, "Customer " + (i + 1)).start();
            Thread.sleep(CLIENT_FREQUENCY);
        }
    }
}
