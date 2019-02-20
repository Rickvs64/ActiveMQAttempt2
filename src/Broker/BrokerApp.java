package Broker;

import Shared.Connector;
import Shared.CustomMessage;
import Shared.ISubscriber;
import Shared.MessageProductResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BrokerApp implements ISubscriber, Runnable {

    private String uniqueID = UUID.randomUUID().toString();
    private Connector connector;
    private List<MessageProductResult> responses;

    @Override
    public void onMessageReceived(CustomMessage message) {

    }

    @Override
    public String getUniqueID() {
        return uniqueID;
    }

    @Override
    public void run() {
        initConnector();
        responses = new ArrayList<MessageProductResult>();

        showIntro();
        showWaiting();
    }

    private void showWaiting() {
        System.out.println("Awaiting requests...");
    }

    private void showIntro() {
        System.out.println("BROKER app has been started.");
        System.out.println("This app will function as a bridge between CLIENT (customer) and multiple SERVERS (shops)");
        System.out.println("############################");
        System.out.println();
    }

    private void initConnector() {
        connector = new Connector(this, "ProductSearchesFromClient");
    }

    public static void main(String[] args) {
        Runnable runnable = new BrokerApp();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
