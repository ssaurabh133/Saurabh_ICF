package com.business.ejb;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import com.connect.DaoImplInstanceFactory;
import com.cp.vo.CommonDealVo;
import com.cp.vo.MDBVO;
import com.exception.OmniFinMarkForRollbackException;
import com.cp.vo.UnderwritingDocUploadVo;
import com.cp.process.FileUploadMDBProcessTemplate;
@MessageDriven(name="FileUploadOCRMDB", activationConfig={
		@ActivationConfigProperty(propertyName="maxSession", propertyValue="1"),
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/OCRUploadQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") 
})
public class FileUploadOCRMDB implements MessageListener{

	 private static final Logger logger = Logger.getLogger(FileUploadOCRMDB.class.getName());

		 
	    /**
	     * Default constructor. 
	     */
	    public FileUploadOCRMDB() {
	    }
		
		@Override
		public void onMessage(Message message) {
			logger.info("FileUploadOCRMDB MDB onMessage-----------");
			try{
			ObjectMessage objMessage = (ObjectMessage) message;
			Object obj = objMessage.getObject();
			MDBVO mdbvo = (MDBVO)obj;
			UnderwritingDocUploadVo vo = mdbvo.getUnderwriterUploadVo(mdbvo);
			copyDataToTempTable(vo);
			
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
		
		void copyDataToTempTable(UnderwritingDocUploadVo vo)
		{
			logger.info("in eligibilityCalculation ------------");
		    boolean flag =  FileUploadMDBProcessTemplate.saveFileUploadDataProcess(vo);
		    if(flag && vo.getDocType().equalsIgnoreCase("BS"))
		    {
		    	FileUploadMDBProcessTemplate.bankDetailsDataValidation(vo);
		    }
			
		}

	}