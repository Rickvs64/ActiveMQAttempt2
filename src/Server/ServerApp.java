package Server;

import Shared.*;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerApp implements ISubscriber, Runnable {

    private String uniqueID = UUID.randomUUID().toString();
    private Connector connector;

    protected List<String> availableProducts;

    @Override
    public void run() {
        initConnector();
        initProductList();

        showIntro();
        showWaiting();
    }

    private void initConnector() {
        connector = new Connector(this, "ProductSearches");
    }

    private void showIntro() {
        System.out.println("SERVER app has been started.");
        System.out.println("This is " + getWebshopName());
        System.out.println("############################");
        System.out.println();
    }

    private void showWaiting() {
        System.out.println("Awaiting requests...");
    }

    protected void initProductList() {
        availableProducts = new ArrayList<>();
        availableProducts.add("Example");
    }

    private Boolean isProductAvailable(String productName) {
        return availableProducts.contains(productName);
    }

    protected String getWebshopName() {
        return "Test webshop";
    }

    @Override
    public void onMessageReceived(CustomMessage message) {
        if (message instanceof MessageProductSearch) {
            MessageProductSearch productSearch = (MessageProductSearch) message;

            System.out.println("Received request for product: " + productSearch.getProductName());
            System.out.println();

            if (isProductAvailable(productSearch.getProductName())) {
                System.out.println(getWebshopName() + " has this product available!");
                System.out.println("Sending response...");
                try {
                    MessageProductResult reply = new MessageProductResult(productSearch.getProductName(), getWebshopName(), true, 1, 1.0d);
                    reply.setIntendedReceiver(productSearch.getSender());
                    connector.sendMessage(reply, "ProductResults");
                    System.out.println("Response sent.");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println(getWebshopName() + " does NOT have this product available...");
                System.out.println("Sending response...");
                try {
                    MessageProductResult reply = new MessageProductResult(productSearch.getProductName(), getWebshopName(), false, 0, 0.0d);
                    reply.setIntendedReceiver(productSearch.getSender());
                    connector.sendMessage(reply, "ProductResults");
                    System.out.println("Response sent.");
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public String getUniqueID() {
        return uniqueID;
    }
}
