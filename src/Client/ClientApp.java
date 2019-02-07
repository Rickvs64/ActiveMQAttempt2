package Client;

import Shared.*;

import javax.jms.JMSException;
import java.util.Scanner;
import java.util.UUID;

public class ClientApp implements ISubscriber, Runnable {

    private String uniqueID = UUID.randomUUID().toString();
    private Connector connector;

    String input;

    @Override
    public void run() {
        initConnector();

        showIntro();
        awaitProductInput();
    }

    private void initConnector() {
        connector = new Connector(this, "ProductResults");
    }

    private void showIntro() {
        System.out.println("CLIENT app has been started.");
        System.out.println("Unique ID: " + uniqueID + ".");
        System.out.println("############################");
    }

    private String showAwaitingInput() {
        System.out.println();
        System.out.println("Please input what kind of product you're interested in: ");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void awaitProductInput() {
        input = showAwaitingInput();
        try {
            MessageProductSearch message = new MessageProductSearch(input);
            message.setSender(uniqueID);
            connector.sendMessage(message, "ProductSearches");
            System.out.println("Sent a request for product: " + input + ".");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new ClientApp();
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void onMessageReceived(CustomMessage message) {
        MessageProductResult productResult = (MessageProductResult) message;

        // Is the received message actually relevant? (not a reply to some outdated request)
        if (productResult.getProductName().equals(input)) {
            System.out.println("Received result from webshop " + productResult.getWebshop() + " for product: " + productResult.getProductName() + ".");

            if (productResult.getIsAvailable()) {
                System.out.println(productResult.getWebshop() + " has " + productResult.getAmountAvailable() + " " + productResult.getProductName() + "s available for $" + productResult.getPrice() + " each.");
            }
            else {
                System.out.println(productResult.getWebshop() + " does NOT have any " + productResult.getProductName() + "s available...");
            }

            // Basically a restart.
            // Currently undesired as this "blocks" the program and prevents subsequent replies from coming in.
            // awaitProductInput();
        }
    }

    @Override
    public String getUniqueID() {
        return uniqueID;
    }
}