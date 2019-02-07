package Shared;

/**
 * MANUAL listener
 * Used by executable/runnable classes to tell the Connector
 * they wish to be notified upon receiving new messages.
 */
public interface ISubscriber {
    void onMessageReceived(CustomMessage message);
    String getUniqueID();
}