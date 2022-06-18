package com.business.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import com.connect.DaoImplInstanceFactory;
import com.cp.vo.UnderwritingDocUploadVo;
import com.exception.OmniFinMarkForRollbackException;
import com.cp.process.EligibilityCalculationProcess;
//import com.cp.eCalMacroPOC.RunMacro;


@MessageDriven(name="OCRMDB", activationConfig={
		@ActivationConfigProperty(propertyName="maxSession", propertyValue="1"),
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/OCRExcelQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") 
})
public class OCRMDB implements MessageListener{

 private static final Logger logger = Logger.getLogger(OCRMDB.class.getName());

	 
    /**
     * Default constructor. 
     */
    public OCRMDB() {
    }
	
	@Override
	public void onMessage(Message message) {
		logger.info("OCRMDB MDB onMessage-----------");
		try{
		ObjectMessage objMessage = (ObjectMessage) message;
		Object obj = objMessage.getObject();
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)obj;
		eligibilityCalculation(vo);
		
		}catch(OmniFinMarkForRollbackException e){
    		throw e;
    	}
    	catch (Exception e)
    	{
			e.printStackTrace();
			logger.info(e);
			throw new OmniFinMarkForRollbackException(e);
		}
	}
	
	void eligibilityCalculation(UnderwritingDocUploadVo vo)
	{
		logger.info("in eligibilityCalculation ------------");
	    boolean flag =  EligibilityCalculationProcess.readAndWriteFile(vo);
		if(flag)
		{
		//	RunMacro.executeMacro(vo.getDocPath(),vo);
		}
	}

}
