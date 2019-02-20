package Broker;

import Shared.Connector;
import Shared.CustomMessage;
import Shared.ISubscriber;
import Shared.MessageProductResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BridgeApp implements ISubscriber, Runnable {

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
        System.out.println("BRIDGE app has been started.");
        System.out.println("This app will function as an additional layer  inbetween CLIENT (customer) and multiple SERVERS (shops)");
        System.out.println("############################");
        System.out.println();
    }

    private void initConnector() {
        // connector = new Connector(this, "ProductSearchesFromClient");
    }

    public static void main(String[] args) {
        Runnable runnable = new BridgeApp();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
