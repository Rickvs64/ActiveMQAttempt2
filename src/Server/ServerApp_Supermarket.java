package Server;

import Server.ServerApp;

import java.util.ArrayList;

public class ServerApp_Supermarket extends ServerApp {

    @Override
    protected String getWebshopName() {
        return "Albert Heijn";
    }

    @Override
    protected void initProductList() {
        availableProducts = new ArrayList<>();
        availableProducts.add("Apple");
        availableProducts.add("Banana");
        availableProducts.add("Orange");
        availableProducts.add("Kiwi");
        availableProducts.add("Pear");
        availableProducts.add("Strawberry");
        availableProducts.add("Berry");
    }

    public static void main(String[] args) {
        Runnable runnable = new ServerApp_Supermarket();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
