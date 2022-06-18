package com.business.ejb;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.cp.process.GenerateFinancialReport;
import org.apache.log4j.Logger;
import com.cp.vo.FinancialReportVo;
import com.cp.dao.FinancialReportDao;
import com.connect.DaoImplInstanceFactory;
import com.exception.OmniFinMarkForRollbackException;

@MessageDriven(name="FinancialReportMDB", activationConfig={
		@ActivationConfigProperty(propertyName="maxSession", propertyValue="1"),
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/OCRReportQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") 
})
public class FinancialReportMDB implements MessageListener {
	 private static final Logger logger = Logger.getLogger(FinancialReportMDB.class.getName());
	 public FinancialReportMDB() {
		// TODO Auto-generated constructor stub
	}
	 
	 @Override
		public void onMessage(Message message) {
			logger.info("FinancialReportMDB MDB onMessage-----------");
			try{
			ObjectMessage objMessage = (ObjectMessage) message;
			Object obj = objMessage.getObject();
			FinancialReportVo vo = (FinancialReportVo)obj;
			generateReportProcess(vo);
			
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
		
		void generateReportProcess(FinancialReportVo vo)
		{
			logger.info("in generateReportProcess ------------");
			String fileName = GenerateFinancialReport.geneareteReport(vo);
		   FinancialReportDao dao =(FinancialReportDao) DaoImplInstanceFactory.getDaoImplInstance(FinancialReportDao.IDENTITY) ;
		   vo.setReportType(fileName);
		   dao.saveReportLink(vo);
			
		}
}
