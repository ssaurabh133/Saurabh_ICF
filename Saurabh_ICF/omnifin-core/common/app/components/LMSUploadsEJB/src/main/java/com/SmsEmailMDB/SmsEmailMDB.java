package com.SmsEmailMDB;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;

import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.communication.engn.daoImplMySql.SmsDAOImpl;

import com.cm.vo.SmsEmailDTO;

import org.apache.log4j.Logger;



import com.exception.OmniFinMarkForRollbackException;

/**
* Message-Driven Bean implementation class for: SmsEmailMDB
*
*/

@MessageDriven(name = "SmsEmailMDB", activationConfig = {
		@ActivationConfigProperty(propertyName="maxSession",propertyValue="1"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
      @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/SmsEmailQueue"),
      @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class SmsEmailMDB implements MessageListener
{

	private static final Logger logger = Logger.getLogger(SmsEmailMDB.class.getName());
	
	 
	public SmsEmailMDB() 
    {
      
    }
	
	
    public void smsEmailSearching(SmsEmailDTO dto)
    {
    	String userId= dto.getUserId();
    	String bDate= dto.getBusiness_date();
    		SmsDAOImpl impl = new SmsDAOImpl();
    		impl.proForSmsEmailBOD(userId,bDate);
    }
    public void onMessage(Message message) 
    {
    	
    	
    	
    	logger.info("INSIDE onMessage(Message message)-------------------------------------------");
    	
    	try
    	{
    		ObjectMessage objMessage = (ObjectMessage) message;
    		Object obj = objMessage.getObject();
    		SmsEmailDTO dto = (SmsEmailDTO)obj;
    		
    		logger.info("Business Date ======"+ dto.getBusiness_date());
    		logger.info("USERID====="+ dto.getUserId());
    		smsEmailSearching(dto);
    		
    		
    	}
    	catch(OmniFinMarkForRollbackException e){
    		throw e;
    	}
    	catch (Exception e)
    	{
			e.printStackTrace();
			logger.info(e);
			throw new OmniFinMarkForRollbackException(e);
		}
        
        logger.info("break point");
    	logger.info("ONMESSAGE COMPLETED ...........................................................");
    }

}
