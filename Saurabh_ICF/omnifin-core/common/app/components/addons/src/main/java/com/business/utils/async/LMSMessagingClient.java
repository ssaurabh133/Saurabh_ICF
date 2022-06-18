package com.business.utils.async;

import java.io.Serializable;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;

import org.apache.log4j.Logger;



public class LMSMessagingClient implements LMSMessagingConstants
{
	private static final Logger logger = Logger.getLogger(LMSMessagingClient.class.getName());
    public void sendObjectMessage(Serializable objectMessage, String queueName) throws Exception
    {
        //String destinationName = "queue/VoucherUploadQueue";
    	logger.info("In LMSMessagingClient----");
        Context ic = null;
        ConnectionFactory cf = null;
        Connection connection =  null;

        try {         
            ic = getInitialContext();

            cf = (ConnectionFactory)ic.lookup(QUEUE_CONNECTION_FACTORY);
            Queue queue = (Queue)ic.lookup(QUEUE_PREFIX+queueName);

            connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);
            connection.start();
            logger.info("Session---"+session);
            ObjectMessage message = session.createObjectMessage(objectMessage);
            publisher.send(message);

        }
        finally
        {         
            if(ic != null)
            {

                try
                { ic.close(); }
                catch(Exception e)
                {
                    throw e;
                }
            }

            closeConnection(connection);
        }
    }

    private void closeConnection(Connection con)
    {      
        try
        {
            if (con != null)
            {
                con.close();
            }         
        }
        catch(JMSException jmse)
        {
            System.out.println("Could not close connection " + con +" exception was " + jmse);
        }
    }

    public static Context getInitialContext( ) throws javax.naming.NamingException {

        Properties p = new Properties( );
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES," org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        return new javax.naming.InitialContext(p);
    }  
}

