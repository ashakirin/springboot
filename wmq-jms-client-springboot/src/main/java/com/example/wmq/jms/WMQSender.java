/*
 * (c) Copyright IBM Corporation 2018
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.wmq.jms;


import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class WMQSender {

    // System exit status value (assume unset value to be 1)
    private static int status = 1;

    private final ConnectionFactory connectionFactory;


    // Create variables for the connection to MQ
    private static final String HOST = "localhost"; // Host name or IP address
    private static final int PORT = 1414; // Listener port for your queue manager
    private static final String CHANNEL = "DEV.APP.SVRCONN"; // Channel name
    private static final String QMGR = "QM1"; // Queue manager name
    private static final String APP_USER = "app"; // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = "passw0rd"; // Password that the application uses to connect to MQ
    private static final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

    @Autowired
    public WMQSender(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void sendMessage() {

        // Variables
        JMSContext context = null;
        Destination destination = null;
        JMSProducer producer = null;
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

            long uniqueNumber = System.currentTimeMillis() % 1000;
            TextMessage message = context.createTextMessage("Your lucky number today is " + uniqueNumber);

            producer = context.createProducer();
            producer.send(destination, message);
            System.out.println("Sent message:\n" + message);

//            consumer = context.createConsumer(destination); // autoclosable
//            String receivedMessage = consumer.receiveBody(String.class, 15000); // in ms or 15 seconds
//
//            System.out.println("\nReceived message:\n" + receivedMessage);

            context.close();

            recordSuccess();
//        } catch (JMSException jmsex) {
//            recordFailure(jmsex);
//        }

    } // end main()

    /**
     * Record this run as successful.
     */
    private static void recordSuccess() {
        System.out.println("SUCCESS");
        status = 0;
        return;
    }

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