public class Manufacturer {
    private final int DEVELOP_FREQUENCY = 3000;
    public void create() {
        for (int i = 0; i < Main.MAX_CLIENTS_CARS; i++) {
            try {
                Thread.sleep(DEVELOP_FREQUENCY);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            Main.shop.giveCreatedCars();
        }
    }
}
