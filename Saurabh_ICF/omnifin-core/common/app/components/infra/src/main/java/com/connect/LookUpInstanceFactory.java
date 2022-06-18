package com.connect;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;


public class LookUpInstanceFactory {
	
	private static final Logger logger = Logger.getLogger(LookUpInstanceFactory.class.getName());
	
	/*public static Object getLookUpInstance(String key,HttpServletRequest request)throws Exception
	{
		String beanName=null;
		String fullQualifiedInterface=null;
		
		Properties props = new Properties();
		props.load(request.getSession().getServletContext().getResourceAsStream("/WEB-INF/jndi.properties"));  			        
	    InitialContext ic = new InitialContext(props);
	    String applName=props.getProperty("enterprise.application.name");// Application Name
	    String moduleName=props.getProperty("enterprise.module.name"); // THIS IS THE NAME OF THE JAR WITH YOUR EJBs. Write its name here, without the .jar.
	    
	     
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("COMMONBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("common.master.bussiness.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("common.master.bussiness.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("CREDITPROCESSINGBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("credit.processing.master.bussiness.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("credit.processing.master.bussiness.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("CREDITMANAGEMENTBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("credit.management.bussiness.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("credit.management.bussiness.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEBTMANAGEMENTBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("debt.management.bussiness.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("debt.management.bussiness.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("LEADPROCESSINGBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("lead.processing.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("lead.processing.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEALPROCESSINGBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("deal.processing.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("deal.processing.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("COMMONFUNCTIONBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("common.function.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("common.function.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("CUSTOMERDETAILBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("customer.details.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("customer.details.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("REFRESHFLAGVALUEBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("refresh.flag.value");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("refresh.flag.value.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("MASTERBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("master.bussiness.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("master.bussiness.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("PAYOUTBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("payout.master.business.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("payout.master.business.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("LEGALMASTERBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("legal.master.business.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("legal.master.business.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("LEGALTRANSACTIONBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("legal.transaction.business.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("legal.transaction.business.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("REPOMASTERBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("repo.master.business.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("repo.master.business.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("REPOTRANSACTIONBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("repo.transaction.business.session.bean");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         fullQualifiedInterface = props.getProperty("repo.transaction.business.session.bean.remote"); // FULLY QUALIFIED NAME OF THE REMOTE CLASS (interface).
	    }
	    
	    
	    
        String lookUpPath="ejb:"+CommonFunction.checkNull(applName)+ CommonFunction.checkNull(moduleName)+ CommonFunction.checkNull(beanName) + "!" + CommonFunction.checkNull(fullQualifiedInterface);
        logger.info("Path for look up Business Impl Class "+lookUpPath);
        Object ob=ic.lookup(lookUpPath);
        lookUpPath=null;
        applName=null;
        moduleName=null;
        beanName=null;
        fullQualifiedInterface=null;
		return ob;
	}*/
	public static Object getLookUpInstance(String key,HttpServletRequest request)throws Exception
	{
		String beanName=null;
		
		
		Properties props = new Properties();
		props.load(request.getSession().getServletContext().getResourceAsStream("/WEB-INF/jndi.properties"));  			        
	    InitialContext ic = new InitialContext(props);
	    String applName=props.getProperty("enterprise.application.name");// Application Name
	    
	    
	     
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("COMMONBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("common.master.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("CREDITPROCESSINGBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("credit.processing.master.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	        
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("CREDITMANAGEMENTBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("credit.management.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEBTMANAGEMENTBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("debt.management.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	        
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("LEADPROCESSINGBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("lead.processing.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	        
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEALPROCESSINGBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("deal.processing.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("COMMONFUNCTIONBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("common.function.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("CUSTOMERDETAILBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("customer.details.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	        
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("REFRESHFLAGVALUEBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("refresh.flag.value.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	        
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("MASTERBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("master.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("PAYOUTBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("payout.master.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("LEGALMASTERBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("legal.master.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("LEGALTRANSACTIONBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("legal.transaction.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("REPOMASTERBUSINESSSESSIONBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("repo.master.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("REPOTRANSACTIONBUSINESSSESSIONBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("repo.transaction.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }

	    //Code start by Manish Baranwal
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("FUNDFLOWANALYSISBUSINESSREMOTE"))
	    {
	    	 beanName = props.getProperty("fund.flow.analysis.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }

	 
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEALBUYERSUPPLIERBUSINESS"))
	    {
	    	 beanName = props.getProperty("buyer.supplier.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	 
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("CIBILANALYSISBUSINESSREMOTE"))
	    {
	    	 beanName = props.getProperty("cibil.analysis.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("LOANDETAILSBUSINESSREMOTE"))
	    {
	    	 beanName = props.getProperty("loan.detail.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("ASSETSCOLLATERALSBUSINESSREMOTE"))
	    {
	    	 beanName = props.getProperty("assets.collaterals.business.session.bean.remotes");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("CHARGEBUSINESSREMOTE"))
	    {
	    	 beanName = props.getProperty("charge.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("SECURITYDEPOSITBUSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("security.deposit.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("INSTALLMENTPLANBUSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("installment.plan.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DOCCUMENTBUSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("doccument.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("NOTEPADBUSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("notepad.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("IMDDETAILSBUSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("imd.details.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("PAYMENTRECEIPTBUSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("payment.receipt.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("WAIVEOFFBUSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("waive.off.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("RECHEDULINGBUSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("recheduling.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("PRESENTATIONPROCESSBUSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("presentation.process.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	    	
	    }
	    //Code End Manish

	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEALVERIFICATIONCAPTURINGBUSINESS"))
	    {
	    	 beanName = props.getProperty("verification.capturing.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEALVERIFICATIONCOMPLETIONBUSINESS"))
	    {
	    	 beanName = props.getProperty("verification.completion.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEALVERIFICATIONINITIATIONGBUSINESS"))
	    {
	    	 beanName = props.getProperty("verification.initiation.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }

	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEALRATEAPPROVALBUSINESS"))
	    {
	    	 beanName = props.getProperty("rate.approval.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }

	    	    

	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("REPORTBUSINESSSESSIONBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("report.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("UPLOADBUSINESSSESSIONBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("upload.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DISBURSALBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("disbursal.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("EARLYBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("early.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	   
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("MATURITYBUSSINESSMASTERREMOTE"))
	    {
	    	 beanName = props.getProperty("maturity.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEALVERIFICATIONGBUSINESS"))
	    {
	    	 beanName = props.getProperty("verification.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	 
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("QUERYINITIATIONRESPONSEBUSINESSSESSIONBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("query.initiation.response.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("QUALITYCHECKBUSINESSSESSIONBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("quality.check.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("DEALFinancialAnalysisBusiness"))
	    {
	    	 beanName = props.getProperty("financial.analysis.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }


	    if(CommonFunction.checkNull(key).equalsIgnoreCase("POLICYDEVIATIONBUSSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("policy.deviation.bussiness.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }


	    if(CommonFunction.checkNull(key).equalsIgnoreCase("CASEMARKINGBUSSINESSSESSIONREMOTE"))
	    {
	    	 beanName = props.getProperty("case.marking.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("MANUALADVICEBUSSINESSSESSIONBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("manual.advice.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("LOVPOPUPBUSINESSREMOTE"))
	    {
	    	 beanName = props.getProperty("lov.pop.business.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }

	
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("REPOBUSSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("repo.business.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("FILEOPERATIONBUSSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("file.operation.bussiness.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("KNOCKOFFBUSSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("knock.off.bussiness.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }

	    if(CommonFunction.checkNull(key).equalsIgnoreCase("INSURANCEBUSSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("insurance.bussiness.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("INSTRUMENTBUSSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("instrument.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("EODBODBUSSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("eodbod.process.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
	    if(CommonFunction.checkNull(key).equalsIgnoreCase("POOLBUSSINESSBEANREMOTE"))
	    {
	    	 beanName = props.getProperty("poll.bussiness.session.bean.remote");//EJB CLASS WITH THE IMPLEMENTATION (simple name)
	         
	    }
        String lookUpPath=CommonFunction.checkNull(applName)+ CommonFunction.checkNull(beanName);
        logger.info("Path for look up Business Impl Class "+lookUpPath);
        Object ob=ic.lookup(lookUpPath);
        lookUpPath=null;
        applName=null;
        beanName=null;

		return ob;
	}

}
