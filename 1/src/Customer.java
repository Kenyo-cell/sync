public class Customer {
    public void buy() {
        try {
            Main.shop.sellCar();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
