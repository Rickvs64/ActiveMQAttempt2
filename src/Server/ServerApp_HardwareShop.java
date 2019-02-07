package Server;

import java.util.ArrayList;

public class ServerApp_HardwareShop extends ServerApp {

    @Override
    protected String getWebshopName() {
        return "Mediamarkt";
    }

    @Override
    protected void initProductList() {
        availableProducts = new ArrayList<>();
        availableProducts.add("Keyboard");
        availableProducts.add("Mouse");
        availableProducts.add("Laptop");
        availableProducts.add("Computer");
        availableProducts.add("PC");
        availableProducts.add("Phone");
        availableProducts.add("Smartphone");
        availableProducts.add("Monitor");
        availableProducts.add("Screen");
        availableProducts.add("Camera");
        availableProducts.add("DVD Player");
        availableProducts.add("Video Player");
        availableProducts.add("Videocard");
        availableProducts.add("GPU");
        availableProducts.add("Processor");
        availableProducts.add("CPU");
        availableProducts.add("RAM");
        availableProducts.add("Motherboard");
    }

    public static void main(String[] args) {
        Runnable runnable = new ServerApp_HardwareShop();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
