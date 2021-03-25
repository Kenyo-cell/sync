public class Officiant extends Thread {
    private Restaurant restaurant;
    private Customer client;


    public Officiant(String name, Restaurant restaurant) {
        super(name);
        this.restaurant = restaurant;
        System.out.printf("%s is up to work\n", name);
    }

    private void searchCustomer() throws InterruptedException{
        System.out.printf("%s searching for clients\n", getName());
        client = restaurant.getFreeCustomer();
        if (client == null) return;
        System.out.printf("%s found %s\n", getName(), client.getName());
        client.setOfficiant(this);
    }

    public void transferOrder(Object order) {
        restaurant.makeOrder(order);
    }

    public void getReadyDish() throws InterruptedException{
        if (client == null) return;
        client.setDish(restaurant.getDish());
    }

    public void letCustomerGo() {
        restaurant.customerServiceDone();
    }

    @Override
    public void run() {
        while (!restaurant.isLast()) {
            try {
                searchCustomer();
                getReadyDish();
            } catch (InterruptedException e) { }
        }
    }
}
