package com.example.wmq.jms;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.function.Consumer;

@Component
public class WMQListener {

    // System exit status value (assume unset value to be 1)
    private static int status = 1;
    private final ConnectionFactory connectionFactory;

    // Create variables for the connection to MQ
//    private static final String HOST = "localhost"; // Host name or IP address
//    private static final int PORT = 1414; // Listener port for your queue manager
//    private static final String CHANNEL = "DEV.APP.SVRCONN"; // Channel name
//    private static final String QMGR = "QM1"; // Queue manager name
//    private static final String APP_USER = "app"; // User name that application uses to connect to MQ
//    private static final String APP_PASSWORD = "passw0rd"; // Password that the application uses to connect to MQ
    private static final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from


    @Autowired
    public WMQListener(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void listenForMessage(final Consumer<String> messageConsumer) {

        // Variables
        JMSContext context = null;
        Destination destination = null;
        JMSConsumer consumer = null;

//        try {
//            // Create a connection factory
//            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
//            JmsConnectionFactory cf = ff.createConnectionFactory();
//
//            // Set the properties
//            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
//            cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
//            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
//            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
//            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
//            cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
//            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
//            cf.setStringProperty(WMQConstants.USERID, APP_USER);
//            cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);
//            //cf.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "*TLS12");

            // Create JMS objects
            context = connectionFactory.createContext();
            destination = context.createQueue("queue:///" + QUEUE_NAME);

            consumer = context.createConsumer(destination); // autoclosable
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println("\nReceived message:\n" + message);
                    try {
                        messageConsumer.accept(message.getBody(String.class));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
//            context.close();
//        } catch (JMSException jmsex) {
//            recordFailure(jmsex);
//        }

    } // end main()

    /**
     * Record this run as failure.
     *
     * @param ex
     */
    private static void recordFailure(Exception ex) {
        if (ex != null) {
            if (ex instanceof JMSException) {
                processJMSException((JMSException) ex);
            } else {
                System.out.println(ex);
            }
        }
        System.out.println("FAILURE");
        status = -1;
        return;
    }

    /**
     * Process a JMSException and any associated inner exceptions.
     *
     * @param jmsex
     */
    private static void processJMSException(JMSException jmsex) {
        System.out.println(jmsex);
        Throwable innerException = jmsex.getLinkedException();
        if (innerException != null) {
            System.out.println("Inner exception(s):");
        }
        while (innerException != null) {
            System.out.println(innerException);
            innerException = innerException.getCause();
        }
        return;
    }

}