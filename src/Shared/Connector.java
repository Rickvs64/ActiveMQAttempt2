package Shared;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Connector {

    // JMS server URL, default broker URL is tcp://localhost:61616
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private Session session;

    // Subscriber to notify upon receiving messages
    private ISubscriber subscriber;

    public Connector(ISubscriber newSub, String listenChannel) {
        try {
            subscriber = newSub;
            connect(listenChannel);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    // Connect and set listen channel/topic
    private void connect(String listenChannel) throws JMSException {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");

        // Getting JMS connection from the server and starting it
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Creating a non transactional session to send/receive JMS message
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // This one is only used for LISTENING
        Destination destination = session.createTopic(listenChannel);

        // MessageConsumer is used for receiving (consuming) messages
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message msg) {
                try {
                    receiveGenericMessage(msg);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // Send message
    public void sendMessage(CustomMessage message, String sendChannel) throws JMSException {
        Destination destination = session.createTopic(sendChannel);

        // MessageProducer is used for sending messages to the queue.
        MessageProducer producer = session.createProducer(destination);
        ObjectMessage oMessage = session.createObjectMessage(message);

        // Here we are sending our message!
        producer.send(oMessage);
    }

    // Receive generic message
    private void receiveGenericMessage(Message msg) throws JMSException {
        // Here we receive the message.
        Message message = msg;

        // Now to quickly make sure it's the right type of message
        if (message instanceof ObjectMessage) {
            ObjectMessage oMessage = (ObjectMessage) message;

            CustomMessage customMessage = (CustomMessage) oMessage.getObject();

            // Only if this message is relevant to the receiver
            if (customMessage.getIntendedReceiver()== null || customMessage.getIntendedReceiver().equals(subscriber.getUniqueID())) {
                subscriber.onMessageReceived(customMessage);
            }
        }
    }
}
